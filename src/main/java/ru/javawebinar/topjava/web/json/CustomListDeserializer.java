package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.javawebinar.topjava.model.Meal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListDeserializer extends StdDeserializer<List<Meal>> {

    public CustomListDeserializer() {
        this(null);
    }

    protected CustomListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Meal> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        List<Meal> meals = new ArrayList<>();
        JsonNode node = jsonParser.readValueAsTree();
        if (node.isArray()) {
            for (final JsonNode objNode : node) {
                Meal meal = JacksonObjectMapper.getMapper().treeToValue(objNode, Meal.class);
                meals.add(meal);
            }
        }
        return meals;
    }
}
