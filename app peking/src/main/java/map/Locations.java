package map;

public class Locations {

    // total number of vertices in the graph
    private int number;

    //list of all the critical vertices
    private int[] critical;

    //constructor
    public Locations(int number, int[] critical) {
        this.number = number;
        this.critical = critical;
    }

    public Locations() {
    }

    //getters and setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int[] getCritical() {
        return critical;
    }

    public void setCritical(int[] critical) {
        this.critical = critical;
    }
}
