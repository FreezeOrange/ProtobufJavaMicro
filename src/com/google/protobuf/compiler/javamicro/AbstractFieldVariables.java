package com.google.protobuf.compiler.javamicro;

import org.stringtemplate.v4.ST;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/31
 */
public abstract class AbstractFieldVariables {
    private Map<String, String> cachedTemplate = new HashMap<String, String>();

    public void codeGenerate(StringBuilder code, String resource) {
        try {
            if (!cachedTemplate.containsKey(resource)) {
                cachedTemplate.put(resource, STUtils.newStringTemplate(resource));
            }
            ST st = new ST(cachedTemplate.get(resource));
            st.add("variables", this);
            code.append(st.render()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
