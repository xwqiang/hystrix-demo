package com.kuyun.util;

/**
 * Created by xuwuqiang on 2018/10/10.
 */
public class HystrixUtil {

    public static String execute(String name) {
        return new HelloWorldHystrixCommand(name).execute();
    }
}
