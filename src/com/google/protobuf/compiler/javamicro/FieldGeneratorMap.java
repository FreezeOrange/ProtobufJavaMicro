package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by clark on 2014/3/30.
 */
public class FieldGeneratorMap {
    private Descriptors.Descriptor descriptor;
    private Params params;

    private List<FieldGenerator> fieldGenerators;
    private List<FieldGenerator> extensionGenerators;

    public FieldGeneratorMap(Descriptors.Descriptor descriptor, Params params) {
        this.descriptor = descriptor;
        this.params = params;

        List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
        fieldGenerators = fields == null || fields.size() == 0 ? Collections.<FieldGenerator>emptyList()
                : new ArrayList<FieldGenerator>(fields.size());
        List<Descriptors.FieldDescriptor> extensions = descriptor.getExtensions();
        extensionGenerators = extensions == null || extensions.size() == 0 ? Collections.<FieldGenerator>emptyList()
                : new ArrayList<FieldGenerator>(extensions.size());

        int size = fieldGenerators.size();
        for (int i = 0; i < size; ++i) {
            fieldGenerators.add(makeGenerator(descriptor.getFields().get(i), params));
        }

        size = extensionGenerators.size();
        for (int i = 0; i < size; ++i) {
            extensionGenerators.add(makeGenerator(descriptor.getExtensions().get(i), params));
        }
    }

    public FieldGenerator get(Descriptors.FieldDescriptor fieldDescriptor) {
        return fieldGenerators.get(fieldDescriptor.getIndex());
    }

    public FieldGenerator getExtension(int index) {
        return extensionGenerators.get(index);
    }

    private static FieldGenerator makeGenerator(Descriptors.FieldDescriptor fieldDescriptor, Params params) {
        Descriptors.FieldDescriptor.JavaType javaType = fieldDescriptor.getJavaType();
        if (fieldDescriptor.isRepeated()) {
            switch (javaType) {
                case MESSAGE:
                    return new RepeatedMessageFieldGenerator(fieldDescriptor, params);

                case ENUM:
                    return new RepeatedEnumFieldGenerator(fieldDescriptor, params);

                default:
                    return new RepeatedPrimitiveFieldGenerator(fieldDescriptor, params);
            }
        } else {
            switch (javaType) {
                case MESSAGE:
                    return new MessageFieldGenerator(fieldDescriptor, params);

                case ENUM:
                    return new EnumFieldGenerator(fieldDescriptor, params);

                default:
                    return new PrimitiveFieldGenerator(fieldDescriptor, params);
            }
        }
    }
}
