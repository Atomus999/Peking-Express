package map;

public class Connections {
/*
      source[i] – target[i] is a connection  &&   the transport cost is price[i]
*/
     //list of all the sources of all edges
    private int[] source;

    //list of all the targets of all edges
    private int[] target;

    //list of all the transport costs in the graph
    private int[] price;

    //constructor
    public Connections(int[] source, int[] target, int[] price) {
        this.source = source;
        this.target = target;
        this.price = price;
    }

    public Connections() {
    }

    //getters and setters
    public int[] getSource() {
        return source;
    }

    public void setSource(int[] source) {
        this.source = source;
    }

    public int[] getTarget() {
        return target;
    }

    public void setTarget(int[] target) {
        this.target = target;
    }

    public int[] getPrice() {
        return price;
    }

    public void setPrice(int[] price) {
        this.price = price;
    }
}
