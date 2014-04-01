package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/27
 */
public class EnumGenerator {
    private Descriptors.EnumDescriptor enumDescriptor;
    private Params params;

    /**
     * The proto language allows multiple enum constants to have the same numeric
     * value.  Java, however, does not allow multiple enum constants to be
     * considered equivalent.  We treat the first defined constant for any
     * given numeric value as "canonical" and the rest as aliases of that
     * canonical value.
     */
    private List<Descriptors.EnumValueDescriptor> canonicalValues = new ArrayList<Descriptors.EnumValueDescriptor>();

    private static class Alias {
        public Descriptors.EnumValueDescriptor value;
        public Descriptors.EnumValueDescriptor canonicalValue;
    }

    private List<Alias> aliases = new ArrayList<Alias>();

    public EnumGenerator(Descriptors.EnumDescriptor enumDescriptor, Params params) {
        this.enumDescriptor = enumDescriptor;
        this.params = params;

        List<Descriptors.EnumValueDescriptor> values = enumDescriptor.getValues();
        for (int i = 0, len = values.size(); i < len; ++i) {
            Descriptors.EnumValueDescriptor value = values.get(i);
            Descriptors.EnumValueDescriptor canonical_value = enumDescriptor.findValueByNumber(value.getNumber());
            if (value == canonical_value) {
                canonicalValues.add(value);
            } else {
                Alias alias = new Alias();
                alias.value = value;
                alias.canonicalValue = canonical_value;
                aliases.add(alias);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder generateCode = new StringBuilder();
        generateCode.append(String.format("// enum %s\n", enumDescriptor.getName()));
        for (Descriptors.EnumValueDescriptor descriptor : canonicalValues) {
            generateCode.append(String.format("public static final int %s = %d;\n",
                    descriptor.getName(), descriptor.getNumber()));
        }
        for (Alias alias : aliases) {
            generateCode.append(String.format("public static final int %s = %s;\n",
                    alias.value.getName(), alias.canonicalValue.getName()));
        }
        generateCode.append("\n");
        return generateCode.toString();
    }
}
