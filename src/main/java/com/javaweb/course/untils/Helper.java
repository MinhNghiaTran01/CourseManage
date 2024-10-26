package com.javaweb.course.untils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Helper {
    
    public static Long getNowMillisAtUtc() {
        return System.currentTimeMillis();
    }
}
