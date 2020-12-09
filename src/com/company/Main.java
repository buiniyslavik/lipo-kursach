package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static void BellmanFord(int[][] graph, int V, int E,
                            int src)
    {
        // Initialize distance of all vertices as infinite.
        int []dis = new int[V];
        for (int i = 0; i < V; i++)
            dis[i] = Integer.MAX_VALUE;

        // initialize distance of source as 0
        dis[src] = 0;

        // Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other
        // vertex can have at-most |V| - 1 edges
        for (int i = 0; i < V - 1; i++)
        {

            for (int j = 0; j < E; j++)
            {
                if (dis[graph[j][0]] + graph[j][2] <
                        dis[graph[j][1]])
                    dis[graph[j][1]] =
                            dis[graph[j][0]] + graph[j][2];
            }
        }

        // check for negative-weight cycles.
        // The above step guarantees shortest
        // distances if graph doesn't contain
        // negative weight cycle. If we get a
        // shorter path, then there is a cycle.
        for (int i = 0; i < E; i++)
        {
            int x = graph[i][0];
            int y = graph[i][1];
            int weight = graph[i][2];
            if (dis[x] != Integer.MAX_VALUE &&
                    dis[x] + weight < dis[y])
                System.out.println("Graph contains negative"
                        +" weight cycle");
        }

        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + "\t\t" + dis[i]);
    }

    // Driver code
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt");
        Scanner inSc = new Scanner(file);

      //  int V = 5; // Number of vertices in graph
      //  int E = 8; // Number of edges in graph

        int V = inSc.nextInt(); // вершины
        int E = inSc.nextInt(); // связи
        int src = inSc.nextInt();
       // int dest = inSc.nextInt();
        // Every edge has three values (u, v, w) where
        // the edge is from vertex u to v. And weight
        // of the edge is w.


        int[][] graph = new int[E][];
        int edgeCount = 0;
        for(int i = 0; i < V; i++) {
            for(int j = 0; j < V; j++) {
                int currElem = inSc.nextInt();
                if (currElem != 0) {
                    int from = i;
                    int to = j;
                    int weight = currElem;
                    graph[edgeCount] = new int[]{from, to, weight};
                    edgeCount++;
                }
            }
        }
       /* int[][] graph = { { 0, 1, -1 }, { 0, 2, 4 },
                { 1, 2, 3 }, { 1, 3, 2 },
                { 1, 4, 2 }, { 3, 2, 5 },
                { 3, 1, 1 }, { 4, 3, -3 } }; */

        BellmanFord(graph, V, E, src);
    }
}

