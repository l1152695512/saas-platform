package com.jikezhiji.tenant.aggregate;

import com.jikezhiji.domain.command.AutoIncrementEsar;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by E355 on 2016/9/19.
 */
@Document(collection="TenantService")
public class TenantService extends AutoIncrementEsar {
    private Long tenantId;
    private String serviceId;
    @NotNull
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Size(max = 500)
    private String remark;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
