private java.util.List<Integer> <variables.name>_ =
                                java.util.Collections.emptyList();
public java.util.List<Integer> get<variables.capitalized_name>List() {
    return <variables.name>_;
}
public int get<variables.capitalized_name>Count() { return <variables.name>_.size(); }
public int get<variables.capitalized_name>(int index) {
    return <variables.name>_.get(index);
}
public <variables.message_name> set<variables.capitalized_name>(int index, int value) {
    <variables.name>_.set(index, value);
    return this;
}
public <variables.message_name> add<variables.capitalized_name>(int value) {
    if (<variables.name>_.isEmpty()) {
        <variables.name>_ = new java.util.ArrayList<java.lang.Integer>();
    }
    <variables.name>_.add(value);
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    <variables.name>_ = java.util.Collections.emptyList();
    return this;
}
<if(variables.is_packed)>private int <variables.name>MemoizedSerializedSize;<endif>