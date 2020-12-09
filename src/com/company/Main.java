package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void BellmanFord(List<Edge> graph, int V, int E,
                            int src, int dest) throws Exception {
        // Initialize distance of all vertices as infinite.
        int[] distanceTo = new int[V];
        for (int i = 0; i < V; i++)
            distanceTo[i] = Integer.MAX_VALUE;

        // initialize distance of source as 0
        distanceTo[src] = 0;

        int[] paths = new int[V];//[]; // shortest path to dest
        paths[0] = 0;//= new int[]{src};
        // Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other
        // vertex can have at-most |V| - 1 edges
        for (int i = 0; i < V - 1; i++) // for each vertex..
        {
            for (int j = 0; j < E; j++) // for each edge
            {
                if (distanceTo[graph.get(j).src] + graph.get(j).weight < distanceTo[graph.get(j).dest]) {
                    distanceTo[graph.get(j).dest] =  distanceTo[graph.get(j).src] + graph.get(j).weight;
                    paths[graph.get(j).dest] = graph.get(j).src;
                }
            }
        }

        // check for negative-weight cycles.
        // The above step guarantees shortest
        // distances if graph doesn't contain
        // negative weight cycle. If we get a
        // shorter path, then there is a cycle.
        for (int i = 0; i < E; i++)
        {
            int x = graph.get(i).src;
            int y = graph.get(i).dest;
            int weight = graph.get(i).weight;
            if (distanceTo[x] != Integer.MAX_VALUE &&
                    distanceTo[x] + weight < distanceTo[y]) {
                System.out.println("Graph contains negative"
                        + " weight cycle");
                throw new Exception("Negative weight cycle");
            }
        }

        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + "\t\t" + distanceTo[i]);

        System.out.println("Shortest path to destination:");
        System.out.print(dest+1);

        int prev = paths[dest];
        while(true) {
            System.out.print(" <- ");
            System.out.print(prev + 1);
            if(prev == src) break;
            prev = paths[prev];
        }
    }


    // Driver code
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner inSc = new Scanner(file);

      //  int V = 5; // Number of vertices in graph
      //  int E = 8; // Number of edges in graph

        int V = inSc.nextInt(); // вершины
        int E = inSc.nextInt(); // связи
        int src = inSc.nextInt() - 1;
        int dest = inSc.nextInt() - 1;
        // Every edge has three values (u, v, w) where
        // the edge is from vertex u to v. And weight
        // of the edge is w.


        //int[][] graph = new int[E][];
        List<Edge> graph = new ArrayList<>();
        int edgeCount = 0;
        for(int i = 0; i < V; i++) {
            for(int j = 0; j < V; j++) {
                int currElem = inSc.nextInt();
                if (currElem != 0) {
                    int from = i;
                    int to = j;
                    int weight = currElem;
                    //graph[edgeCount] = new int[]{from, to, weight};
                    graph.add(new Edge(from, to, weight));
                    edgeCount++;
                }
            }
        }
       /* int[][] graph = { { 0, 1, -1 }, { 0, 2, 4 },
                { 1, 2, 3 }, { 1, 3, 2 },
                { 1, 4, 2 }, { 3, 2, 5 },
                { 3, 1, 1 }, { 4, 3, -3 } }; */
    try {
        BellmanFord(graph, V, E, src, dest);
    }
    catch(Exception e) {

    }
    }
}

