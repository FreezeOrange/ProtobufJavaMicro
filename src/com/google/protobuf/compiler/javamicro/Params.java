package com.google.protobuf.compiler.javamicro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Date: 2014/3/27
 */
public class Params {
    private String baseName;
    private Map<String, String> javaPackages;
    private Map<String, String> javaOuterClassNames;
    private Set<String> isMultipleFilesFileSet;
    private Optimization optimization;
    private MultipleFiles overrideMultipleFiles;
    private boolean useVector;

    public Params(String baseName) {
        this.baseName = baseName;
        javaPackages = new HashMap<String, String>(64);
        javaOuterClassNames = new HashMap<String, String>(64);
        isMultipleFilesFileSet = new HashSet<String>();
        optimization = Optimization.getDefault();
        overrideMultipleFiles = MultipleFiles.JAVAMICRO_MUL_UNSET;
        useVector = false;
    }

    public String getBaseName() {
        return baseName;
    }

    public boolean hasJavaPackage(String fileName) {
        return javaPackages.containsKey(fileName);
    }

    public String getJavaPackage(String fileName) {
        return javaPackages.get(fileName);
    }

    public void setJavaPackage(String fileName, String javaPackage) {
        if (fileName != null && javaPackage != null) {
            javaPackages.put(fileName, javaPackage);
        }
    }

    public boolean hasJavaOuterClassName(String fileName) {
        return javaOuterClassNames.containsKey(fileName);
    }

    public String getJavaOuterClassName(String fileName) {
        return javaOuterClassNames.get(fileName);
    }

    public void setJavaOuterClassNames(String fileName, String javaOuterClassname) {
        if (fileName != null && javaOuterClassname != null) {
            javaOuterClassNames.put(fileName, javaOuterClassname);
        }
    }

    public Optimization getOptimization() {
        return optimization;
    }

    public void setOptimization(Optimization optimization) {
        if (optimization == null) {
            this.optimization = Optimization.getDefault();
        } else {
            this.optimization = optimization;
        }
    }

    public MultipleFiles getOverrideMultipleFiles() {
        return overrideMultipleFiles;
    }

    public void setOverrideMultipleFiles(boolean value) {
        this.overrideMultipleFiles = value ? MultipleFiles.JAVAMICRO_MUL_TRUE
                : MultipleFiles.JAVAMICRO_MUL_FALSE;
    }

    public void clearOverrideMultipleFiles() {
        this.overrideMultipleFiles = MultipleFiles.JAVAMICRO_MUL_UNSET;
    }

    public void setJavaMultipleFiles(String fileName, boolean value) {
        if (value) {
            isMultipleFilesFileSet.add(fileName);
        } else {
            isMultipleFilesFileSet.remove(fileName);
        }
    }

    public boolean isJavaMultipleFiles(String fileName) {
        return isMultipleFilesFileSet.contains(fileName);
    }

    public boolean isUseVector() {
        return useVector;
    }

    public void setUseVector(boolean useVector) {
        this.useVector = useVector;
    }

    /**
     * Created with IntelliJ IDEA.
     * Date: 2014/3/27
     */
    public static enum MultipleFiles {
        JAVAMICRO_MUL_UNSET, JAVAMICRO_MUL_FALSE, JAVAMICRO_MUL_TRUE
    }

    /**
     * Created with IntelliJ IDEA.
     * Date: 2014/3/27
     */
    public static enum Optimization {
        JAVAMICRO_OPT_SPEED, JAVAMICRO_OPT_SPACE;

        public static Optimization getDefault() {
            return JAVAMICRO_OPT_SPEED;
        }
    }
}
