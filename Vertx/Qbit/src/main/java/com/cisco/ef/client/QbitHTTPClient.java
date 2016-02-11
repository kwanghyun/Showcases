package com.cisco.ef.client;

import com.cisco.ef.model.Employee;
import io.advantageous.boon.json.JsonFactory;
import io.advantageous.qbit.http.client.HttpClient;
import io.advantageous.qbit.http.request.HttpRequest;
import io.advantageous.qbit.http.request.HttpRequestBuilder;
import io.advantageous.qbit.http.request.HttpResponse;

import static io.advantageous.qbit.http.client.HttpClientBuilder.httpClientBuilder;
import static io.advantageous.boon.core.IO.puts;

/**
 * Created by kwjang on 2/7/16.
 */
public class QbitHTTPClient {
    public static void main(String... args) {
        /* Setup an httpClient. */
        HttpClient httpClient = httpClientBuilder()
                .setHost("localhost").setPort(8080).build();
        httpClient.start();

        /* Send no param get. */
        HttpResponse httpResponse = httpClient.get("/hello/mom");
        puts(httpResponse);

        /* Send one param get. */
        httpResponse = httpClient.getWith1Param("/hello/singleParam",
                "hi", "mom");
        puts("single param", httpResponse);


        /* Send two param get. */
        httpResponse = httpClient.getWith2Params("/hello/twoParams",
                "hi", "mom", "hello", "dad");
        puts("two params", httpResponse);


        /* Send six params with get. */
        final HttpRequest httpRequest = HttpRequestBuilder.httpRequestBuilder()
                .addParam("hi", "mom")
                .addParam("hello", "dad")
                .addParam("greetings", "kids")
                .addParam("yo", "pets")
                .addParam("hola", "pets")
                .addParam("salutations", "all").build();

        httpResponse = httpClient.sendRequestAndWait(httpRequest);
        puts("6 params", httpResponse );

        /* Send no param post. */
        httpResponse = httpClient.post( "/hello/mom" );
        puts( httpResponse );


        /* Send one param post. */
        httpResponse = httpClient.postWith1Param("/hello/singleParam",
                "hi", "mom");
        puts("single param", httpResponse );


        /* Send two param post. */
        httpResponse = httpClient.postWith2Params("/hello/twoParams",
                "hi", "mom", "hello", "dad");
        puts("two params", httpResponse );

        /* Sync POST JSON with Sync response */
        httpResponse = httpClient.postJson("/foo/json/sync",
                JsonFactory.toJson(new Employee("Hyun", 35)));

        puts("POST JSON RESPONSE", httpResponse);


        /* Send no param post. */
        httpResponse = httpClient.put( "/hello/mom" );
        puts( httpResponse );


        /* Send one param post. */
        httpResponse = httpClient.putWith1Param("/hello/singleParam",
                "hi", "mom");
        puts("single param", httpResponse );


        httpResponse = httpClient.putJson("/foo/json/",
                JsonFactory.toJson(new Employee("KWANG", 36)));

        puts("PUT JSON RESPONSE", httpResponse);
    }


}
