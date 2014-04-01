package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/3/30.
 */
public class RepeatedMessageFieldGenerator extends FieldGenerator {
    private Descriptors.FieldDescriptor fieldDescriptor;
    private MessageFieldVariables messageFieldVariables;

    public RepeatedMessageFieldGenerator(Descriptors.FieldDescriptor fieldDescriptor, Params params) {
        super(params);
        this.fieldDescriptor = fieldDescriptor;
        messageFieldVariables = MessageFieldVariables.create(params, fieldDescriptor);
    }

    @Override
    public void generateMembers(StringBuilder code) {
        messageFieldVariables.codeGenerate(code, "repeated_message_field_members.st");
    }

    @Override
    public void generateMergingCode(StringBuilder code) {
        messageFieldVariables.codeGenerate(code, "repeated_message_field_merging_code.st");
    }

    @Override
    public void generateParsingCode(StringBuilder code) {
        messageFieldVariables.codeGenerate(code, "repeated_message_field_parsing_code.st");
    }

    @Override
    public void generateSerializationCode(StringBuilder code) {
        messageFieldVariables.codeGenerate(code, "repeated_message_field_serialization_code.st");
    }

    @Override
    public void generateSerializedSizeCode(StringBuilder code) {
        messageFieldVariables.codeGenerate(code, "repeated_message_field_serialization_size_code.st");
    }

    @Override
    public String getBoxedType() {
        return Helpers.getClassName(params, fieldDescriptor.getMessageType());
    }
}
