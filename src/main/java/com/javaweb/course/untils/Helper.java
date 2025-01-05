package com.javaweb.course.untils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Helper {

    public static Long getNowMillisAtUtc() {
        return System.currentTimeMillis();
    }
}
