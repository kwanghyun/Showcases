package com.cisco.ef.client;

import com.cisco.ef.model.Employee;
import io.advantageous.boon.core.Sys;
import io.advantageous.boon.json.JsonFactory;
import io.advantageous.qbit.http.client.HttpClient;

import org.apache.log4j.Logger;

import static io.advantageous.qbit.http.client.HttpClientBuilder.httpClientBuilder;
import static io.advantageous.boon.core.IO.puts;


/**
 * Created by kwjang on 2/7/16.
 */
public class QbitHTTPAsyncClient {
    final static Logger logger = Logger.getLogger(QbitHTTPAsyncClient.class);

    public static void main(String... args) {

        /* Setup an httpClient. */
        HttpClient httpClient = httpClientBuilder()
                .setHost("localhost").setPort(8080).build();
        httpClient.start();


        /* Using Async support with lambda. */
        httpClient.getAsync("/hi/async", (code, contentType, body) -> {
            puts("Async text with lambda", body);
        });

        Sys.sleep(100);


        /* Using Async support with lambda. */
        httpClient.getAsyncWith1Param("/hi/async", "hi", "mom", (code, contentType, body) -> {
            logger.info("@@ : code => " + code);
            puts("Async text with lambda 1 param\n", body);
        });

        Sys.sleep(100);


        /* Using Async support with lambda. */
        httpClient.getAsyncWith2Params("/hi/async",
                "p1", "v1",
                "p2", "v2",
                (code, contentType, body) -> {
                    puts("Async text with lambda 2 params\n", body);
                });

        Sys.sleep(100);


        /* Using Async support with lambda. */
        httpClient.getAsyncWith3Params("/hi/async",
                "p1", "v1",
                "p2", "v2",
                "p3", "v3",
                (code, contentType, body) -> {
                    puts("Async text with lambda 3 params\n", body);
                });

        Sys.sleep(100);



        /* Using Async support with lambda. */
        httpClient.postAsync("/hi/async",
                (code, contentType, body) -> {
                    puts("Async text with lambda", body);
                });

        Sys.sleep(100);


        httpClient.sendJsonPostAsync("/foo/json/",
                JsonFactory.toJson(new Employee("Hyun", 35) {
                }),
                (code, contentType, body) ->
                        puts("ASYNC POST", code, contentType, JsonFactory.fromJson(body)));


        Sys.sleep(100);

        /* Using Async support with lambda. */
        httpClient.postFormAsyncWith1Param("/hi/async", "hi", "mom",
                (code, contentType, body) -> {
                    puts("Async text with lambda 1 param\n", body);
                });

        Sys.sleep(100);


        /* Using Async support with lambda. */
        httpClient.postFormAsyncWith2Params("/hi/async",
                "p1", "v1",
                "p2", "v2",
                (code, contentType, body) -> {
                    puts("Async text with lambda 2 params\n", body);
                });

        Sys.sleep(100);


        /* Using Async support with lambda. */
        httpClient.postFormAsyncWith3Params("/hi/async",
                "p1", "v1",
                "p2", "v2",
                "p3", "v3",
                (code, contentType, body) -> {
                    puts("Async text with lambda 3 params\n", body);
                });

        Sys.sleep(100);


        /* Using Async support with lambda. */
        httpClient.putAsync("/hi/async", (code, contentType, body) -> {
            puts("Async text with lambda", body);
        });

        Sys.sleep(100);

        /* Using Async support with lambda. */
        httpClient.putFormAsyncWith1Param("/hi/async", "hi", "mom",
                (code, contentType, body) -> {
                    puts("Async text with lambda 1 param\n", body);
                });

        Sys.sleep(100);

        httpClient.sendJsonPutAsync("/foo/json/",
                JsonFactory.toJson(new Employee("KWANG", 34)),
                (code, contentType, body)
                        -> puts("ASYNC PUT", code, contentType, body));
    }
}
