package com.jikezhiji.tenant.aggregate;

import com.jikezhiji.domain.command.AutoIncrementEsar;
import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by E355 on 2016/8/23.
 */
@Document(collection="Tenant")
public class Tenant extends AutoIncrementEsar {
    @NotNull
    @Pattern(regexp="^[a-zA-Z]+[0-9a-zA-Z]*$")
    @Size(min=3,max=30)
    private String code;

    @Size(max = 50)
    private String name;

    @Size(min = 1,max = 5)
    private Set<String> hosts = new HashSet<>();



    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Set<String> getHosts() {
        return Collections.unmodifiableSet(hosts);
    }


    public Tenant addHosts(Set<String> hosts) {
        this.hosts.addAll(hosts);
        return this;
    }
    public Tenant(){

    }
    public Tenant(Long id, String code, String name) {
        super.setId(id);
        this.code = code;
        this.name = name;
    }

    public Tenant(Long id, String code,String name, Set<String> hosts){
        this(id,code,name);
        this.addHosts(hosts);
    }

    public Tenant updateName(String name) {
        this.name = name;
        return this;
    }
    public Tenant resetHosts(Set<String> hosts) {
        this.hosts.clear();
        this.hosts.addAll(hosts);
        return this;
    }

}
