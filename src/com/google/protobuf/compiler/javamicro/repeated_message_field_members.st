private java.util.List\<<variables.type>\> <variables.name>_ =
                                            java.util.Collections.emptyList();
public java.util.List\<<variables.type>\> get<variables.capitalized_name>List() {
    return <variables.name>_;
}
public int get<variables.capitalized_name>Count() { return <variables.name>_.size(); }
public <variables.type> get<variables.capitalized_name>(int index) {
    return <variables.name>_.get(index);
}
public <variables.message_name> set<variables.capitalized_name>(int index, <variables.type> value) {
    if (value == null) {
        throw new NullPointerException();
    }
    <variables.name>_.set(index, value);
    return this;
}
public <variables.message_name> add<variables.capitalized_name>(<variables.type> value) {
    if (value == null) {
        throw new NullPointerException();
    }
    if (<variables.name>_.isEmpty()) {
        <variables.name>_ = new java.util.ArrayList\<<variables.type>\>();
    }
    <variables.name>_.add(value);
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    <variables.name>_ = java.util.Collections.emptyList();
    return this;
}