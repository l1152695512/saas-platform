package com.jikezhiji.test;

/**
 * Created by Administrator on 2016/10/26.
 */
public aspect HelloAspect {
    pointcut HelloWorldPointCut() : execution(* com.jikezhiji.test.HelloWorld.main(..));

    before() : HelloWorldPointCut(){
        System.out.println("Hello world");
    }
}
