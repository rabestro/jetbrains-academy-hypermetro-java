package metro.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import metro.entity.JsonStation;
import metro.entity.Line;
import metro.entity.Metro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class MetroLoader {
    public Metro load(final String fileName) throws IOException {
        final var schema = loadSchema(fileName);
        if (schema.isEmpty()) {
            throw new IllegalArgumentException("Incorrect file");
        }
        return createMetro(schema);
    }

    private Map<String, SortedMap<Integer, JsonStation>> loadSchema(final String fileName) throws IOException {
        final var path = Paths.get(fileName);
        if (Files.notExists(path)) {
            throw new IllegalArgumentException("Error! Such a file doesn't exist!");
        }
        final var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        final var gson = new Gson();

        return gson.fromJson(reader,
                new TypeToken<Map<String, SortedMap<Integer, JsonStation>>>() {
                }.getType());
    }

    private Metro createMetro(final Map<String, SortedMap<Integer, JsonStation>> schema) {
        final var lines = schema.entrySet().stream()
                .map(this::createLine)
                .collect(Collectors.toUnmodifiableSet());
        return new Metro(lines);
    }

    private Line createLine(final Map.Entry<String, SortedMap<Integer, JsonStation>> lineEntry) {
        final var metroLine = new Line(lineEntry.getKey());
        lineEntry.getValue().values().forEach(metroLine::append);
        return metroLine;
    }
}