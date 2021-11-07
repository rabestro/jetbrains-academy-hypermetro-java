package metro.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MetroStation {
    private final StationID stationID;
    private Set<StationID> transfer = Set.of();
    private Set<StationID> next = Set.of();
    private Set<StationID> prev = Set.of();
    private int time;

    static MetroStation from(final String line, final JsonObject jsonStation) {
        final var name = jsonStation.get("name").getAsString();
        final var transfer = parseTransfer(jsonStation.get("transfer"));
        final var station = new MetroStation(new StationID(line, name));
        station.setTime(getTime(jsonStation));
        station.setTransfer(transfer);
        return station;
    }

    private static int getTime(final JsonObject jsonStation) {
        final var hasTime = jsonStation.has("time") && !jsonStation.get("time").isJsonNull();
        return hasTime ? jsonStation.get("time").getAsInt() : 1;
    }

    private static Set<StationID> parseTransfer(final JsonElement jsonElement) {
        final var transfer = new HashSet<StationID>();
        if (!jsonElement.isJsonNull()) {
            jsonElement.getAsJsonArray().forEach(element -> {
                final var jsonObject = element.getAsJsonObject();
                final var stationId = StationID.from(jsonObject);
                transfer.add(stationId);
            });
        }
        return transfer;
    }

    public Set<StationID> getNeighbors() {
        final var neighbors = new HashSet<StationID>();
        neighbors.addAll(next);
        neighbors.addAll(prev);
        neighbors.addAll(transfer);
        return neighbors;
    }
}
