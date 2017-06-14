package com.jikezhiji.survey.util;


import java.util.List;

/**
 * Created by liusizuo on 2017/6/8.
 */
public class Lists {

    public static <T> List<T>[] split(List<T> list, int index){
        List<T> part1 = list.subList(0,index);
        List<T> part2 = list.subList(index,list.size());
        return new List[]{part1,part2};
    }
}
