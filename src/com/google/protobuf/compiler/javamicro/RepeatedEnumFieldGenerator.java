package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/3/30.
 */
public class RepeatedEnumFieldGenerator extends FieldGenerator {
    private Descriptors.FieldDescriptor descriptor;
    private EnumFieldVariables variables;

    public RepeatedEnumFieldGenerator(Descriptors.FieldDescriptor fieldDescriptor, Params params) {
        super(params);
        descriptor = fieldDescriptor;
        variables = EnumFieldVariables.create(params, fieldDescriptor);

        if (fieldDescriptor.getOptions().hasPacked()) {
            System.err.println("MicroRuntime does not support packed!");
        }
    }

    @Override
    public void generateMembers(StringBuilder code) {
        variables.codeGenerate(code, "repeated_enum_field_members.st");
    }

    @Override
    public void generateMergingCode(StringBuilder code) {
        variables.codeGenerate(code, "repeated_enum_field_merging_code.st");
    }

    @Override
    public void generateParsingCode(StringBuilder code) {
        variables.codeGenerate(code, "repeated_enum_field_parsing_code.st");
    }

    @Override
    public void generateSerializationCode(StringBuilder code) {
        variables.codeGenerate(code, "repeated_enum_field_serialization_code.st");
    }

    @Override
    public void generateSerializedSizeCode(StringBuilder code) {
        variables.codeGenerate(code, "repeated_enum_field_serialization_size_code.st");
    }

    @Override
    public String getBoxedType() {
        return Helpers.getClassName(params, descriptor.getEnumType());
    }
}
