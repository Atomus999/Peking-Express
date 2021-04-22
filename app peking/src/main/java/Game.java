import com.fasterxml.jackson.databind.ObjectMapper;
import map.Map;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Game {

    List<Player> players = new ArrayList<>();

    int graph[][];
    int startingVertex;
    int maxBudget;
    int finalVertex;
    boolean[] criticalVertices;
    boolean[] occupiedCritVertices;
    int vertices;

    //new for reading the testfile
    int startLocation;
    ObjectMapper mapper = new ObjectMapper();
    List<List<Integer>> occupiedLocations;

    List<boolean[]> currentlyOccupiedVertices = new ArrayList<>();

    public void transformList(List<List<Integer>> occupiedLocations){
        for (int i = 0; i < occupiedLocations.size(); i++) {
            currentlyOccupiedVertices.add(new boolean[vertices]);
            for (int j = 0; j < occupiedLocations.get(i).size(); j++) {
                int temp= occupiedLocations.get(i).get(j);
                int temp2=this.map.getKey(temp);
                currentlyOccupiedVertices.get(i)[temp2]= true ;

            }
        }
    }

    //map
    private Map map;

    public Game(int[][] graph, int maxBudget, int finalVertex, boolean[] criticalVertices) {
        this.graph = graph;
        this.maxBudget = maxBudget;
        this.finalVertex = finalVertex;
        this.occupiedCritVertices = new boolean[graph.length];
        this.criticalVertices = criticalVertices;
        this.vertices = graph.length;

    }

    public Game() {

    }

    public Game(int maxBudget, int finalVertex) {
        this.maxBudget = maxBudget;
        this.finalVertex = finalVertex;
    }

    public void setBudget(int budget){
        this.maxBudget=budget;
    }

    public void readFile(String first) {
        // String first = "testfile.txt";
        try {
            //reading the testfile
            String content = new String(Files.readAllBytes(Paths.get(first)));
            JSONObject o = new JSONObject(content);

            //reading the map from the testfile.txt
            Map jsnMap = mapper.readValue(content, Map.class);
            this.map = new Map(jsnMap.getLocations(), jsnMap.getConnections());
            this.initializeMap(map);
            //reading the startLocation from the testfile.txt
            String extra = (content.substring(content.lastIndexOf("}") + 1)).trim();
            this.startLocation = parseInt(extra.substring(0, 1));
            this.setStartLocation(startLocation);
            String list = extra.substring(2);

            //reading the budget from the testfile.txt
            String extra2 = list.replaceAll("\\s+", "");
            this.maxBudget = parseInt(extra2.substring(0,extra2.indexOf("[")));
            this.setBudget(maxBudget);
            String list2 = extra2.substring(extra2.indexOf("["));

            //reading the ocuppiedLocations from the testfile.txt
            this.occupiedLocations = mapper.readValue(list2, List.class);
            transformList(this.occupiedLocations);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void nextMoveNoMap() {
        //algorithm solution if we always wait a turn when encountering an occupied critical node
        List<Integer> calculateWaiting = dijkstra(true, true);
        //algorithm solution if we always change the path when encountering an occupied critical node
        List<Integer> calculateChanging = dijkstra(false, true);
        List<Integer> calculateWaiting2 = dijkstra(true, false);
        List<Integer> calculateChanging2 = dijkstra(false, false);

        List<Integer> result;

        //if changing is null that means the critical node is blocking the way to the end without any
        //other available path
        if (calculateChanging != null && calculateWaiting != null)
            //result will take the solution that wins in as few turns as possible
            result = calculateChanging.size() < (calculateWaiting.size() + 1) ? calculateChanging : calculateWaiting;
        else if (calculateChanging2 != null && calculateWaiting2 != null)
            result = calculateChanging2.size() < (calculateWaiting2.size() + 1) ? calculateChanging2 : calculateWaiting2;
        else if(calculateWaiting2!=null)
            result=calculateWaiting2;
        else result = null;

        try {
            for (int i = 0; i < occupiedCritVertices.length; i++) {
                if (occupiedCritVertices[i] == true && result.get(1) == i) {
                    //if the vertex where we move is occupied,wait
                     System.out.println("You are at " + result.get(0) + ", vertex " + i + " is occupied.Wait");
//                    return this.map.getValueByKey(result.get(0));
//                    return result.get(0);
                }
            }
            //result.get(0) is where we were at the beggining of a turn
            //result.get(1) is where we are after moving(end of turn)

            if (result.get(0) != finalVertex)
                //this is to calculate the next most efficient path starting from the new position we moved
                startingVertex = result.get(1);
             System.out.println("You are at " + result.get(0) + ", go to vertex: " + result.get(1));
//            return this.map.getValueByKey(result.get(0));
//            return result.get(0);
        } catch (Exception e) {
            System.out.println("You have already reached the end");
//            return finalVertex;
        }
    }

    public void nextMove() {
        //algorithm solution if we always wait a turn when encountering an occupied critical node
        List<Integer> calculateWaiting = dijkstra(true, true);
        //algorithm solution if we always change the path when encountering an occupied critical node
        List<Integer> calculateChanging = dijkstra(false, true);
        List<Integer> result;

        //if changing is null that means the critical node is blocking the way to the end without any
        //other available path
        if (calculateChanging != null)
            //result will take the solution that wins in as few turns as possible
            result = calculateChanging.size() < (calculateWaiting.size() + 1) ? calculateChanging : calculateWaiting;
        else
            result = calculateWaiting;
        try {
            for (int i = 0; i < occupiedCritVertices.length; i++) {
                if (occupiedCritVertices[i] == true && result.get(1) == i) {
                    //if the vertex where we move is occupied,wait
                    System.out.println("You are at " + this.map.getValueByKey(result.get(0)) + ", vertex " + this.map.getValueByKey(i) + " is occupied.Wait");
                    return;
                }
            }
            //result.get(0) is where we were at the beggining of a turn
            //result.get(1) is where we are after moving(end of turn)
            System.out.println("You are at " + this.map.getValueByKey(result.get(0)) + ", go to vertex: " + this.map.getValueByKey(result.get(1)));
            if (result.get(0) != finalVertex)
                //this is to calculate the next most efficient path starting from the new position we moved
                startingVertex = result.get(1);
        } catch (Exception e) {
            System.out.println("You have already reached the end");
        }
    }

    public List<Integer> dijkstra(boolean alwaysWait, boolean efficientPath) {
        //parent[i]=j <- j is the parent of i
        int parent[] = new int[vertices];

        //value[u]=v <- v is the minimum weight from source to u(shortest path)
        int value[] = new int[vertices];

        //processed[w]=true if vertex w was processed
        boolean processed[] = new boolean[vertices];

        //actualBudget[u] = b <- b is the price from source to u
        int actualBudget[] = new int[vertices];

        //initialize the starting vertex with values to be picked first always
        parent[startingVertex] = -1; //root node
        value[startingVertex] = 0; //minimum value to get picked first

        //loop through everything beggining at startingVertex
        for (int i = startingVertex + 1; i < vertices + startingVertex; i++) {
            value[i % vertices] = Integer.MAX_VALUE;
            processed[i % vertices] = false;
            actualBudget[i % vertices] = 0;
        }
        for (int i = 0; i < vertices - 1; i++) {
            //select best vertex to process
            int bestVertex = selectMinValVertex(value, processed, vertices);
            //in case the critical vertex is occupied and blocks the path when trying to change return null
            if (bestVertex == -1) {
                //even if the algorithm doesn't finish completely because it can't reach all vertices because of the budget,
                // if we already found a way to the end return it
                List<Integer> path = getPath(parent, startingVertex, finalVertex);
                if (path.get(path.size() - 1) == finalVertex && efficientPath == false)
                    return path;

                return null;
            }
            processed[bestVertex] = true;

            //update the  values of the adjacent vertices
            for (int j = 0; j < vertices; j++) {
                //if going to the next vertex would exceed our budget skip it
                if ((actualBudget[bestVertex] + graph[bestVertex][j]) > maxBudget)
                    continue;
                //if there is a connection between u and j
                //if vertex j was not processed
                //if the edge weight is smaller than the current value
                if (graph[bestVertex][j] != 0 && processed[j] != true &&
                        value[bestVertex] + (efficientPath ? 1 : graph[bestVertex][j])/*graph[bestVertex][j] 1*/ < value[j] &&
                        value[bestVertex] != Integer.MAX_VALUE) {
                    //if our path is blocked and alwaysWait is false then ignore this vertex to try to
                    //find a better solution
                    if (alwaysWait == false && occupiedCritVertices[j] == true) {
                        continue;
                    }
                    actualBudget[j] = actualBudget[bestVertex] + graph[bestVertex][j];
                    value[j] = value[bestVertex] + (efficientPath ? 1 : graph[bestVertex][j])/*graph[bestVertex][j] 1*/;
                    parent[j] = bestVertex;

                }
            }
        }
        //show the most efficient path based on where we start and want to end
        List<Integer> path = getPath(parent, startingVertex, finalVertex);
        // printValues(path,value,vertices,actualBudget,startingVertex,finalVertex);

        return path;
    }

    public void setStartLocationNoMap(int startLocation) {
        this.startingVertex = startLocation;

    }

    public void updateOccupiedLocations(boolean[] occupiedVertices) {

        for (int i = 0; i < occupiedCritVertices.length; i++) {
            occupiedCritVertices[i] = false;
        }
        for (int i = 0; i < criticalVertices.length; i++) {
            if (occupiedVertices[i] == true && criticalVertices[i] == true)
                occupiedCritVertices[i] = true;
        }
    }

    public int nextMoveUpdated() {
        //algorithm solution if we always wait a turn when encountering an occupied critical node
        List<Integer> calculateWaiting = dijkstra(true, true);
        //algorithm solution if we always change the path when encountering an occupied critical node
        List<Integer> calculateChanging = dijkstra(false, true);
        List<Integer> calculateWaiting2 = dijkstra(true, false);
        List<Integer> calculateChanging2 = dijkstra(false, false);

        List<Integer> result;

        //if changing is null that means the critical node is blocking the way to the end without any
        //other available path
        if (calculateChanging != null && calculateWaiting != null)
            //result will take the solution that wins in as few turns as possible
            result = calculateChanging.size() < (calculateWaiting.size() + 1) ? calculateChanging : calculateWaiting;
        else if (calculateChanging2 != null && calculateWaiting2 != null)
            result = calculateChanging2.size() < (calculateWaiting2.size() + 1) ? calculateChanging2 : calculateWaiting2;
        else if(calculateWaiting2!=null)
            result=calculateWaiting2;
        else result = null;

        try {
            for (int i = 0; i < occupiedCritVertices.length; i++) {
                if (occupiedCritVertices[i] == true && result.get(1) == i) {
                    //if the vertex where we move is occupied,wait
                    // System.out.println("You are at " + this.map.getValueByKey(result.get(0)) + ", vertex " + this.map.getValueByKey(i) + " is occupied.Wait");
                    return this.map.getValueByKey(result.get(0));
//                    return result.get(0);
                }
            }
            //result.get(0) is where we were at the beggining of a turn
            //result.get(1) is where we are after moving(end of turn)

            if (result.get(0) != finalVertex)
                //this is to calculate the next most efficient path starting from the new position we moved
                startingVertex = result.get(1);
            // System.out.println("You are at " + this.map.getValueByKey(result.get(0)) + ", go to vertex: " + this.map.getValueByKey(result.get(1)));
            return this.map.getValueByKey(result.get(0));
//            return result.get(0);
        } catch (Exception e) {
//            System.out.println("You have already reached the end");
            return this.map.getValueByKey(finalVertex);
        }
    }

    public void runAlgorithm() {
        int currentLocation = startingVertex;
        int currentTurn = 1;
        List<Integer> path = new ArrayList<>();

        while (currentLocation != this.map.getValueByKey(finalVertex)) {
            currentLocation = nextMoveUpdated();
            path.add(currentLocation);
            try {
                updateOccupiedLocations(currentlyOccupiedVertices.get(currentTurn));
            } catch (Exception e) {
            }
            if (criticalVertices[this.map.getKey(currentLocation)] == true)
                occupiedCritVertices[this.map.getKey(currentLocation)] = true;
            currentTurn++;
//            System.out.println(path);

        }
        System.out.println(path);
    }

    public void updateCompetitorLocationNoMap(int id, int loc) {

        //loc is the given vertex number eg:for the given graph in the presentation 1,2,3,88
        //location is the number we use to keep track of indexes eg:
        // for the given graph in the presentation 0,1,2,3
        int location = loc;

        for (Player player : players) {
            if (player.getId() == id) {
                int formerLocation = player.getCurrentLocation();
                occupiedCritVertices[formerLocation] = false;
                player.setCurrentLocation(location);
                if (criticalVertices[location] == true) {
                    occupiedCritVertices[location] = true;
                }
                System.out.println("Competitors with id " + player.getId() + " are at " + loc);
            }

        }
        //maintain occupied crit list accurate even after a player leave a critical vertex
        for (int i = 0; i < criticalVertices.length; i++) {
            for (Player player : players) {
                if (criticalVertices[i] == true && player.getCurrentLocation() == i)
                    occupiedCritVertices[i] = true;
            }
        }


    }

    public void updateCompetitorLocation(int id, int loc) {

        //loc is the given vertex number eg:for the given graph in the presentation 1,2,3,88
        //location is the number we use to keep track of indexes eg:
        // for the given graph in the presentation 0,1,2,3
        int location = this.map.getKey(loc);

        for (Player player : players) {
            if (player.getId() == id) {
                int formerLocation = player.getCurrentLocation();
                occupiedCritVertices[formerLocation] = false;
                player.setCurrentLocation(location);
                if (criticalVertices[location] == true) {
                    occupiedCritVertices[location] = true;
                }
                System.out.println("Competitors with id " + player.getId() + " are at " + loc);
            }

        }
        //maintain occupied crit list accurate even after a player leave a critical vertex
        for (int i = 0; i < criticalVertices.length; i++) {
            for (Player player : players) {
                if (criticalVertices[i] == true && player.getCurrentLocation() == i)
                    occupiedCritVertices[i] = true;
            }
        }


    }

    private void printValues(List<Integer> path, int[] value, int vertices, int[] budget, int startingVertex, int finalVertex) {
        System.out.println(path);
        System.out.println(finalVertex + "               " + value[finalVertex] + "( budget: " + budget[finalVertex] + ")");
        System.out.println("Vertex" + "     " + "Distance from root");

        for (int i = startingVertex; i < vertices + startingVertex; i++) {
            System.out.println(i % vertices + "               " + value[i % vertices] + "( budget: " + budget[i % vertices] + ")");
        }
    }

    //calculates the chosen path in the dijkstra function
    private List<Integer> getPath(int[] parent, int startingVertex, int finalVertex) {
        List<Integer> path = new ArrayList<>();
        int index = finalVertex;
        path.add(index);
        while (index != startingVertex) {
            path.add(parent[index]);
            index = parent[index];
        }
        Collections.reverse(path);
        return path;
    }

    //pick minimum value vertex that is not processed
    private int selectMinValVertex(int[] value, boolean[] processed, int vertices) {
        int min = Integer.MAX_VALUE;
        int chosenVertex = -1;

        for (int i = 0; i < vertices; i++) {
            if (processed[i] == false && value[i] < min) {
                chosenVertex = i;
                min = value[i];
            }
        }
        return chosenVertex;
    }

    //initialize map for the game
    public int[][] initializeMap(Map map) {

        //update the attributes of the game class
        this.map = map;
        this.vertices = map.getLocations().getNumber();
        int finalVertex = 88;
        this.finalVertex = map.getKey(finalVertex);

        //prepare the matrix
        int number = this.map.getLocations().getNumber();
        int[][] graph = new int[number][number];

        //initialize the graph with 0s
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                graph[i][j] = 0;
            }
        }

        //create the matrix
        int[] source = this.map.getConnections().getSource();
        int[] target = this.map.getConnections().getTarget();
        int[] price = this.map.getConnections().getPrice();
        for (int i = 0; i < this.map.getConnections().getSource().length; i++) {
            graph[source[i]][target[i]] = price[i];
            graph[target[i]][source[i]] = price[i];
        }

        // initialize the array that will help us manage and check the occupied critical vertices
        this.occupiedCritVertices = new boolean[graph.length];

        //initialize the array that keeps track of the critical vertices
        boolean[] criticalVertices = new boolean[graph.length];
        for (int i = 0; i < this.map.getLocations().getCritical().length; i++) {
            criticalVertices[this.map.getKey(this.map.getLocations().getCritical()[i])] = true;
        }
        this.setCriticalVertices(criticalVertices);

        //print the generated graph
        printGraph(graph, number);

        //return the generated graph
        this.graph = graph;
        this.vertices = graph.length;
        return graph;
    }

    //getters and setters
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void printGraph(int[][] graph, int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
            for (int j = 0; j < n; j++) {
                System.out.print(graph[i][j] + " ");
            }
        }
    }

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public int getStartingVertex() {
        return startingVertex;
    }

    public void setStartingVertex(int startingVertex) {
        this.startingVertex = startingVertex;
    }

    public int getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(int maxBudget) {
        this.maxBudget = maxBudget;
    }

    public int getFinalVertex() {
        return finalVertex;
    }

    public void setFinalVertex(int finalVertex) {
        this.finalVertex = finalVertex;
    }

    public boolean[] getCriticalVertices() {
        return criticalVertices;
    }

    public void setCriticalVertices(boolean[] criticalVertices) {
        this.criticalVertices = criticalVertices;
    }

    public boolean[] getOccupiedCritVertices() {
        return occupiedCritVertices;
    }

    public void setOccupiedCritVertices(boolean[] occupiedCritVertices) {
        this.occupiedCritVertices = occupiedCritVertices;
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(int startLocation) {
        this.startingVertex = this.map.getKey(startLocation);

    }
    public void setFinalLocation(int finalLocation) {
        this.finalVertex = this.map.getKey(finalLocation);

    }

    public List<List<Integer>> getOccupiedLocations() {
        return occupiedLocations;
    }

    public void setOccupiedLocations(List<List<Integer>> occupiedLocations) {
        this.occupiedLocations = occupiedLocations;
    }

    public List<boolean[]> getCurrentlyOccupiedVertices() {
        return currentlyOccupiedVertices;
    }

    public void setCurrentlyOccupiedVertices(List<boolean[]> currentlyOccupiedVertices) {
        this.currentlyOccupiedVertices = currentlyOccupiedVertices;
    }
}
