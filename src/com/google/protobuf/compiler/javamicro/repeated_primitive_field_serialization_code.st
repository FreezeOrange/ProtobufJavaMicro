<if(variables.isPacked)>if (get<variables.capitalized_name>List().size() > 0) {
    output.writeRawVarint32(<variables.tag>);
    output.writeRawVarint32(<variables.name>MemoizedSerializedSize);
}
for (<variables.type> element : get<variables.capitalized_name>List()) {
    output.write<variables.capitalized_type>NoTag(element);
}<else><if(variables.isFastStringHandling)>for (byte[] element : <variables.name>Utf8_) {
    output.writeByteArray(<variables.number>, element);
}<else>for (<variables.type> element : get<variables.capitalized_name>List()) {
    output.write<variables.capitalized_type>(<variables.number>, element);
}<endif><endif>