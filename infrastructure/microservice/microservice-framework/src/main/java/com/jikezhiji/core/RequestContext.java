package com.jikezhiji.core;

/**
 * Created by E355 on 2016/9/14.
 */
public final class RequestContext {

    private static ThreadLocal<Request> context = new InheritableThreadLocal<>();

    static void addRequest(Request context){
        RequestContext.context.set(context);
    }

    public static void removeCurrentContext(){
        context.remove();
    }

    public static Request getCurrentContext(){
        return context.get();
    }
}
