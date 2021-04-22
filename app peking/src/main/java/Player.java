public class Player {
    private int id;
    private int currentLocation;

    public Player(int id, int currentLocation) {
        this.id = id;
        this.currentLocation = currentLocation;
    }

    public int getId() {
        return id;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }
}
