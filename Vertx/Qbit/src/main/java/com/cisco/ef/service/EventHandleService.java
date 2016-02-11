package com.cisco.ef.service;

import com.cisco.ef.model.Event;
import io.advantageous.qbit.annotation.PathVariable;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestMethod;
import io.advantageous.qbit.annotation.RequestParam;
import io.advantageous.qbit.reactive.Callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kwjang on 2/6/16.
 */

@RequestMapping(value = "/api/event", description = "Event Handling service")
public class EventHandleService {

    private static final Logger log = LoggerFactory.getLogger(EventHandleService.class);

    private Map<Long, Event> eventMap = new TreeMap<Long, Event>();

    @RequestMapping("/count")
    public int size() {

        return eventMap.size();
    }

    @RequestMapping(value = "/event/{pacakgeId}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable("pacakgeId") Long pacakgeId) {

        Event event = eventMap.get(pacakgeId);
        if (event ==  null) {
            throw new IllegalArgumentException("Event " + pacakgeId + " does not exist");
        }
        return event;
    }

    @RequestMapping("/events")
    public void list(final Callback<ArrayList<Event>> callback) {
        callback.accept(new ArrayList<Event>(eventMap.values()));
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public void add(final Callback<Boolean> callback, final Event item) {
        log.info("[add()] started..");
        System.out.println("[add()] started.." + item.toString());
        eventMap.put(item.getPackageId(), item);
        callback.accept(true);
    }

    @RequestMapping(value = "/eventSync", method = RequestMethod.POST)
    public boolean addSync(final Event item) {
        log.info("[addSync()] started..");
        System.out.println("[addSync()] started..");
        eventMap.put(item.getPackageId(), item);
        return true;
    }

    @RequestMapping(value = "/event", method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value = "id") final String packageId) {
        Event remove = eventMap.remove(packageId);
        return remove != null;
    }

    @RequestMapping(value = "/event", method = RequestMethod.DELETE)
    public void remove(final Callback<Boolean> callback,
                       final @RequestParam("id") String id) {

        Event remove = eventMap.remove(id);
        callback.accept(remove != null);
    }

}
