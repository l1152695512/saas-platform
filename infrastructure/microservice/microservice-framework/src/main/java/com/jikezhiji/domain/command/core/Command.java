package com.jikezhiji.domain.command.core;


/**
 * Created by E355 on 2016/8/24.
 */
public interface Command {
    void execute(String commandId, AggregateHolder holder, Executable expression);
}
