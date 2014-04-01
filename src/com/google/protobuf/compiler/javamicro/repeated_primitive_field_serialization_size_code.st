{
    int dataSize = 0;
    <if(variables.fixedSizeEqualNegOne)><if(variables.isFastStringHandling)><variables.name>Utf8_ = new java.util.ArrayList\<byte[]\>();
    byte[] bytes = null;
    int sizeArray = get<variables.capitalized_name>List().size();
    for (int i = 0; i < sizeArray; i++) {
        <variables.type> element = get<variables.capitalized_name>(i);
        try {
            bytes = element.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }
        <variables.name>Utf8_.add(bytes);
        dataSize += com.google.protobuf.micro.CodedOutputStreamMicro
                    .computeByteArraySizeNoTag(bytes);
    }<else>for (<variables.type> element : get<variables.capitalized_name>List()) {
        dataSize += com.google.protobuf.micro.CodedOutputStreamMicro
                    .compute<variables.capitalized_type>SizeNoTag(element);
    }<endif><else>dataSize = <variables.fixed_size> * get<variables.capitalized_name>List().size();<endif>
    size += dataSize;
    <if(variables.isPacked)>if (!get<variables.capitalized_name>List().isEmpty()) {
        size += <variables.tag_size>;
        size += com.google.protobuf.micro.CodedOutputStreamMicro
                .computeInt32SizeNoTag(dataSize);
    }<else>size += <variables.tag_size> * get<variables.capitalized_name>List().size();<endif>
    <if(variables.isPacked)><variables.name>MemoizedSerializedSize = dataSize;<endif>
}