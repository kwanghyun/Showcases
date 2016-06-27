package com.cisco.ef.service;

import io.advantageous.consul.Consul;
import io.advantageous.consul.domain.CatalogService;
import io.advantageous.consul.domain.ConsulResponse;
import io.advantageous.consul.domain.Service;
import io.advantageous.consul.domain.ServiceHealth;
import io.advantageous.consul.endpoints.CatalogEndpoint;
import io.vertx.core.AbstractVerticle;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by kwjang on 2/11/16.
 */
public class ConsulService extends AbstractVerticle {
    final static private Logger logger = Logger.getLogger(ConsulService.class);
    final static private String CONSUL_HOST = "10.106.8.158";

    @Override
    public void start() throws Exception {
        Consul client = Consul.consul(CONSUL_HOST, 8500);

        //        String serviceId = UUID.randomUUID().toString();
        String serviceId = "sl-reservation-service-12345";
        client.agent().registerService(8888, 10000L, "sl-reservation-service", serviceId);
        client.agent().pass(serviceId);

        vertx.setPeriodic(5000, id -> {
            // This handler will get called every second
            client.agent().pass(serviceId);
        });

        CatalogEndpoint catalogClient = client.catalog();
        ConsulResponse<List<CatalogService>> services = catalogClient.getService("meteor");
        logger.info("[getSingleService]" + services.getResponse().iterator().next().getServiceName());

        for (Map.Entry<String, Service> service : client.agent().getServices().entrySet()) {
            logger.info(service.toString());
        }

        ConsulResponse<List<ServiceHealth>> healthyServices = client.health().getHealthyServices("spark");
        for (ServiceHealth service : healthyServices.getResponse()) {
            logger.info("[healthyServices] Service => " + service.getService().toString());
            logger.info("[healthyServices] Node => " + service.getNode().toString());

        }

        String leader = client.status().getLeader();
        logger.info("[Leader] : " + leader);

        List<String> peers = client.status().getPeers();
        for (String ipAndPort : peers) {
            logger.info("[Peers] : " + ipAndPort);
        }
    }
}
