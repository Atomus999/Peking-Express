package map;

import java.util.*;

public class Map {

    //locations
    private Locations locations;

    //connections
    private Connections connections;

    //dictionary for vertices
    HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();

    //constructor
    public Map(Locations locations, Connections connections) {
        this.locations = locations;
        this.connections = connections;

        List<Integer> vertices = new ArrayList<>();
        for(int i = 0; i < this.connections.getSource().length; i++){
            if(!vertices.contains(this.connections.getSource()[i])){
                vertices.add(this.connections.getSource()[i]);
            }
            if(!vertices.contains(this.connections.getTarget()[i])){
                vertices.add(this.connections.getTarget()[i]);
            }
        }
        Collections.sort(vertices);
        int key = 0;
        while(key < this.locations.getNumber()){
           for(int i = 0; i < vertices.size(); i++) {

           h.put(key, vertices.get(i));
           key ++;
           }
        }

        for(int i = 0; i < this.connections.getSource().length; i++){
            this.connections.getSource()[i] = this.getKey(this.connections.getSource()[i]);
            this.connections.getTarget()[i] = this.getKey(this.connections.getTarget()[i]);
        }
    }

    public Map() {
    }

    //additional functions
    public int getKey(int value){
        for(java.util.Map.Entry<Integer, Integer> entry : h.entrySet()){
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public int getValueByKey(int key){
        for(java.util.Map.Entry<Integer, Integer> entry : h.entrySet()){
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return -1;
    }

    //getters and setters
    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public Connections getConnections() {
        return connections;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    public HashMap<Integer, Integer> getH() {
        return h;
    }

    public void setH(HashMap<Integer, Integer> h) {
        this.h = h;
    }
}
