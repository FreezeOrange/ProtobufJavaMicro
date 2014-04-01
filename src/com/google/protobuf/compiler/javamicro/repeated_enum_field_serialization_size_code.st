{
    int dataSize = 0;
    for (java.lang.Integer element : get<variables.capitalized_name>List()) {
        dataSize += com.google.protobuf.micro.CodedOutputStreamMicro
                    .computeInt32SizeNoTag(element);
    }
    size += dataSize;
    <if(variables.is_packed)>if (get<variables.capitalized_name>List().size() != 0) {
        size += <variables.tag_size>;
        size += com.google.protobuf.micro.CodedOutputStreamMicro
                .computeRawVarint32Size(dataSize);
    }<else>size += <variables.tag_size> * get<variables.capitalized_name>List().size();<endif>
    <if(variables.is_packed)><variables.name>MemoizedSerializedSize = dataSize;<endif>
}