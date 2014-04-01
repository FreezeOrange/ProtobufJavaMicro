package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.WireFormat;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/27
 */
public class Helpers {

    public static String getBoxedPrimitiveTypeName(Descriptors.FieldDescriptor.JavaType javaType) {
        switch (javaType) {
            case INT:
                return "java.lang.Integer";
            case LONG:
                return "java.lang.Long";
            case FLOAT:
                return "java.lang.Float";
            case DOUBLE:
                return "java.lang.Double";
            case BOOLEAN:
                return "java.lang.Boolean";
            case STRING:
                return "java.lang.String";
            case BYTE_STRING:
                return "com.google.protobuf.micro.ByteStringMicro";
            case ENUM:
                return "java.lang.Integer";
            case MESSAGE:
                return null;
        }

        throw new IllegalArgumentException("No way to get here!");
    }

    public static String getDefaultValue(Params params, Descriptors.FieldDescriptor field) {
        // Switch on cpp_type since we need to know which default_value_* method
        // of FieldDescriptor to call.
        switch (field.getJavaType()) {
            case INT:
                if (field.hasDefaultValue()) {
                    return field.getDefaultValue().toString();
                } else {
                    return "0";
                }
            case BOOLEAN:
                if (field.hasDefaultValue()) {
                    return field.getDefaultValue().toString();
                } else {
                    return "false";
                }
            case LONG:
                if (field.hasDefaultValue()) {
                    return field.getDefaultValue() + "L";
                } else {
                    return "0L";
                }
            case FLOAT:
                if (field.hasDefaultValue()) {
                    return field.getDefaultValue() + "F";
                } else {
                    return "0F";
                }
            case DOUBLE:
                if (field.hasDefaultValue()) {
                    return field.getDefaultValue() + "D";
                } else {
                    return "0D";
                }
            case BYTE_STRING:
                if (field.hasDefaultValue()) {
                    return String.format("com.google.protobuf.micro.ByteStringMicro.copyFromUtf8(\"%s\")",
                            StringUtils.cEscape(field.getDefaultValue().toString()));
                } else {
                    return "com.google.protobuf.micro.ByteStringMicro.EMPTY";
                }
            case STRING:
                if (field.hasDefaultValue()) {
                    if (StringUtils.isAllAscii(field.getDefaultValue().toString())) {
                        return "\"" + StringUtils.cEscape(field.getDefaultValue().toString()) + "\"";
                    } else {
                        return String.format("com.google.protobuf.micro.Internal.stringDefaultValue(\"%s\")",
                                StringUtils.cEscape(field.getDefaultValue().toString()));
                    }
                } else {
                    return "\"\"";
                }
            case ENUM:
                return getClassName(params, field.getEnumType()) + "." + field.getDefaultValue().toString();
            case MESSAGE:
                return getClassName(params, field.getMessageType()) + ".getDefaultInstance()";
        }

        throw new IllegalArgumentException("No way to get here!");
    }

    public static String toJavaName(Params params, String name, boolean isClass,
                                    Descriptors.Descriptor parent, Descriptors.FileDescriptor file) {
        StringBuilder result = new StringBuilder();
        if (parent != null) {
            result.append(getClassName(params, parent));
        } else if (isClass && params.isJavaMultipleFiles(file.getName())) {
            result.append(getFileJavaPackage(params, file));
        } else {
            result.append(getClassName(params, file));
        }

        if (result.length() > 0) {
            result.append('.');
        }
        result.append(name);                    // TODO 如果 name 是 Java 的关键字怎么办
        return result.toString();
    }

    public static String getClassName(Params params, Descriptors.ServiceDescriptor descriptor) {
        return toJavaName(params, descriptor.getName(), true,
                null, descriptor.getFile());
    }

    public static String getClassName(Params params, Descriptors.Descriptor descriptor) {
        return toJavaName(params, descriptor.getName(), true,
                descriptor.getContainingType(), descriptor.getFile());
    }

    public static String getClassName(Params params, Descriptors.EnumDescriptor descriptor) {
        // An enum's class name is the enclosing message's class name or the outer
        // class name.
        Descriptors.Descriptor parent = descriptor.getContainingType();
        if (parent != null) {
            return getClassName(params, parent);
        } else {
            return getClassName(params, descriptor.getFile());
        }
    }

    /**
     * 和 {@link #getFileClassName(Params, com.google.protobuf.Descriptors.FileDescriptor)}
     * 最大的不同就是返回的类名是完整的，包含包名部分。
     *
     * @param params
     * @param file
     * @return
     */
    public static String getClassName(Params params, Descriptors.FileDescriptor file) {
        StringBuilder resultBuilder = new StringBuilder();
        String packageName = getFileJavaPackage(params, file);
        if (packageName.length() > 0) {
            resultBuilder.append(packageName).append(".");
        }
        resultBuilder.append(getFileClassName(params, file));
        return resultBuilder.toString();
    }

    /**
     * 如果 params 中存在 JavaOuterClassName 则使用 JavaOuterClassName，否则
     * 将 proto 文件的名字作为类的名字。
     * 注意：只返回类的名字，没有包含包名的部分。
     *
     * @param params
     * @param file
     * @return
     */
    public static String getFileClassName(Params params, Descriptors.FileDescriptor file) {
        String fileName = file.getName();
        if (params.hasJavaOuterClassName(fileName)) {
            return params.getJavaOuterClassName(fileName);
        } else {
            // Use the filename itself with underscores removed
            // and a CamelCase style name.
            String basename;
            int lastSlashIndex = fileName.lastIndexOf('/');
            if (lastSlashIndex == -1) {
                basename = fileName;
            } else {
                basename = fileName.substring(lastSlashIndex + 1);
            }
            return underscoresToCamelCaseImpl(basename, true);
        }
    }

    /**
     * 将输入的字符串切换为驼峰标志。
     * 如果 capNextLetter 为 true 则表示第一个字符起开始转换。
     *
     * @param input
     * @param capNextLetter
     * @return
     */
    public static String underscoresToCamelCaseImpl(String input, boolean capNextLetter) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if ('a' <= chars[i] && chars[i] <= 'z') {
                if (capNextLetter) {
                    result.append((char) (chars[i] + ('A' - 'a')));
                } else {
                    result.append(chars[i]);
                }
                capNextLetter = false;
            } else if ('A' <= chars[i] && chars[i] <= 'Z') {
                if (i == 0 && !capNextLetter) {
                    // Force first letter to lower-case unless explicitly told to
                    // capitalize it.
                    result.append((char) (chars[i] + ('a' - 'A')));
                } else {
                    // Capital letters after the first are left as-is.
                    result.append(chars[i]);
                }
                capNextLetter = false;
            }
        }
        return result.toString();
    }

    public static String underscoresToCamelCase(Descriptors.FieldDescriptor descriptor) {
        return underscoresToCamelCaseImpl(getFieldName(descriptor), false);
    }

    public static String UnderscoresToCapitalizedCamelCase(Descriptors.FieldDescriptor descriptor) {
        return underscoresToCamelCaseImpl(getFieldName(descriptor), true);
    }

    private static String getFieldName(Descriptors.FieldDescriptor descriptor) {
        if (descriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP) {
            return descriptor.getMessageType().getName();
        } else {
            return descriptor.getName();
        }
    }

    public static String underscoresToCamelCase(Descriptors.MethodDescriptor descriptor) {
        return underscoresToCamelCaseImpl(descriptor.getName(), false);
    }

    /**
     * 获取一个 proto 文件的 Java Package 的名字
     *
     * @param params
     * @param file
     * @return
     */
    public static String getFileJavaPackage(Params params, Descriptors.FileDescriptor file) {
        String fileName = file.getName();
        if (params.hasJavaPackage(fileName)) {
            return params.getJavaPackage(fileName);
        } else {
            String result = "";
            String aPackage = file.getPackage();
            if (aPackage != null && aPackage.trim().length() != 0) {
                result = aPackage.trim();
            }

            return result;
        }
    }

    public static int getTag(Descriptors.FieldDescriptor descriptor) {
        return WireFormat.makeTag(descriptor.getNumber(),
                getFieldType(descriptor.getType()).getWireType());
    }

    static int getTagSize(Descriptors.FieldDescriptor descriptor) {
        int result = CodedOutputStream.computeRawVarint32Size(
                WireFormat.makeTag(descriptor.getNumber(), getFieldType(descriptor.getType()).getWireType()));
        if (descriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP) {
            // Groups have both a start and an end tag.
            return result * 2;
        } else {
            return result;
        }
    }

    private static WireFormat.FieldType getFieldType(Descriptors.FieldDescriptor.Type type) {
        switch (type) {
            case BOOL:
                return WireFormat.FieldType.BOOL;
            case BYTES:
                return WireFormat.FieldType.BYTES;
            case DOUBLE:
                return WireFormat.FieldType.DOUBLE;
            case ENUM:
                return WireFormat.FieldType.ENUM;
            case FIXED32:
                return WireFormat.FieldType.FIXED32;
            case FIXED64:
                return WireFormat.FieldType.FIXED64;
            case FLOAT:
                return WireFormat.FieldType.FLOAT;
            case GROUP:
                return WireFormat.FieldType.GROUP;
            case INT32:
                return WireFormat.FieldType.INT32;
            case INT64:
                return WireFormat.FieldType.INT64;
            case MESSAGE:
                return WireFormat.FieldType.MESSAGE;
            case SFIXED32:
                return WireFormat.FieldType.SFIXED32;
            case SFIXED64:
                return WireFormat.FieldType.SFIXED64;
            case SINT32:
                return WireFormat.FieldType.SINT32;
            case SINT64:
                return WireFormat.FieldType.SINT64;
            case STRING:
                return WireFormat.FieldType.STRING;
            case UINT32:
                return WireFormat.FieldType.UINT32;
            case UINT64:
                return WireFormat.FieldType.UINT64;
        }

        throw new IllegalArgumentException("No way to get here!");
    }
}
