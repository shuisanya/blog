package com.mhy.blog.utils;

public class ParseUtils {

    public static int parseInt(Object str){
        if(str != null){
            if(str instanceof String) {
                return Integer.parseInt((String) str);
            }
            return (int) str;
        }else {
            return 1;
        }

    }

}
