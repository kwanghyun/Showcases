package com.cisco.ef.service;

import com.cisco.ef.model.Event;
import io.advantageous.qbit.annotation.PathVariable;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestMethod;
import io.advantageous.qbit.annotation.RequestParam;
import io.advantageous.qbit.reactive.Callback;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.oauth2.AccessToken;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kwjang on 2/6/16.
 */

@RequestMapping(value = "/api/auth", description = "Oauth service")
public class OauthService {

    private static final Logger logger = LoggerFactory.getLogger(OauthService.class);

    private Map<Long, Event> eventMap = new TreeMap<Long, Event>();
    private Vertx vertx;

    public OauthService(Vertx vertx) {
        this.vertx = vertx;
    }

    @RequestMapping("/test")
    public void test() {
        OAuth2Auth oauth2 = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE, new JsonObject()
                .put("clientID", "1015c2b97bd1f36eb883")
                .put("clientSecret", "dcb58dcee5eeae69d1fd7778dbd0e3a2462c683e")
                .put("site", "https://github.com/login")
                .put("tokenPath", "/oauth/access_token")
                .put("authorizationPath", "/oauth/authorize")
        );

        // when there is a need to access a protected resource or call a protected method,
        // call the authZ url for a challenge
        String authorization_uri = oauth2.authorizeURL(new JsonObject()
                .put("redirect_uri", "http://localhost:8888/api/callback")
                .put("scope", "notifications")
                .put("state", "3(#0/!~"));
        // when working with web application use the above string as a redirect url

        // in this case GitHub will call you back in the callback uri one should now
        // complete the handshake as:
        String code = "";
        // the code is provided as a url parameter by github callback call

        oauth2.getToken(new JsonObject().put("code", code).put("redirect_uri",
                "http://localhost:8888/api/callback"), res -> {
            if (res.failed()) {
                logger.info("Access Token Error: " + res.cause().getMessage());
            } else {
                // Get the access token object (the authorization code is given from the previous step).
                AccessToken token = res.result();
                logger.info("Access Token GET: " + token.principal());
            }
        });
    }

    @RequestMapping("/callback")
    public void callback() {
        logger.info("CALL BACL CALLED..................");

    }

}
