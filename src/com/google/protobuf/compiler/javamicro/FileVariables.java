package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/4/2.
 */
public class FileVariables extends AbstractFieldVariables {
    public boolean isPackageNameEmpty;
    public String packageName;
    public String classname;
    public boolean isJavaMultipleFiles;

    private FileVariables() {}

    public static FileVariables create(Params params, Descriptors.FileDescriptor descriptor) {
        FileVariables variables = new FileVariables();
        variables.packageName = Helpers.getFileJavaPackage(params, descriptor);
        variables.isPackageNameEmpty = variables.packageName == null || variables.packageName.length() == 0;
        variables.classname = Helpers.getFileClassName(params, descriptor);
        variables.isJavaMultipleFiles = params.isJavaMultipleFiles(descriptor.getName());
        return variables;
    }

    public String getEnumGenerators() {
        // TODO
        return null;
    }

    public String getMessageGenerators() {
        // TODO
        return null;
    }

    public String getStaticMessageGenerators() {
        // TODO
        return null;
    }
}
