package com.jikezhiji.domain.command.core.simple;


import com.jikezhiji.core.SubjectContext;
import com.jikezhiji.domain.AggregateRoot;
import com.jikezhiji.domain.AggregateRootException;
import com.jikezhiji.domain.AggregateRootPersistException;
import com.jikezhiji.domain.IdIncrement;
import com.jikezhiji.domain.command.core.AggregateHolder;
import com.jikezhiji.domain.command.core.Executable;
import com.jikezhiji.domain.command.repository.CommandRepositorySupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;
import java.sql.Timestamp;
import java.util.*;

import static org.springframework.validation.DataBinder.DEFAULT_OBJECT_NAME;

/**
 * Created by Administrator on 2016/10/26.
 */
public class OperationCommand {
    private Log log = LogFactory.getLog(getClass());

    private CommandRepositorySupport repository;

    private Set<Validator> validatorSet;

    private SortedSet<com.jikezhiji.domain.command.event.EventListener> listeners = new TreeSet<>((l1, l2) -> l1.getOrder() - l2.getOrder());

    public OperationCommand(Set<com.jikezhiji.domain.command.event.EventListener> listeners, CommandRepositorySupport repository, Validator ... validators){
        listeners.forEach(this::register);
        this.repository = repository;
        if(validators!=null) {
            this.validatorSet = new HashSet<>(Arrays.asList(validators));
        }
    }

    public void register(com.jikezhiji.domain.command.event.EventListener listener) {
        if(listener.getSupportedType().isAssignableFrom(OperationEvent.class)) {
            if(listener.getOrder() <= 0 && listener.getClass() != OperationEventPersistenceListener.class) {
                throw new IllegalStateException("SimpleEventListener必须是第一个处理SimpleEvent的Listener");
            }
        }
        log.info("EventListener("+listener.getClass().getName()+")注册成功");
        listeners.add(listener);
    }

    private boolean isIdIncrementType(Class<? extends AggregateRoot> type){
        return type.getAnnotation(IdIncrement.class) != null;
    }

    public void  execute(String commandId, AggregateHolder holder, Executable expression) {
        if (commandId == null) {
            throw new AggregateRootException("commandId不能为空");
        }
        if (expression == null) {
            throw new AggregateRootException("expression不能为空");
        }

        if(holder.getId() == null && isIdIncrementType(holder.getType())) {
            holder.setId(repository.getNextId(holder.getType()));
        } else {
            throw new AggregateRootException("非自增长的聚合根id不能为空");
        }

        AggregateHolder original = holder.clone();
        try {
            this.doExecute(commandId,holder,expression);
        } catch (AggregateRootPersistException e) {
            this.doExecute(commandId,original,expression);
        }
    }


    public void doExecute(String commandId, AggregateHolder holder, Executable expression) {
        if(holder.getAggregateRoot().isDeleted()) {
            return;
        }
        OperationAspect.setOperationEvent(new OperationEvent());
        expression.apply(holder);
        if(validate(holder.getAggregateRoot())) {
            triggerEvent(commandId, holder);
        }
    }

    private boolean validate(AggregateRoot aggregateRoot) throws ValidationException {
        Errors errors = new BeanPropertyBindingResult(aggregateRoot, DEFAULT_OBJECT_NAME);
        validatorSet.stream()
                .filter(validator -> validator.supports(AggregateRoot.class))
                .forEach(validator -> validator.validate(aggregateRoot,errors));
        if(errors.hasErrors()) {
            throw new AggregateRootException(errors.toString());
        }
        return true;
    }

    public void triggerEvent(String commandId, AggregateHolder changeSet) {
        OperationEvent event = OperationAspect.getAndRemoveOperationEvent();
        event.setId(commandId);
        event.setAggregateRootId(changeSet.getId());
        event.setAggregateRoot(changeSet.getAggregateRoot());
        event.setTriggeredTime(new Timestamp(System.currentTimeMillis()));
        event.setTriggeredBy(SubjectContext.currentIdentifiers());
        for(com.jikezhiji.domain.command.event.EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

}
