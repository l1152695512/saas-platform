package com.jikezhiji.domain.command.core.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class BusinessOperation {
    private String businessCode;
    private Object[] parameters;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
