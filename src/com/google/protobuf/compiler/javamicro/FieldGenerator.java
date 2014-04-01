package com.google.protobuf.compiler.javamicro;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/27
 */
public abstract class FieldGenerator {
    protected Params params;

    protected FieldGenerator(Params params) {
        this.params = params;
    }

    public abstract void generateMembers(StringBuilder code);

    public abstract void generateMergingCode(StringBuilder code);

    public abstract void generateParsingCode(StringBuilder code);

    public abstract void generateSerializationCode(StringBuilder code);

    public abstract void generateSerializedSizeCode(StringBuilder code);

    public abstract String getBoxedType();
}
