package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/3/30.
 */
public class MessageFieldVariables extends AbstractFieldVariables {
    public String name;

    public String capitalized_name;

    public String number;

    public String type;

    public String group_or_message;

    public String message_name;

    public boolean is_group;

    private MessageFieldVariables() {}

    public static MessageFieldVariables create(Params params, Descriptors.FieldDescriptor descriptor) {
        MessageFieldVariables variables = new MessageFieldVariables();
        variables.name = Helpers.underscoresToCamelCase(descriptor);
        variables.capitalized_name = Helpers.UnderscoresToCapitalizedCamelCase(descriptor);
        variables.number = descriptor.getNumber() + "";
        variables.type = Helpers.getClassName(params, descriptor.getMessageType());
        variables.is_group = descriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP;
        variables.group_or_message = variables.is_group ? "Group" : "Message";
        variables.message_name = descriptor.getContainingType().getName();
        return variables;
    }

}
