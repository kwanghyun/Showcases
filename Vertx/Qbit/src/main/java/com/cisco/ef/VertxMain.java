package com.cisco.ef;

import com.cisco.ef.service.DsaService;
import com.cisco.ef.service.EventHandleService;

import com.cisco.ef.service.OauthService;
import io.advantageous.qbit.admin.ManagedServiceBuilder;
import io.advantageous.qbit.http.config.HttpServerConfig;
import io.advantageous.qbit.http.server.HttpServer;
import io.advantageous.qbit.meta.builder.ContextMetaBuilder;
import io.advantageous.qbit.system.QBitSystemManager;
import io.advantageous.qbit.vertx.http.VertxHttpServerBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.oauth2.AccessToken;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by kwjang on 2/8/16.
 */
public class VertxMain extends AbstractVerticle {

    final static private Logger logger = Logger.getLogger(VertxMain.class);
    //    final static private String CONSUL_HOST = "localhost";

    /**
     * Used to startup QBit and use features like swagger, etc.
     */
    private final ManagedServiceBuilder managedServiceBuilder;
    /**
     * Used to shutdown QBit services cleanly.
     */
    private QBitSystemManager systemManager;

    /**
     * Used to shutdown QBit services cleanly.
     */
//    private ServiceDiscovery clientAgent;

    /**
     * Create this verticle
     *
     * @param managedServiceBuilder managedServiceBuilder
     */
    public VertxMain(ManagedServiceBuilder managedServiceBuilder) {
        this.managedServiceBuilder = managedServiceBuilder;
    }

    public static void main(final String... args) throws Exception {
        final ManagedServiceBuilder managedServiceBuilder = createManagedServiceBuilder();


        final CountDownLatch latch = new CountDownLatch(1);
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxMain(managedServiceBuilder), result -> {
            if (result.succeeded()) {
                System.out.println("Deployment id is: " + result.result());
            } else {
                System.out.println("Deployment failed!");
                result.cause().printStackTrace();
            }
            latch.countDown();
        });


        latch.await(5, TimeUnit.SECONDS);

        logger.info("QBit and Vertx are open for e-Busbies");
    }


    @Override
    public void start() throws Exception {


        /* Vertx HTTP Server. */
        final io.vertx.core.http.HttpServer vertxHttpServer =
                this.getVertx().createHttpServer();

        /* Route one call to a vertx handler. */
        final Router router = createRouterAndVertxOnlyRoute();

        final HttpServer httpServer = getHttpServerAndRoutes(vertxHttpServer, router);

        systemManager = managedServiceBuilder.getSystemManager();

        managedServiceBuilder.addEndpointService(new EventHandleService());
//        managedServiceBuilder.addEndpointService(new OauthService(vertx));

        /*
        * Create and start new service endpointServer.
        */
        managedServiceBuilder
                .getEndpointServerBuilder()
                .setHttpServer(httpServer)
                .build()
                .startServer();


        /*
         * Associate the router as a request handler for the vertxHttpServer.
         */
        vertxHttpServer.requestHandler(router::accept).listen(
                managedServiceBuilder.getPort());

        logger.info("[Client Credential flow] started...");

        OAuth2Auth oauth2 = OAuth2Auth.create(vertx, OAuth2FlowType.CLIENT, new JsonObject()
                .put("clientID", "f4f9ea5a8e2fe081f337")
                .put("clientSecret", "df8aadec3990f219b9326b67a0c7506356d14215")
                .put("site", "https://github.com/login")
                .put("tokenPath", "/oauth/access_token")
                .put("authorizationPath", "/oauth/authorize")

        );

        JsonObject tokenConfig = new JsonObject();

        // Callbacks
        // Save the access token
        oauth2.getToken(tokenConfig, res -> {
            if (res.failed()) {
                logger.info("[Access Token Error]: " + res.cause().getMessage());
            } else {
                // Get the access token object (the authorization code is given from the previous step).
                AccessToken token = res.result();
                logger.info("[Access Token SUCCESS]: principal => " + token.principal().encodePrettily());
                logger.info("[Access Token SUCCESS]: expired => " + token.expired());
            }
        });

//        vertx.deployVerticle("com.cisco.ef.service.OauthService");
        vertx.deployVerticle("com.cisco.ef.service.ConsulService", new DeploymentOptions().setWorker(true));
//        DsaService dsaService = new DsaService();
//        dsaService.start();

    }


    private HttpServer getHttpServerAndRoutes(final io.vertx.core.http.HttpServer vertxHttpServer, final Router router) {

        /* Route everything under /v1 to QBit http server. */
        final Route qbitRoute = router.route().path("/v1/*");


        /*
         * Use the VertxHttpServerBuilder which is a special builder for Vertx/Qbit integration.
         */
        return VertxHttpServerBuilder.vertxHttpServerBuilder()
                .setRoute(qbitRoute)
                .setHttpServer(vertxHttpServer)
                .setVertx(getVertx())
                .setConfig(new HttpServerConfig()) //not needed in master branch of qbit workaround for bug
                .build();
    }

    private Router createRouterAndVertxOnlyRoute() {
        final Router router = Router.router(getVertx()); //Vertx router
        router.route("/ui").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.setStatusCode(202);
            response.end("route1 this does not have very much data to transfer");
        });
        return router;
    }

    @Override
    public void stop() throws Exception {
        if (systemManager != null) {
            systemManager.shutDown();
        }
    }


    private static ManagedServiceBuilder createManagedServiceBuilder() {
    /* Create the ManagedServiceBuilder which manages a clean shutdown, health, stats, etc. */
        final ManagedServiceBuilder managedServiceBuilder =
                ManagedServiceBuilder.managedServiceBuilder()
                        .setRootURI("/v1") //Defaults to services
                        .setPort(8888); //Defaults to 8080 or environment variable PORT

        /* Start the admin builder which exposes health
        end-points and swagger meta data. */
        managedServiceBuilder.getAdminBuilder().build().startServer();

        /* Context meta builder to document this endpoint.
        * Gets used by swagger support.
        */
        ContextMetaBuilder contextMetaBuilder = managedServiceBuilder.getContextMetaBuilder();
        contextMetaBuilder.setContactEmail("kwjang@cisco.com");
        contextMetaBuilder.setDescription("Smart locker microservice");
        contextMetaBuilder.setContactURL("http://www.cisco.com");
        contextMetaBuilder.setContactName("Cisco System, Inc");
        contextMetaBuilder.setLicenseName("Micro Service Demo");
        contextMetaBuilder.setLicenseURL("http://www.cisco.com");
        contextMetaBuilder.setTitle("SmartLocker Demo");
        contextMetaBuilder.setVersion("0.0.1");

//        managedServiceBuilder.getStatsDReplicatorBuilder().setHost("192.168.59.103");
//        managedServiceBuilder.setEnableStatsD(true);
        return managedServiceBuilder;
    }

}
