package com.jikezhiji.domain.command.core.simple;

import com.jikezhiji.domain.command.BusinessMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/10/26.
 */
@Aspect
public class OperationAspect {
    private static ThreadLocal<OperationEvent> localOperationEvent = new InheritableThreadLocal<>();

    @Pointcut("this(com.jikezhiji.domain.command.EventSourcingAggregateRoot) && (execution(public *.new(..)) || execution(public * com.jikezhiji..*.*(..)))")
    public void AggregateRootPointCut(JoinPoint joinPoint){}

    @Before("AggregateRootPointCut(jp)")
    public void beforeInvoke(JoinPoint jp){
        OperationEvent event = localOperationEvent.get();
        if(event !=null && isNotInternalCall(jp)){
            Signature signature = jp.getSignature();
            if(signature instanceof CodeSignature) {
                BusinessOperation operation = new BusinessOperation();
                operation.setParameters(jp.getArgs());
                if(signature instanceof MethodSignature) {
                    Method method = ((MethodSignature) signature).getMethod();
                    BusinessMethod businessMethod = method.getAnnotation(BusinessMethod.class);
                    operation.setBusinessCode(businessMethod != null ? businessMethod.value() : method.getName());
                } else if(signature instanceof ConstructorSignature) {
                    Constructor constructor = ((ConstructorSignature) signature).getConstructor();
                    BusinessMethod businessMethod = (BusinessMethod) constructor.getAnnotation(BusinessMethod.class);
                    operation.setBusinessCode(businessMethod != null ? businessMethod.value() : constructor.getName());
                }
                event.getPayload().add(operation);
            }
        }
    }

    private boolean isNotInternalCall(JoinPoint jp){
        String pointcutClass = jp.getThis().getClass().getName();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 4; i < stackTraceElements.length; i++) {
            if(stackTraceElements[i].getClassName().equals(pointcutClass)) {
                return false;
            }
        }
        return true;
    }

    static void setOperationEvent(OperationEvent event) {
        localOperationEvent.set(event);
    }
    static OperationEvent getAndRemoveOperationEvent() {
        OperationEvent event = localOperationEvent.get();
        localOperationEvent.remove();
        return event;
    }
}
