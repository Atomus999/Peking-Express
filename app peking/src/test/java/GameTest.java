import map.Connections;
import map.Locations;
import map.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    //Game game=new Game();
    @Test
    void StartInMiddleTree() {

        int graph[][] = new int[][]{
                {  0, 3, 7, 0, 0, 0, 0, 0, 0, 0, 0},
                {  3, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0},
                { 7, 0, 0, 5, 0, 10, 0, 0, 0, 0, 0},
                {0, 1, 5, 0, 7, 2, 0, 11, 5, 0, 22},
                {  0, 2, 0, 7, 0, 0, 1, 0, 0, 0, 0},
                { 0, 0, 10, 2, 0, 0, 0, 0, 0, 1, 0},
                {  0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 5},
                { 0, 0, 0, 11, 0, 0, 0, 0, 0, 0, 7},
                {  0, 0, 0, 5, 0, 0, 0, 0, 0, 2, 5},
                {  0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0},
                { 0, 0, 0, 22, 0, 0, 5, 7, 5, 0, 0}

        };
        double startTime = System.nanoTime();
        int rootVertex = 5;
        int maxBudget = 12;
        int finalVertex = 10;
        boolean[] criticalVertices = new boolean[graph.length];
        criticalVertices[3] = true;

        Game game = new Game(graph, maxBudget, finalVertex, criticalVertices);
        game.setStartLocationNoMap(rootVertex);
        game.addPlayer(new Player(1, 3));
//        game.runAlgorithm();
        game.updateCompetitorLocationNoMap(1, 3);
        game.nextMoveNoMap();
        game.nextMoveNoMap();
//          game.updateCompetitorLocationNoMap(1, 1);
          game.nextMoveNoMap();
          game.nextMoveNoMap();
          game.nextMoveNoMap();
          game.nextMoveNoMap();

        // List<Integer> results =game.dijkstra();

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000;
        System.out.println("Dijkstra Tree2 execution time: " + duration + " miliseconds");


    }
    @Test
    void ChangingMoreEfficientTree() {
        int graph[][] = new int[][]{
                {0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 0, 1, 1, 0},


        };

        double startTime = System.nanoTime();

        int rootVertex = 0;
        int maxBudget = 10;
        int finalVertex = 4;
        boolean[] criticalVertices = new boolean[graph.length];
        criticalVertices[3] = true;

        Game game = new Game(graph, maxBudget, finalVertex, criticalVertices);
        game.setStartLocationNoMap(rootVertex);

        game.addPlayer(new Player(1, 3));
        game.runAlgorithm();
//        game.updateCompetitorLocationNoMap(1, 3);
//        game.nextMoveNoMap();
//        game.nextMoveNoMap();
////        game.updateCompetitorLocationNoMap(1, 1);
//        game.nextMoveNoMap();
//        game.nextMoveNoMap();
//        game.nextMoveNoMap();
//        game.nextMoveNoMap();

        // List<Integer> results =game.dijkstra();

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000;
        System.out.println("Dijkstra Tree2 execution time: " + duration + " miliseconds");


    }

    @Test
    void WaitingMoreEfficientTree() {
        int graph[][] = new int[][]{
                {0, 1, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 0},


        };
        double startTime = System.nanoTime();
        int rootVertex = 0;
        int maxBudget = 10;
        int finalVertex = 6;
        boolean[] criticalVertices = new boolean[graph.length];
        criticalVertices[5] = true;

        Game game = new Game(graph, maxBudget, finalVertex, criticalVertices);
        game.setStartLocationNoMap(rootVertex);

        game.addPlayer(new Player(1, 3));

        game.updateCompetitorLocationNoMap(1, 5);
        game.nextMoveNoMap();
        game.nextMoveNoMap();
        game.updateCompetitorLocationNoMap(1, 1);
        game.nextMoveNoMap();
        game.nextMoveNoMap();
        game.nextMoveNoMap();
        game.nextMoveNoMap();

        // List<Integer> results =game.dijkstra();

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000;
        System.out.println("Dijkstra Tree2 execution time: " + duration + " miliseconds");


    }

    @Test
    void OurChosenTreeBig() {
      /*
                THE MATRIX WE NEED TO PROVIDE:

                {  0, 3, 0, 0, 0, 4, 0, 0, 0, 0},
                {  3, 0, 3, 0, 1, 0, 1, 0, 0, 0},
                {  0, 3, 0, 1, 0, 0, 0, 0, 0, 0},
                {  0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {  0, 1, 0, 0, 0, 0, 0, 0, 0, 2},
                {  4, 0, 0, 0, 0, 0, 0, 0, 0, 9},
                {  0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                {  0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
                {  0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
                {  0, 0, 0, 1, 2, 9, 0, 0, 1, 0}

       */

        //creating the game
        Game game = new Game(10, 9);

        //initializing the given map
        Locations locations = new Locations(10, new int[]{4});
        Connections connections = new Connections(new int[]{0, 0, 1, 1, 1, 2, 3, 4, 5, 6, 7, 8}, new int[]{1, 5, 2, 4, 6, 3, 9, 9, 9, 7, 8, 9}, new int[]{3, 4, 3, 1, 1, 1, 1, 2, 9, 1, 1, 1});
        Map map = new Map(locations, connections);
        int[][] graph = game.initializeMap(map);

        //setting up the starting location for our player
        int rootVertex = 0;
        game.setStartLocation(rootVertex);

        System.out.println();

        game.addPlayer(new Player(1, 3));
        game.addPlayer(new Player(2, 2));

        game.runAlgorithm();

//        // GAME TIME:
//       // game.updateCompetitorLocation(1, 4);
//      //  game.updateCompetitorLocation(2, 3);
//        game.nextMove();
//       // game.updateCompetitorLocation(2, 4);
//        game.nextMove();
//       // game.updateCompetitorLocation(1, 2);
//        game.nextMove();
//       // game.updateCompetitorLocation(2, 1);
//        game.nextMove();
//        game.nextMove();
//        game.nextMove();

        // double endTime = System.nanoTime();
        //double duration = (endTime - startTime)/1000000;
        //System.out.println("Dijkstra Tree2 execution time: "+ duration+" miliseconds");
    }

    @Test
    void OurChosenTreeSmall() {

        /*     THE MATRIX WE NEED TO PROVIDE:

                {  0, 1, 3, 7},
                {  1, 0, 1, 0},
                {  3, 1, 0, 1},
                {  7, 0, 1, 0},
        */

        //creating the game
        Game game = new Game(5, 88);

        //initializing the given map
        Locations locations = new Locations(4, new int[]{3});
        Connections connections = new Connections(new int[]{1, 1, 1, 2, 3}, new int[]{2, 3, 88, 3, 88}, new int[]{1, 3, 7, 1, 1});
        Map map = new Map(locations, connections);
        int[][] graph = game.initializeMap(map);

        //setting up the starting location for our player
        int rootVertex = 1;
        game.setStartLocation(rootVertex);

        System.out.println();

        game.addPlayer(new Player(1, 3));

        game.runAlgorithm();
        // GAME TIME:
//        game.updateCompetitorLocation(1, 3);
//        game.nextMove();
////        game.updateCompetitorLocation(1, 3);
//        game.nextMove();
////        game.updateCompetitorLocation(1, 88);
//        game.nextMove();
//        game.nextMove();
//        game.nextMove();

        // double startTime = System.nanoTime();
        // double endTime = System.nanoTime();
        // double duration = (endTime - startTime)/1000000;
        //System.out.println("Dijkstra OurChosenTree execution time: "+ duration+" miliseconds");
    }


    @Test
    public void ReadFile(){
        Game g = new Game();
        g.readFile("testfile.txt");
        System.out.println();
        g.runAlgorithm();
        Locations locations = new Locations(4, new int[]{3});
        Connections connections = new Connections(new int[]{3, 2, 1, 2, 3}, new int[]{2, 1, 88, 88, 88}, new int[]{3, 7, 9, 2, 1});
        Map map = new Map(locations, connections);


        List<List<Integer>> ocuppiedLocations = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>(); list1.add(1); list1.add(3); ocuppiedLocations.add(list1);
        List<Integer> list2 = new ArrayList<>(); list2.add(2); ocuppiedLocations.add(list2);
        List<Integer> list3 = new ArrayList<>(); list3.add(88);  list3.add(3); ocuppiedLocations.add(list3);

        //checking start location
        assertEquals(3, g.getStartLocation());

        //checking the budget
        assertEquals(21, g.getMaxBudget());

        //checking the map
        assertEquals(map.getLocations().getNumber(), g.getMap().getLocations().getNumber());
        assertArrayEquals(map.getLocations().getCritical(), g.getMap().getLocations().getCritical());
        assertArrayEquals(map.getConnections().getSource(), g.getMap().getConnections().getSource());
        assertArrayEquals(map.getConnections().getTarget(), g.getMap().getConnections().getTarget());
        assertArrayEquals(map.getConnections().getPrice(), g.getMap().getConnections().getPrice());

        //checking the occupied locations
        assertArrayEquals(list1.toArray(), g.getOccupiedLocations().get(0).toArray());
        assertArrayEquals(list2.toArray(), g.getOccupiedLocations().get(1).toArray());
        assertArrayEquals(list3.toArray(), g.getOccupiedLocations().get(2).toArray());
    }

    @Test
    public void ReadTest(){
        Game g= new Game();
        g.readFile("test6.txt");
        g.setStartLocation(14);
        g.runAlgorithm();
    }

    @Test
    public void ReadFileOur(){
        Game g= new Game();
        g.readFile("testfileOur.txt");
        g.runAlgorithm();

        int b=0;
        int i=0;
        b= ++i + ++i + i--;
        System.out.println(b);
        System.out.println(i);
    }
    @Test
    public void ReadFile3(){
        Game g= new Game();
        g.readFile("testfile3.txt");
        g.runAlgorithm();
    }
    @Test
    public void ReadFile4(){
        Game g= new Game();
        g.readFile("testfile4.txt");
        g.runAlgorithm();
    }

    @Test
    public void ReadFile2(){
        Game g = new Game();
        g.readFile("testfile2.txt");
        System.out.println();
        g.runAlgorithm();
        Locations locations = new Locations(10, new int[]{7, 6, 3});
        Connections connections = new Connections(new int[]{2, 7, 3, 1, 9, 4, 6, 8, 5, 4}, new int[]{7, 3, 1, 9, 4, 6, 8, 5, 88, 5}, new int[]{3, 1, 5, 6, 2, 8, 3, 7, 2, 5});
        Map map = new Map(locations, connections);

        List<List<Integer>> ocuppiedLocations = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>(); list1.add(4); list1.add(9); list1.add(8); list1.add(88); ocuppiedLocations.add(list1);
        List<Integer> list2 = new ArrayList<>(); list2.add(6); list2.add(1); list2.add(9); ocuppiedLocations.add(list2);
        List<Integer> list3 = new ArrayList<>(); list3.add(4); list3.add(1); list3.add(3); list3.add(8); list3.add(9); ocuppiedLocations.add(list3);
        List<Integer> list4 = new ArrayList<>(); list4.add(4); list4.add(5); list4.add(1); list4.add(9); list4.add(3); ocuppiedLocations.add(list4);

        //checking start location
        assertEquals(2, g.getStartLocation());

        //checking the budget
        assertEquals(39, g.getMaxBudget());

        //checking the map
        assertEquals(map.getLocations().getNumber(), g.getMap().getLocations().getNumber());
        assertArrayEquals(map.getLocations().getCritical(), g.getMap().getLocations().getCritical());
        assertArrayEquals(map.getConnections().getSource(), g.getMap().getConnections().getSource());
        assertArrayEquals(map.getConnections().getTarget(), g.getMap().getConnections().getTarget());
        assertArrayEquals(map.getConnections().getPrice(), g.getMap().getConnections().getPrice());

        //checking the occupied locations
        assertArrayEquals(list1.toArray(), g.getOccupiedLocations().get(0).toArray());
        assertArrayEquals(list2.toArray(), g.getOccupiedLocations().get(1).toArray());
        assertArrayEquals(list3.toArray(), g.getOccupiedLocations().get(2).toArray());
        assertArrayEquals(list4.toArray(), g.getOccupiedLocations().get(3).toArray());

    }
}