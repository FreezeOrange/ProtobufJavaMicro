package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;
import com.google.protobuf.WireFormat;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/31
 */
public class EnumFieldVariables extends AbstractFieldVariables {
    public String name;
    public String capitalized_name;
    public String number;
    public String type;
    public String defaultValue;
    public String tag;
    public String tag_size;
    public String message_name;
    public boolean is_packed;

    private EnumFieldVariables() {}

    public static EnumFieldVariables create(Params params, Descriptors.FieldDescriptor descriptor) {
        EnumFieldVariables variables = new EnumFieldVariables();
        variables.name = Helpers.underscoresToCamelCase(descriptor);
        variables.capitalized_name = Helpers.UnderscoresToCapitalizedCamelCase(descriptor);
        variables.number = descriptor.getNumber() + "";
        variables.type = "int";
        variables.defaultValue = Helpers.getDefaultValue(params, descriptor);
        variables.tag = Helpers.getTag(descriptor) + "";
        variables.tag_size = Helpers.getTagSize(descriptor) + "";
        variables.message_name = descriptor.getContainingType().getName();
        variables.is_packed = descriptor.getOptions().hasPacked();
        return variables;
    }

}
