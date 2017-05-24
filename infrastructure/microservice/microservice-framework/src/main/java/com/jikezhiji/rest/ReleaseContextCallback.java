package com.jikezhiji.rest;



import com.jikezhiji.core.RequestContext;
import com.jikezhiji.core.SubjectContext;

import javax.ws.rs.container.CompletionCallback;

/**
 * Created by E355 on 2016/10/12.
 */
public class ReleaseContextCallback implements CompletionCallback {
    @Override
    public void onComplete(Throwable throwable) {
        RequestContext.removeCurrentContext();
        SubjectContext.removeCurrentSubject();
    }
}
