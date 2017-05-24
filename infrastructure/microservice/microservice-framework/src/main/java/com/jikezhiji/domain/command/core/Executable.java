package com.jikezhiji.domain.command.core;


import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import com.jikezhiji.domain.command.core.AggregateHolder;

import java.util.function.Function;

/**
 * 定义如何调用业务方法的表达式类（business method）
 * 调用领域对象的相关业务方法以完成业务逻辑处理；
 * Created by E355 on 2016/9/22.
 */
public interface Executable extends Function<AggregateHolder,EventSourcingAggregateRoot<?>>{
}
