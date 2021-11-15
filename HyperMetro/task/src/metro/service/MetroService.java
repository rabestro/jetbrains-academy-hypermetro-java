package metro.service;

import metro.algorithm.Node;
import metro.model.MetroLine;
import metro.model.MetroStation;
import metro.model.StationID;

import java.util.Deque;

public interface MetroService {
    MetroLine getMetroLine(String name);

    MetroStation getMetroStation(StationID stationId);

    void addHead(String lineName, String stationName);

    void append(String lineName, String stationName);

    void connect(StationID source, StationID target);

    void remove(StationID target);

    Deque<Node<StationID>> bfsRoute(StationID source, StationID target);

    Deque<Node<StationID>> route(StationID source, StationID target);

    Deque<Node<StationID>> fastestRoute(StationID source, StationID target);
}
