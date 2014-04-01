<if(variables.isFastStringHandling)>if (has<variables.capitalized_name>()) {
    try {
        <variables.name>Utf8_ = <variables.name>_.getBytes("UTF-8");
    } catch (java.io.UnsupportedEncodingException e) {
        throw new RuntimeException("UTF-8 not supported.");
    }
    size += com.google.protobuf.micro.CodedOutputStreamMicro
            .computeByteArraySize(<variables.number>, <variables.name>Utf8_);
}<else>if (has<variables.capitalized_name>()) {
    size += com.google.protobuf.micro.CodedOutputStreamMicro
            .compute<variables.capitalized_type>Size(<variables.number>, get<variables.capitalized_name>());
}<endif>