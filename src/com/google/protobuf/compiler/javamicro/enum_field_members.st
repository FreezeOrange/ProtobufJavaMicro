private boolean has<variables.capitalized_name>;
private int <variables.name>_ = <variables.defaultValue>;
public boolean has<variables.capitalized_name>() { return has<variables.capitalized_name>; }
public int get<variables.capitalized_name>() { return <variables.name>_; }
public <variables.message_name> set<variables.capitalized_name>(int value) {
    has<variables.capitalized_name> = true;
    <variables.name>_ = value;
    return this;
}
public <variables.message_name> clear<variables.capitalized_name>() {
    has<variables.capitalized_name> = false;
    <variables.name>_ = <variables.defaultValue>;
    return this;
}