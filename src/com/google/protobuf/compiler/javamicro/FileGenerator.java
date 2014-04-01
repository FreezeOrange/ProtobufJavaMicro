package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;
import com.google.protobuf.FileDescriptorProto;
import com.google.protobuf.Message;
import com.google.protobuf.UnknownFieldSet;

import java.util.Map;

/**
 * Created by clark on 2014/4/1.
 */
public class FileGenerator {
    private Descriptors.FileDescriptor descriptor;
    private Params params;
    private FileVariables variables;

    public FileGenerator(Descriptors.FileDescriptor descriptor, Params params) {
        this.descriptor = descriptor;
        this.params = params;
        variables = FileVariables.create(params, descriptor);
    }

    public void generate(StringBuilder code) {
        // We don't import anything because we refer to all classes by their
        // fully-qualified names in the generated source.
        variables.codeGenerate(code, "file_generate_code.st");
    }

    public String validate() {
        StringBuilder error = new StringBuilder();
        FileDescriptorProto fileDescriptorProto = descriptor.toProto();
        if (usesExtensions(fileDescriptorProto)) {
            error.append(descriptor.getName()).append(" : ")
                    .append("Java MICRO_RUNTIME does not support extensions");
        } else if (descriptor.getServices().size() > 0) {
            error.append(descriptor.getName()).append(" : ")
                    .append("Java MICRO_RUNTIME does not support services");
        }
        if (!Helpers.isOuterClassNeeded(params, descriptor)) return null;

        // Check whether legacy javamicro generator would omit the outer class.
        if (!params.hasJavaOuterClassName(descriptor.getName())
                && descriptor.getMessageTypes().size() == 1
                && descriptor.getEnumTypes().size() == 0
                && descriptor.getExtensions().size() == 0) {
            System.out.println("INFO: " + descriptor.getName() + ":");
            System.out.print("Javamicro generator has changed to align with java generator. ");
            System.out.print("An outer class will be created for this file and the single message ");
            System.out.print("in the file will become a nested class. Use java_multiple_files to ");
            System.out.print("skip generating the outer class, or set an explicit ");
            System.out.println("java_outer_classname to suppress this message.");
        }

        // Check that no class name matches the file's class name.  This is a common
        // problem that leads to Java compile errors that can be hard to understand.
        // It's especially bad when using the java_multiple_files, since we would
        // end up overwriting the outer class with one of the inner ones.
        boolean found_conflict = false;
        for (Descriptors.Descriptor message : descriptor.getMessageTypes()) {
            if (message.getName().equals(variables.classname)) {
                found_conflict = true;
                break;
            }
        }
        if (found_conflict) {
            error.append(descriptor.getName())
                    .append(": Cannot generate Java output because the file's outer class name, \"")
                    .append(variables.classname)
                    .append("\", matches the name of one of the types declared inside it.  ")
                    .append("Please either rename the type or use the java_outer_classname ")
                    .append("option to specify a different outer class name for the .proto file.");
        }
        return error.length() == 0 ? null : error.toString();
    }

    private static boolean usesExtensions(Message message) {
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (unknownFields != null && unknownFields.asMap().size() > 0) {
            return true;
        }

        Map<Descriptors.FieldDescriptor, Object> allFields = message.getAllFields();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
            Descriptors.FieldDescriptor field = entry.getKey();
            if (field.isExtension()) return true;
            if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (field.isRepeated()) {
                    int count = message.getRepeatedFieldCount(field);
                    for (int i = 0; i < count; ++i) {
                        Message subMessage = (Message) message.getRepeatedField(field, i);
                        if (usesExtensions(subMessage)) return true;
                    }
                } else {
                    Message subMessage = (Message) entry.getValue();
                    if (usesExtensions(subMessage)) return true;
                }
            }
        }

        return false;
    }
}
