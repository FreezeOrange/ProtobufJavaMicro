package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/3/30.
 */
public class PrimitiveFieldGenerator extends FieldGenerator {
    public PrimitiveFieldGenerator(Descriptors.FieldDescriptor fieldDescriptor, Params params) {
        super(params);
    }

    @Override
    public void generateMembers(StringBuilder code) {

    }

    @Override
    public void generateMergingCode(StringBuilder code) {

    }

    @Override
    public void generateParsingCode(StringBuilder code) {

    }

    @Override
    public void generateSerializationCode(StringBuilder code) {

    }

    @Override
    public void generateSerializedSizeCode(StringBuilder code) {

    }

    @Override
    public String getBoxedType() {
        return null;
    }
}
