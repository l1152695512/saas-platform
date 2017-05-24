package com.jikezhiji.domain.command.core.simple.data;

import com.jikezhiji.domain.AggregateRootException;
import com.jikezhiji.domain.command.BusinessMethod;
import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import com.jikezhiji.domain.command.core.simple.BusinessOperation;
import com.jikezhiji.domain.command.core.simple.OperationEvent;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2016/10/31.
 */
public class OperationEventApplicator {
    private static Map<Class<?>,Map<String,Map<List<Class<?>>,Executable>>> mappings = new HashMap<>();

    public static <ID extends Serializable> void apply(EventSourcingAggregateRoot<ID> es, OperationEvent event) {
        es.setVersion(es.getVersion() + 1);
        Collection<BusinessOperation> operations =  event.getPayload();
        Map<String,Map<List<Class<?>>,Executable>> codeMapping = getBusinessMethodMapping(es.getClass());
        operations.forEach(businessOperation -> {
            List<Class<?>> parameterTypes = new ArrayList<>();
            List<Object> args = new ArrayList<>();
//            businessOperation.getParameters().forEach(e->{
//                parameterTypes.add(e.getType());
//                args.add(e.getValue());
//            });
            //TODO
            try {
                Executable businessMethod = codeMapping.get(businessOperation.getBusinessCode()).get(parameterTypes);
                if(businessMethod instanceof Method) {
                    ((Method)businessMethod).invoke(es,args.toArray());
                } else {
                    ((Constructor<?>)businessMethod).newInstance(args.toArray());
                }
            } catch (Exception e) {
                throw new AggregateRootException(e);
            }
        });
    }

    private static Map<String,Map<List<Class<?>>,Executable>> getBusinessMethodMapping(Class<?> type){
        if(!mappings.containsKey(type)) {
            Map<String,Map<List<Class<?>>,Executable>> methodMappings = new HashMap<>();
            List<Executable> executables = Arrays.asList(type.getConstructors());
            executables.addAll(Arrays.asList( type.getMethods()));
            for (Executable m: executables){
                String businessCode = m.getName();
                if(m.getAnnotation(BusinessMethod.class) != null) {
                    businessCode = m.getAnnotation(BusinessMethod.class).value();
                }

                if(!methodMappings.containsKey(businessCode)) {
                    methodMappings.put(businessCode,new HashMap<>());
                }

                Map<List<Class<?>>,Executable> parameterMap = methodMappings.get(businessCode);
                List<Class<?>> list = Arrays.asList(m.getParameterTypes());
                if(parameterMap.containsKey(list)) {
                   throw new AggregateRootException("BusinessMethod方法签名冲突，不能拥有同样的code和参数类型");
                }
                parameterMap.put(list,m);
            }
        }
        return mappings.get(type);
    }

}
