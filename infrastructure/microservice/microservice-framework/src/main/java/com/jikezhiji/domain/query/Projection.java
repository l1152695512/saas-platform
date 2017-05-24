package com.jikezhiji.domain.query;

/**
 * Created by E355 on 2016/9/23.
 */
public interface Projection {
    <V> V getValue(String path);
}
