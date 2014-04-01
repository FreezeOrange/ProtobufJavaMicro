package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/31
 */
public class PrimitiveFieldVariables extends AbstractFieldVariables {
    public String name;
    public String capitalized_name;
    public String number;
    public String type;
    public String defaultValue;
    public String boxed_type;
    public String capitalized_type;
    public String tag;
    public String tag_size;
    public String null_check;
    public String fixed_size;
    public String message_name;
    public boolean isFastStringHandling;
    public boolean isVariableLenType;
    public boolean isRenferenceType;
    public boolean isPacked;
    public boolean fixedSizeEqualNegOne;

    public static PrimitiveFieldVariables create(Descriptors.FieldDescriptor descriptor, Params params) {
        PrimitiveFieldVariables variables = new PrimitiveFieldVariables();
        variables.name = Helpers.underscoresToCamelCase(descriptor);
        variables.capitalized_name = Helpers.UnderscoresToCapitalizedCamelCase(descriptor);
        variables.number = descriptor.getNumber() + "";
        variables.type = getPrimitiveTypeName(descriptor.getJavaType());
        variables.defaultValue = Helpers.getDefaultValue(params, descriptor);
        variables.boxed_type = Helpers.getBoxedPrimitiveTypeName(descriptor.getJavaType());
        variables.capitalized_type = getCapitalizedType(descriptor);
        variables.tag = Helpers.getTag(descriptor) + "";
        variables.tag_size = Helpers.getTagSize(descriptor) + "";
        if (isReferenceType(descriptor.getJavaType())) {
            variables.null_check = "if (value == null) {\n"
                    + "    throw new NullPointerException();\n"
                    + "}\n";
        } else {
            variables.null_check = "";
        }
        int fixedSize = fixedSize(descriptor.getType());
        variables.fixedSizeEqualNegOne = fixedSize == -1;
        if (fixedSize != -1) {
            variables.fixed_size = fixedSize + "";
        }
        variables.message_name = descriptor.getContainingType().getName();
        variables.isFastStringHandling = isFastStringHandling(descriptor, params);
        variables.isVariableLenType = isVariableLenType(descriptor.getJavaType());
        variables.isRenferenceType = isReferenceType(descriptor.getJavaType());
        variables.isPacked = descriptor.isPacked();
        return variables;
    }

    private PrimitiveFieldVariables () {}

    private static String getPrimitiveTypeName(Descriptors.FieldDescriptor.JavaType type) {
        switch (type) {
            case INT:
                return "int";
            case LONG:
                return "long";
            case FLOAT:
                return "float";
            case DOUBLE:
                return "double";
            case BOOLEAN:
                return "boolean";
            case STRING:
                return "java.lang.String";
            case BYTE_STRING:
                return "com.google.protobuf.micro.ByteStringMicro";
            case ENUM:
                return null;
            case MESSAGE:
                return null;

            // No default because we want the compiler to complain if any new
            // JavaTypes are added.
        }

        throw new IllegalArgumentException("Can't get here.");
    }

    private static boolean isReferenceType(Descriptors.FieldDescriptor.JavaType type) {
        switch (type) {
            case INT:
                return false;
            case LONG:
                return false;
            case FLOAT:
                return false;
            case DOUBLE:
                return false;
            case BOOLEAN:
                return false;
            case STRING:
                return true;
            case BYTE_STRING:
                return true;
            case ENUM:
                return false;
            case MESSAGE:
                return true;

            // No default because we want the compiler to complain if any new
            // JavaTypes are added.
        }

        throw new IllegalArgumentException("Can't get here.");
    }

    private static String getCapitalizedType(Descriptors.FieldDescriptor field) {
        switch (field.getType()) {
            case INT32:
                return "Int32";
            case UINT32:
                return "UInt32";
            case SINT32:
                return "SInt32";
            case FIXED32:
                return "Fixed32";
            case SFIXED32:
                return "SFixed32";
            case INT64:
                return "Int64";
            case UINT64:
                return "UInt64";
            case SINT64:
                return "SInt64";
            case FIXED64:
                return "Fixed64";
            case SFIXED64:
                return "SFixed64";
            case FLOAT:
                return "Float";
            case DOUBLE:
                return "Double";
            case BOOL:
                return "Bool";
            case STRING:
                return "String";
            case BYTES:
                return "Bytes";
            case ENUM:
                return "Enum";
            case GROUP:
                return "Group";
            case MESSAGE:
                return "Message";

            // No default because we want the compiler to complain if any new
            // types are added.
        }

        throw new IllegalArgumentException("Can't get here.");
    }

    private static int fixedSize(Descriptors.FieldDescriptor.Type type) {
        switch (type) {
            case INT32:
                return -1;
            case INT64:
                return -1;
            case UINT32:
                return -1;
            case UINT64:
                return -1;
            case SINT32:
                return -1;
            case SINT64:
                return -1;
            case FIXED32:
                return 4; //WireFormatLite::kFixed32Size;
            case FIXED64:
                return 8; //WireFormatLite::kFixed64Size;
            case SFIXED32:
                return 4; //WireFormatLite::kSFixed32Size;
            case SFIXED64:
                return 8; //WireFormatLite::kSFixed64Size;
            case FLOAT:
                return 4; //WireFormatLite::kFloatSize;
            case DOUBLE:
                return 8; //WireFormatLite::kDoubleSize;

            case BOOL:
                return 1; //WireFormatLite::kBoolSize;
            case ENUM:
                return -1;

            case STRING:
                return -1;
            case BYTES:
                return -1;
            case GROUP:
                return -1;
            case MESSAGE:
                return -1;

            // No default because we want the compiler to complain if any new
            // types are added.
        }

        throw new IllegalArgumentException("Can't get here.");
    }

    private static boolean isVariableLenType(Descriptors.FieldDescriptor.JavaType type) {
        switch (type) {
            case INT:
                return false;
            case LONG:
                return false;
            case FLOAT:
                return false;
            case DOUBLE:
                return false;
            case BOOLEAN:
                return false;
            case STRING:
                return true;
            case BYTE_STRING:
                return true;
            case ENUM:
                return false;
            case MESSAGE:
                return true;

            // No default because we want the compiler to complain if any new
            // JavaTypes are added.
        }

        throw new IllegalArgumentException("Can't get here.");
    }

    private static boolean isFastStringHandling(Descriptors.FieldDescriptor descriptor, Params params) {
        return ((params.getOptimization() == Params.Optimization.JAVAMICRO_OPT_SPEED)
                && (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING));
    }
}
