package com.jikezhiji.core;

/**
 * Created by E355 on 2016/9/12.
 */
public abstract class SubjectContext {

    private static ThreadLocal<Subject> context = new InheritableThreadLocal<>();

    public static void addSubject(Subject subject) {
        context.set(subject);
    }

    public static Subject currentSubject() {
        return context.get();
    }

    public static String currentIdentifiers() {
        Subject subject = currentSubject();
        if(subject != null && subject.getPrimary() != null) {
            return subject.getPrimary().getName();
        }
        return null;
    }

    public static void removeCurrentSubject() {
        context.remove();
    }
}
