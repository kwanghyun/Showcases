package com.cisco.ef.client;

import com.cisco.ef.model.Event;
import io.advantageous.boon.json.JsonFactory;
import io.advantageous.qbit.http.HTTP;

/**
 * Created by kwjang on 2/6/16.
 */
public class HTTPClient {

    public static void main(final String... args) throws Exception {

        for (int index = 0; index < 100; index++) {

            HTTP.postJSON("http://localhost:8888/v1/api/event/event",
                    JsonFactory.toJson(new Event(index + 1, "site" + index % 3,
                            "bank" + index % 6, index % 13, index % 4,
                            System.currentTimeMillis())));
            System.out.print(".");
        }
    }

}