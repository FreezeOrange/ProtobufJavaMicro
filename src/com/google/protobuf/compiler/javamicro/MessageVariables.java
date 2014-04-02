package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/4/2.
 */
public class MessageVariables extends  AbstractFieldVariables {
    public String modifiers;
    public String classname;

    private MessageVariables() {}

    public static MessageVariables create(Descriptors.Descriptor descriptor, Params params) {
        MessageVariables variables = new MessageVariables();
        String fileName = descriptor.getFile().getName();
        boolean isOwnFile = params.isJavaMultipleFiles(fileName)
                && descriptor.getContainingType() == null;
        variables.modifiers = isOwnFile ? " " : " static ";
        variables.classname = descriptor.getName();
        return variables;
    }
}
