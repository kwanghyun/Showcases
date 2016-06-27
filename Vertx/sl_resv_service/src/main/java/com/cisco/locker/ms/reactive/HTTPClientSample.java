package com.cisco.locker.ms.reactive;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;

import com.google.gson.Gson;

import rx.Observable;
import rx.apache.http.ObservableHttp;

public class HTTPClientSample {
	private Map<String, Set<Map<String, Object>>> cache = new ConcurrentHashMap<>();

	// Observable.never() :
	// Returns an Observable that never sends any items or notifications to an
	// Observer.
	// doOnNext() :
	// Modifies the source Observable so that it invokes an action when it calls
	// onNext.
	public Observable<Map<String, Object>> fromCache(String url) {
		return Observable.from(getCache(url)).defaultIfEmpty(null)
				.flatMap(json -> (json == null) ? Observable.never() : Observable.just(json))
				.doOnNext(json -> json.put("json_cached", true));
	}

	public Set<Map<String, Object>> getCache(String url) {
		if (!cache.containsKey(url)) {
			cache.put(url, new HashSet<Map<String, Object>>());
		}
		return cache.get(url);
	}

	public static void main(String[] args) {
		try (CloseableHttpAsyncClient client = HttpAsyncClients.createDefault()) {

			client.start();

			String username = "kwanghyun";
			HTTPClientSample ob = new HTTPClientSample();
			Observable<Map> resp = ob.githubUserInfoRequest(client, username);

			Utils.blockingSubscribePrint(resp.map(json -> json.get("name") + "(" + json.get("language") + ")"), "Json");

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private Observable<Map> githubUserInfoRequest(HttpAsyncClient client, String githubUser) {
		if (githubUser == null) {
			return Observable.<Map> error(new NullPointerException("Github user must not be null!"));
		}

		String url = "https://api.github.com/users/" + githubUser + "/repos";

		Observable<Map> response = requestJson(client, url);
		response.forEach((data) -> System.out.println("@@ response : " + data) );
		Utils.blockingSubscribePrint(response, "&&Response => ");
		
		return response.filter(json -> json.containsKey("git_url")).filter(json -> json.get("fork").equals(false));
	}

	private Observable<Map> requestJson(HttpAsyncClient client, String url) {
		Observable<String> rawResponse = ObservableHttp.createGet(url, client).toObservable()
				.flatMap(resp -> resp.getContent()
						.map(bytes -> new String(bytes, java.nio.charset.StandardCharsets.UTF_8)))
				.retry(5).cast(String.class).map(String::trim).doOnNext(resp -> getCache(url).clear());

		Observable<String> objects = rawResponse.filter(data -> data.startsWith("{")).map(data -> "[" + data + "]");

		Observable<String> arrays = rawResponse.filter(data -> data.startsWith("["));

		// ambWith() :
		// Mirrors the Observable (current or provided) that first either emits
		// an item or sends a termination notification.
		Observable<Map> response = arrays.ambWith(objects).map(data -> {
			return new Gson().fromJson(data, List.class);
		}).flatMapIterable(list -> list).cast(Map.class)
				.doOnNext(json -> getCache(url).add((Map<String, Object>) json));
		
		// amb() :
		// Given two Observables, mirrors the one that first either emits an
		// item or sends a termination notification.
		return Observable.amb(fromCache(url), response);
	}
}
