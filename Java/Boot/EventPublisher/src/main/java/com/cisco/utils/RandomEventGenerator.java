package com.cisco.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RandomEventGenerator {
	private static final Logger LOG = LoggerFactory.getLogger(RandomEventGenerator.class);

	List<HashMap<String, String>> metaInfos = new ArrayList<HashMap<String, String>>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Value("${rabbitmq.publisher.eventsource.dir}")
	private String targetDirectory = "";

	@PostConstruct
	public void init() {
		if (targetDirectory.isEmpty())
			targetDirectory = System.getProperty("user.dir");
		
		LOG.info("targetDirectory -> {}", targetDirectory);
		
		try {
			Files.walk(Paths.get(targetDirectory)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					if (filePath.toString().endsWith(".json")) {
						LOG.info("Reading a file : {}", filePath);
						readJsonFile(filePath.toString());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private void readJsonFile(String path) {
		try {
			byte[] mapData = Files.readAllBytes(Paths.get(path));
			if(mapData == null)
				return;
			ObjectMapper objectMapper = new ObjectMapper();
			List<HashMap<String, String>> parsedList = objectMapper.readValue(mapData,
					new TypeReference<List<HashMap<String, String>>>() {
					});

			metaInfos.addAll(parsedList);
			LOG.info("Loaded meta info : {}", metaInfos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, Object> generateRandomData(int idx) {
		HashMap<String, String> metaInfo = metaInfos.get(idx);
		LOG.info("Selected meta info {}", metaInfo);
		HashMap<String, Object> newEvent = new HashMap<>();
		Random rd = new Random();

		for (Entry<String, String> set : metaInfo.entrySet()) {
			String key = set.getKey();
			String value = set.getValue();
			String[] valueArr = value.split(",");

			String type = valueArr[0].trim().toLowerCase();

			if (valueArr.length > 1) {

				StringBuilder result = new StringBuilder();
				int min = 0;
				int max = 0;
				int rnd = 0;
				switch (type) {
				case "ref":
					// Skip for now to reference later
					break;
				case "string":
					result.append(valueArr[1].trim());
					min = Integer.parseInt(valueArr[2].trim());
					max = Integer.parseInt(valueArr[3].trim());
					rnd = rd.nextInt(max - min) + min;
					result.append(rnd);
					newEvent.put(key, result.toString());
					break;
				case "long":
					result.append(valueArr[1].trim());
					min = Integer.parseInt(valueArr[2].trim());
					max = Integer.parseInt(valueArr[3].trim());
					rnd = rd.nextInt(max - min) + min;
					result.append(rnd);
					newEvent.put(key, Long.parseLong(result.toString()));
					break;
				case "int":
					min = Integer.parseInt(valueArr[1].trim());
					max = Integer.parseInt(valueArr[2].trim());
					rnd = rd.nextInt(max - min) + min;
					newEvent.put(key, rnd);
					break;
				case "boolean":
					newEvent.put(key, rd.nextBoolean());
					break;
				case "timestamp":
					String start = valueArr[1].trim();
					String end = valueArr[2].trim();
					newEvent.put(key, generateRandomTimestampInRange(start, end));
					break;

				default:
					LOG.error("NO Value Type !!!!!!!!!!!");
				}
			} else {
				newEvent.put(key, value);
			}
		}

		// Set Reference type
		for (Entry<String, String> set : metaInfo.entrySet()) {
			String key = set.getKey().trim();
			String value = set.getValue().trim();
			if (value.startsWith("ref")) {
				String[] valueArr = value.split(",");
				String prefix = valueArr[1].trim();
				String refKey = valueArr[2].trim().substring(1, valueArr[2].length());
				LOG.info("refKey -> {}", prefix + refKey);
				newEvent.put(key, prefix + newEvent.get(refKey));
			}
		}

		return newEvent;
	}

	public String generateRandomTimestampInRange(String start, String end) {

		Instant startTime = Instant.parse(start);
		Instant endTime;
		if (end.equals("now")) {
			endTime = Instant.now();

		} else {
			endTime = Instant.parse(end);
		}

		long startEpoch = startTime.getEpochSecond();
		long endEpoch = endTime.getEpochSecond();

		long randomEpoch = startEpoch + Math.abs(new Random().nextLong()) % (endEpoch - startEpoch);
		ZonedDateTime utc = Instant.ofEpochSecond(randomEpoch).atZone(ZoneOffset.UTC);

		return utc.toString();
	}

	public List<HashMap<String, String>> getMetaInfos() {
		return metaInfos;
	}

	public void setMetaInfos(List<HashMap<String, String>> metaInfos) {
		this.metaInfos = metaInfos;
	}

//	public static void main(String[] args) throws JsonProcessingException {
//
//		RandomEventGenerator reg = new RandomEventGenerator();
//		reg.init();
//
//		for (int i = 0; i < 10; i++) {
//			Random r = new Random();
//			int metaIdx = r.nextInt(reg.getMetaInfos().size());
//
//			ObjectMapper mapper = new ObjectMapper();
//			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reg.generateRandomData(metaIdx));
//			LOG.info("Generated DATA :: \n {} ", json);
//		}
//	}
}
