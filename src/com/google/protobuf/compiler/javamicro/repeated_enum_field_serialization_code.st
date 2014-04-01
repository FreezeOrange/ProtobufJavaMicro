<if(variables.is_packed)>if (get<variables.capitalized_name>List().size() > 0) {
    output.writeRawVarint32(<variables.tag>);
    output.writeRawVarint32(<variables.name>MemoizedSerializedSize);
}
for (<variables.type> element : get<variables.capitalized_name>List()) {
    output.writeRawVarint32(element.getNumber());
}
<else>for (java.lang.Integer element : get<variables.capitalized_name>List()) {
    output.writeInt32(<variables.number>, element);
}<endif>