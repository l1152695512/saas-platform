package com.jikezhiji.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by E355 on 2016/9/1.
 */
public class MathUtils {

    public static Number add(Number n1,Number n2) {
        if(n1.getClass() == BigDecimal.class) {
            BigDecimal value1 = n1==null? BigDecimal.ZERO :(BigDecimal)n1;
            BigDecimal value2 = n2==null? BigDecimal.ZERO :(BigDecimal)n2;
            return value1.add(value2);
        } else if(n1.getClass() == BigInteger.class) {
            BigInteger value1 = n1==null? BigInteger.ZERO :(BigInteger)n1;
            BigInteger value2 = n2==null? BigInteger.ZERO :(BigInteger)n2;
            return value1.add(value2);
        } else if(n1.getClass() == Long.class) {
            Long value1 = n1 == null ? 0L:(Long) n1;
            Long value2 = n2 == null ? 0L:(Long) n2;
            return value1+value2;
        } else if(n1.getClass() == Integer.class) {
            Integer value1 = n1 == null ? 0:(Integer) n1;
            Integer value2 = n2 == null ? 0:(Integer) n2;
            return value1+value2;
        } else if(n1.getClass() == Short.class) {
            Short value1 = n1 == null ? 0:(Short) n1;
            Short value2 = n2 == null ? 0:(Short) n2;
            return value1+value2;
        } else if(n1.getClass() == Byte.class) {
            Byte value1 = n1 == null ? 0:(Byte) n1;
            Byte value2 = n2 == null ? 0:(Byte) n2;
            return value1+value2;
        } else if(n1.getClass() == Float.class) {
            Float value1 = n1 == null ? 0:(Float) n1;
            Float value2 = n2 == null ? 0:(Float) n2;
            return value1+value2;
        } else if(n1.getClass() == Double.class) {
            Double value1 = n1 == null ? 0:(Double) n1;
            Double value2 = n2 == null ? 0:(Double) n2;
            return value1+value2;
        } else {
            throw new UnsupportedOperationException("不支持的数值类型计算");
        }
    }


    public static Number minus(Number n1,Number n2) {
        if(n1.getClass() == BigDecimal.class) {
            BigDecimal value1 = n1==null? BigDecimal.ZERO :(BigDecimal)n1;
            BigDecimal value2 = n2==null? BigDecimal.ZERO :(BigDecimal)n2;
            return value1.add(value2.negate());
        } else if(n1.getClass() == BigInteger.class) {
            BigInteger value1 = n1==null? BigInteger.ZERO :(BigInteger)n1;
            BigInteger value2 = n2==null? BigInteger.ZERO :(BigInteger)n2;
            return value1.add(value2.negate());
        } else if(n1.getClass() == Long.class) {
            Long value1 = n1 == null ? 0L:(Long) n1;
            Long value2 = n2 == null ? 0L:(Long) n2;
            return value1 - value2;
        } else if(n1.getClass() == Integer.class) {
            Integer value1 = n1 == null ? 0:(Integer) n1;
            Integer value2 = n2 == null ? 0:(Integer) n2;
            return value1 - value2;
        } else if(n1.getClass() == Short.class) {
            Short value1 = n1 == null ? 0:(Short) n1;
            Short value2 = n2 == null ? 0:(Short) n2;
            return value1 - value2;
        } else if(n1.getClass() == Byte.class) {
            Byte value1 = n1 == null ? 0:(Byte) n1;
            Byte value2 = n2 == null ? 0:(Byte) n2;
            return value1 - value2;
        } else if(n1.getClass() == Float.class) {
            Float value1 = n1 == null ? 0:(Float) n1;
            Float value2 = n2 == null ? 0:(Float) n2;
            return value1- value2;
        } else if(n1.getClass() == Double.class) {
            Double value1 = n1 == null ? 0:(Double) n1;
            Double value2 = n2 == null ? 0:(Double) n2;
            return value1 - value2;
        } else {
            throw new UnsupportedOperationException("不支持的数值类型计算");
        }
    }
}
