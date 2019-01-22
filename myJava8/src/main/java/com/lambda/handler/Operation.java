package com.lambda.handler;

@FunctionalInterface
public interface Operation<T,R> {

    R operator(T t1,T t2);

}
