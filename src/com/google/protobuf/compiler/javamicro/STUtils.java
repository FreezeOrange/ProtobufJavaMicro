package com.google.protobuf.compiler.javamicro;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by clark on 2014/3/30.
 */
public class STUtils {

    public static String newStringTemplate(String resource) throws IOException {
        return IOUtils.toString(STUtils.class.getClassLoader().getResourceAsStream(
                "com/google/protobuf/compiler/javamicro/" + resource));
    }

}
