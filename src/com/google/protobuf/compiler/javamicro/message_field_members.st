private boolean has<variables.capitalized_name>;
private <variables.type> <variables.name>_ = null;
public boolean has<variables.capitalized_name>() { return has<variables.capitalized_name>; }
public <variables.type> get<variables.capitalized_name>() { return <variables.name>_; }
public <variables.message_name> set<variables.capitalized_name>(<variables.type> value) {
    if (value == null) {
        throw new NullPointerException();
    }
    has<variables.capitalized_name> = true;
    <variables.name>_ = value;
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    has<variables.capitalized_name> = false;
    <variables.name>_ = null;
    return this;
}