package com.google.protobuf.compiler.javamicro;

import com.google.protobuf.Descriptors;

/**
 * Created by clark on 2014/4/2.
 */
public class MessageGenerator {
    private Descriptors.Descriptor descriptor;
    private Params params;
    private FieldGeneratorMap fieldGenerators;

    public MessageGenerator(Descriptors.Descriptor descriptor, Params params) {
        this.descriptor = descriptor;
        this.params = params;
        fieldGenerators = new FieldGeneratorMap(descriptor, params);
    }

    public void generateStaticVariables(StringBuilder code) {
        // Generate static members for all nested types.
        for (Descriptors.Descriptor nestDescriptor : descriptor.getNestedTypes()) {
            // TODO(kenton):  Reuse MessageGenerator objects?
            new MessageGenerator(nestDescriptor, params).generateStaticVariables(code);
        }
    }

    public void generateStaticVariableInitializers(StringBuilder code) {
        // Generate static member initializers for all nested types.
        for (Descriptors.Descriptor nestDescriptor : descriptor.getNestedTypes()) {
            // TODO(kenton):  Reuse MessageGenerator objects?
            new MessageGenerator(nestDescriptor, params).generateStaticVariableInitializers(code);
        }

        if (descriptor.getExtensions().size() != 0) {
            System.err.println("Extensions not supported in MICRO_RUNTIME");
        }
    }

    public void generate(StringBuilder code) {
        String fileName = descriptor.getFile().getName();
        boolean isOwnFile = params.isJavaMultipleFiles(fileName)
                && descriptor.getContainingType() == null;
        if (descriptor.getExtensions().size() != 0) {
            System.err.println("Extensions not supported in MICRO_RUNTIME");
        }

        // Note: Fields (which will be emitted in the loop, below) may have the same names as fields in
        // the inner or outer class.  This causes Java warnings, but is not fatal, so we suppress those
        // warnings here in the class declaration.

    }
}
