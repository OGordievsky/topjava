package ru.javawebinar.topjava.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.javawebinar.topjava.model.Meal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomListSerializer extends StdSerializer<List<Meal>> {

    public CustomListSerializer(Class<List<Meal>> listClass) {
        super(listClass);
    }

    public CustomListSerializer() {
        this(null);
    }

    @Override
    public void serialize(List<Meal> meals, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<Meal> ids = new ArrayList<>();
        for (Meal meal : meals) {
            ids.add(new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        }
        jsonGenerator.writeObject(ids);
    }
}
