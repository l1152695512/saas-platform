package com.jikezhiji.tenant.aggregate;

import com.jikezhiji.domain.command.AutoIncrementEsar;
import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by E355 on 2016/9/19.
 */
@Document(collection="Service")
public class Service extends AutoIncrementEsar {

    @Size(min=3,max = 30)
    private String name;

    @Size(max = 300)
    private String description;

    @NotNull
    private String code;

    /**
     * 初始化租户的端点
     */
    @NotNull
    private String initTenantEndpoint;

    /**
     * 接口文档地址
     */
    private String apiEndpoint;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInitTenantEndpoint() {
        return initTenantEndpoint;
    }

    public void setInitTenantEndpoint(String initTenantEndpoint) {
        this.initTenantEndpoint = initTenantEndpoint;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }


    public TenantService initTenant(Tenant tenant){

        return null;
    }

}
