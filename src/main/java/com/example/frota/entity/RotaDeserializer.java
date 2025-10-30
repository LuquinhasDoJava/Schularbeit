package com.example.frota.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class RotaDeserializer extends StdDeserializer<Rota> {

    public RotaDeserializer() {
        this(null);
    }

    public RotaDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Rota deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {

        JsonNode node = parser.getCodec().readTree(parser);

        String origem = node.get("origin_addresses").get(0).asText();
        String destino = node.get("destination_addresses").get(0).asText();

        JsonNode element = node.get("rows").get(0)
                .get("elements").get(0);

        String distancia = element.get("distance").get("text").asText();
        String tempo = element.get("duration").get("text").asText();

        return new Rota(origem, destino, distancia, tempo);
    }
}