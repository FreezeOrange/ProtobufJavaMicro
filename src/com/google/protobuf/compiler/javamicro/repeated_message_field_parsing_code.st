<variables.type> value = new <variables.type>();
<if(variables.is_group)>input.readGroup(value, <variables.number>);<else>input.readMessage(value);<endif>
add<variables.capitalized_name>(value);