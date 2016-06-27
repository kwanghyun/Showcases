package com.cisco.ef.service;

import org.dsa.iot.dslink.DSLink;
import org.dsa.iot.dslink.DSLinkFactory;
import org.dsa.iot.dslink.DSLinkHandler;
import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.node.NodeBuilder;
import org.dsa.iot.dslink.node.value.Value;
import org.dsa.iot.dslink.node.value.ValueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kwjang on 2/15/16.
 */
public class DsaService extends DSLinkHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DsaService.class);


    @Override
    public boolean isResponder() {
        return true;
    }

//    public static void main(String[] args) {
////		SpringApplication.run(DsaDemoApplication.class, args);
//
//        String dsaBrokerUrl = "http://10.106.8.159:8080/conn";
//        String dsLinkPath = "/Users/jangkwanghyun/Showcases/Vertx/Qbit/src/main/resources/dslink.json";
//
//        final String[] dsa_args = {"--broker", dsaBrokerUrl, "-d", dsLinkPath};
//
//        DSLinkFactory.start(dsa_args, new DsaService());
//    }

    public void start(){
        String dsaBrokerUrl = "http://10.106.8.159:8080/conn";
        String dsLinkPath = "/Users/jangkwanghyun/Showcases/Vertx/Qbit/src/main/resources/dslink.json";

        final String[] dsa_args = {"--broker", dsaBrokerUrl, "-d", dsLinkPath};

        DSLinkFactory.start(dsa_args, new DsaService());
    }

    @Override
    public void onResponderInitialized(DSLink link) {
        Node superRoot = link.getNodeManager().getSuperRoot();
        NodeBuilder builder = superRoot.createChild("example");
        builder.setSerializable(false);
        builder.setDisplayName("Example");
        builder.setValueType(ValueType.STRING);
        builder.setValue(new Value("Hello world"));
        builder.build();

        LOGGER.info("Initialized");
    }

    @Override
    public void onResponderConnected(DSLink link) {
        LOGGER.info("Connected");
    }

}