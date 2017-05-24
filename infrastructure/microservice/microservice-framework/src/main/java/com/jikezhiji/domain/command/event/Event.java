package com.jikezhiji.domain.command.event;


import com.jikezhiji.domain.command.EventSourcingAggregateRoot;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E355 on 2016/8/24.
 */
public abstract class Event<Payload> {
    public static String ID = "_id";
    public static String AGGREGATE_ROOT_ID = "aggregateRootId";
    public static String TRIGGERED_TIME = "triggeredTime";
    public static String TRIGGERED_BY = "triggeredBy";
    public static String ATTRIBUTES = "getAttributes";
    public static String PAYLOAD = "payload";

    private String id;
    private Serializable aggregateRootId;
    private String triggeredBy;
    private Date triggeredTime;
    private long version;
    private Payload payload;
    private Map<String, Object> attributes = new HashMap<>();
    private EventSourcingAggregateRoot<? extends Serializable> aggregateRoot;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Serializable getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(Serializable aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    public String getTriggeredBy() {
        return triggeredBy;
    }

    public void setTriggeredBy(String triggeredBy) {
        this.triggeredBy = triggeredBy;
    }

    public Date getTriggeredTime() {
        return triggeredTime;
    }

    public void setTriggeredTime(Date triggeredTime) {
        this.triggeredTime = triggeredTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Object> attrs) {
        this.attributes = attrs;
    }

    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public EventSourcingAggregateRoot<? extends Serializable> getAggregateRoot() {
        return aggregateRoot;
    }

    public void setAggregateRoot(EventSourcingAggregateRoot<? extends Serializable> aggregateRoot) {
        this.aggregateRoot = aggregateRoot;
    }

    public Class<? extends EventSourcingAggregateRoot> getAggregateRootClass() {
        return aggregateRoot.getClass();
    }

}
