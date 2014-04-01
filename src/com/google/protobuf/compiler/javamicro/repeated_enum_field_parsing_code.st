<if(variables.is_packed)>int length = input.readRawVarint32();
int oldLimit = input.pushLimit(length);
while(input.getBytesUntilLimit() > 0) {<endif>
    add<variables.capitalized_name>(input.readInt32());
<if(variables.is_packed)>}
input.popLimit(oldLimit);<endif>