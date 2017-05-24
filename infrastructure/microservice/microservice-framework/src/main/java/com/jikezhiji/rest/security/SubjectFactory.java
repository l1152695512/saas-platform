package com.jikezhiji.rest.security;

import com.jikezhiji.core.Subject;

/**
 * Created by E355 on 2016/10/12.
 */
public interface SubjectFactory<P> {
    Subject createSubject(P token);
}
