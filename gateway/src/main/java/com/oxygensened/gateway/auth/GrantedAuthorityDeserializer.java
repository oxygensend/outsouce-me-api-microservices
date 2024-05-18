package com.oxygensened.gateway.auth;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityDeserializer extends StdDeserializer<List<SimpleGrantedAuthority>>
{
    protected GrantedAuthorityDeserializer() {
        super(GrantedAuthority.class);
    }

    @Override
    public List<SimpleGrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode tree = jsonParser.getCodec().readTree(jsonParser);
        List<JsonNode> authorities = tree.findValues("authority");

        return authorities.stream().map( authority -> new SimpleGrantedAuthority(authority.toString())).toList();
    }
}
