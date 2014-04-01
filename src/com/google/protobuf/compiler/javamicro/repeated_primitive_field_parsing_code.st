<if(variables.isPacked)>int length = input.readRawVarint32();
int limit = input.pushLimit(length);
while (input.getBytesUntilLimit() > 0) {
    add<variables.capitalized_name>(input.read<variables.capitalized_type>());
}
input.popLimit(limit);<else>add<variables.capitalized_name>(input.read<variables.capitalized_type>());<endif>