<if(variables.isFastStringHandling)>private java.util.List\<<variables.type>\> <variables.name>_ =
        java.util.Collections.emptyList();
public java.util.List\<<variables.type>\> get<variables.capitalized_name>List() {
    return <variables.name>_;
}
private java.util.List\<byte[]\> <variables.name>Utf8_ = null;
public int get<variables.capitalized_name>Count() { return <variables.name>_.size(); }
public <variables.type> get<variables.capitalized_name>(int index) {
    return <variables.name>_.get(index);
}
public <variables.message_name> set<variables.capitalized_name>(int index, <variables.type> value) {
    <variables.null_check>
    <variables.name>_.set(index, value);
    <variables.name>Utf8_ = null;
    return this;
}
public <variables.message_name> add<variables.capitalized_name>(<variables.type> value) {
    <variables.null_check>
    if (<variables.name>_.isEmpty()) {
        <variables.name>_ = new java.util.ArrayList\<<variables.type>\>();
    }
    <variables.name>_.add(value);
    <variables.name>Utf8_ = null;
    return this;
}
public <variables.message_name> add<variables.capitalized_name>(<variables.type> value) {
    <variables.null_check>
    if (<variables.name>_.isEmpty()) {
        <variables.name>_ = new java.util.ArrayList\<<variables.type>\>();
    }
    <variables.name>_.add(value);
    <variables.name>Utf8_ = null;
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    <variables.name>_ = java.util.Collections.emptyList();
    <variables.name>Utf8_ = null;
    return this;
}<else>private java.util.List\<<variables.boxed_type>\> <variables.name>_ =
        java.util.Collections.emptyList();
public java.util.List\<<variables.boxed_type>\> get<variables.capitalized_name>List() {
    return <variables.name>_;
}
public int get<variables.capitalized_name>Count() { return <variables.name>_.size(); }
public <variables.type> get<variables.capitalized_name>(int index) {
    return <variables.name>_.get(index);
}
public <variables.message_name> set<variables.capitalized_name>(int index, <variables.type> value) {
    <variables.null_check>
    <variables.name>_.set(index, value);
    return this;
}
public <variables.message_name> add<variables.capitalized_name>(<variables.type> value) {
    <variables.null_check>
    if (<variables.name>_.isEmpty()) {
        <variables.name>_ = new java.util.ArrayList\<<variables.boxed_type>\>();
    }
    <variables.name>_.add(value);
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    <variables.name>_ = java.util.Collections.emptyList();
    return this;
}<endif><if(variables.isPacked)>private int <variables.name>MemoizedSerializedSize;<endif>