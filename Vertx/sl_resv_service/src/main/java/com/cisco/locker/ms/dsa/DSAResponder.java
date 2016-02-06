package com.cisco.locker.ms.dsa;

import org.dsa.iot.dslink.DSLink;
import org.dsa.iot.dslink.DSLinkFactory;
import org.dsa.iot.dslink.DSLinkHandler;
import org.dsa.iot.dslink.node.Node;
import org.dsa.iot.dslink.node.NodeBuilder;
import org.dsa.iot.dslink.node.value.Value;
import org.dsa.iot.dslink.node.value.ValueType;

import com.cisco.locker.ms.controller.StagedOrderController;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DSAResponder extends DSLinkHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DSAResponder.class);
    @Override
    public boolean isResponder() {
        return true;
    }

    @Override
    public void onResponderInitialized(final DSLink link) {
        LOGGER.info("Initialized");
    }

    @Override
    public void onResponderConnected(final DSLink link) {
        LOGGER.info("Connected");
        
    }

    public static void main(String[] args) {
        DSLinkFactory.start(args, new DSAResponder());
    }
    
    private void loadNodes(final DSLink link){
        Node superRoot = link.getNodeManager().getSuperRoot();
        NodeBuilder builder = superRoot.createChild("MyNum");
        builder.setDisplayName("My Number");
        builder.setValueType(ValueType.STRING);
        builder.setValue(new Value("sDFSDf"));
        final Node node = builder.build();
    }
}
