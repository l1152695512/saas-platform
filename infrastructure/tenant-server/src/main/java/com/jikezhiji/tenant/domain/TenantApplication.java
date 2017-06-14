package com.jikezhiji.tenant.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jikezhiji.tenant.enumeration.EntityStatus;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
/**
 * Created by E355 on 2016/9/19.
 */
@Entity
@IdClass(TenantApplication.TenantApplicationPK.class)
public class TenantApplication {
    @Id
    private String tenantId;
    @Id
    private String applicationId;
    private Date startTime;
    private Date endTime;
    private String description;

    @Convert(converter = MapConverter.class)
    private Map<String,Object> properties;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String serviceId) {
        this.applicationId = serviceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public TenantApplication() {
    }

    public TenantApplication(String tenantId, String applicationId) {
        this();
        this.tenantId = tenantId;
        this.applicationId = applicationId;
    }

    public static class TenantApplicationPK implements Serializable {
        private String tenantId;
        private String applicationId;
        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String serviceId) {
            this.applicationId = serviceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TenantApplicationPK that = (TenantApplicationPK) o;

            if (!tenantId.equals(that.tenantId)) return false;
            return applicationId.equals(that.applicationId);
        }

        @Override
        public int hashCode() {
            int result = tenantId.hashCode();
            result = 31 * result + applicationId.hashCode();
            return result;
        }
    }

    public static class MapConverter implements AttributeConverter<Map<String,Object>, String> {

        private ObjectMapper mapper;


        @Override
        public String convertToDatabaseColumn(Map<String, Object> attribute) {
            if(attribute == null) return null;
            try {
                return this.mapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("map对象转json失败",e);
            }
        }

        @Override
        public Map<String, Object> convertToEntityAttribute(String dbData) {
            if(dbData == null) return null;
            try {
                return this.mapper.readValue(dbData, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new IllegalArgumentException("json字符串转map对象失败",e);
            }
        }
    }

}
