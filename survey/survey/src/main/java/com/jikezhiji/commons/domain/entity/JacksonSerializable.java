package com.jikezhiji.commons.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by liusizuo on 2017/6/2.
 */
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public interface JacksonSerializable extends Serializable{
}
