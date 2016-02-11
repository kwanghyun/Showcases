//package com.cisco.ef;
//
//import com.cisco.ef.service.EventHandleService;
//import io.advantageous.qbit.admin.ManagedServiceBuilder;
//import io.advantageous.qbit.meta.builder.ContextMetaBuilder;
//
///**
// * Created by kwjang on 2/6/16.
// */
//public class Main {
//    public static void main(final String... args) throws Exception {
//
//        /* Create the ManagedServiceBuilder which manages a clean shutdown, health, stats, etc. */
//        final ManagedServiceBuilder managedServiceBuilder =
//                ManagedServiceBuilder.managedServiceBuilder()
//                        .setRootURI("/v1") //Defaults to services
//                        .setPort(8888); //Defaults to 8080 or environment variable PORT
//
//        /* Context meta builder to document this endpoint.  */
//        ContextMetaBuilder contextMetaBuilder = managedServiceBuilder.getContextMetaBuilder();
//        contextMetaBuilder.setContactEmail("kwjang@cisco.com");
//        contextMetaBuilder.setDescription("Smart locker microservice");
//        contextMetaBuilder.setContactURL("http://www.cisco.com");
//        contextMetaBuilder.setContactName("Cisco System, Inc");
//        contextMetaBuilder.setLicenseName("Demo");
//        contextMetaBuilder.setLicenseURL("http://www.canttouchthis.com");
//        contextMetaBuilder.setTitle("SmartLocker Demo");
//        contextMetaBuilder.setVersion("0.0.1");
//
//
////        managedServiceBuilder.getStatsDReplicatorBuilder().setHost("192.168.59.103");
////        managedServiceBuilder.setEnableStatsD(true);
//
//
//        /* Start the service. */
//        managedServiceBuilder.addEndpointService(new EventHandleService()) //Register TodoService
//                .getEndpointServerBuilder()
//                .build().startServer();
//
//        /* Start the admin builder which exposes health end-points and swagger meta data. */
//        managedServiceBuilder.getAdminBuilder().build().startServer();
//
//        System.out.println("Event Server and Admin Server started");
//
//          /* Setup WebSocket Server support. */
//
//    }
//}