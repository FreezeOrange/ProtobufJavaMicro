private boolean has<variables.capitalized_name>;
private <variables.type> <variables.name>_ = <variables.defaultValue>;
public <variables.type> get<variables.capitalized_name>() { return <variables.name>_; }
public boolean has<variables.capitalized_name>() { return has<variables.capitalized_name>; }
<if(variables.isFastStringHandling)>private byte [] <variables.name>Utf8_ = null;
public <variables.message_name> set<variables.capitalized_name>(<variables.type> value) {
    has<variables.capitalized_name> = true;
    <variables.name>_ = value;
    <variables.name>Utf8_ = null;
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    has<variables.capitalized_name> = false;
    <variables.name>_ = <variables.defaultValue>;
    <variables.name>Utf8_ = null;
    return this;
}<else><if(variables.isVariableLenType)>public <variables.message_name> set<variables.capitalized_name>(<variables.type> value) {
    has<variables.capitalized_name> = true;
    <variables.name>_ = value;
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    has<variables.capitalized_name> = false;
    <variables.name>_ = <variables.defaultValue>;
    return this;
}<else>public <variables.message_name> set<variables.capitalized_name>(<variables.type> value) {
    has<variables.capitalized_name> = true;
    <variables.name>_ = value;
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    has<variables.capitalized_name> = false;
    <variables.name>_ = <variables.defaultValue>;
    return this;
}<endif><endif>