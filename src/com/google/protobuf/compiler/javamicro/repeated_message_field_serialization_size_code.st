for (<variables.type> element : get<variables.capitalized_name>List()) {
    size += com.google.protobuf.micro.CodedOutputStreamMicro
                    .compute<variables.group_or_message>Size(<variables.number>, element);
}