<if(variables.isFastStringHandling)>if (has<variables.capitalized_name>()) {
    output.writeByteArray(<variables.number>, <variables.name>Utf8_);
}<else>if (has<variables.capitalized_name>()) {
    output.write<variables.capitalized_type>(<variables.number>, get<variables.capitalized_name>());
}<endif>