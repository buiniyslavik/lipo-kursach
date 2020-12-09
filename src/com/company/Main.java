package com.company;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static String BellmanFord(List<Edge> graph, int V, int E,
                            int src, int dest) {
        int[] distanceTo = new int[V]; // расстояния
        for (int i = 0; i < V; i++)
            distanceTo[i] = Integer.MAX_VALUE;

        distanceTo[src] = 0;

        int[] paths = new int[V]; // массив для восстановления путей
        paths[0] = 0;

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

        for (int i = 0; i < E; i++) // проверка на отрицательный цикл
        {
            int x = graph.get(i).src;
            int y = graph.get(i).dest;
            int weight = graph.get(i).weight;
            if (distanceTo[x] != Integer.MAX_VALUE &&
                    distanceTo[x] + weight < distanceTo[y]) {
                return "Отрицательный цикл";

            }
        }
        // вывод данных
        StringBuilder outp = new StringBuilder(Integer.toString(distanceTo[dest]) + '\n');
        Stack<String> shortestPath = new Stack<>();
        shortestPath.push(Integer.toString(dest+1));

        int prev = paths[dest];
        while(true) {
            shortestPath.push(Integer.toString(prev+1));
            if(prev == src) break;
            prev = paths[prev];
        }

        while(!shortestPath.isEmpty()) {
            outp.append(shortestPath.pop()).append(' ');
        }
        outp.append('\n');
        return outp.toString();
    }

    public static void main(String[] args) throws IOException {
        File file = new File("input.txt");
        Scanner inSc = new Scanner(file);

        int V = inSc.nextInt(); // вершины
        int E = inSc.nextInt(); // связи
        int src = inSc.nextInt() - 1; // откуда
        int dest = inSc.nextInt() - 1; // куда

        List<Edge> graph = new ArrayList<>();
        for(int i = 0; i < V; i++) {
            for(int j = 0; j < V; j++) {
                int currElem = inSc.nextInt();
                if (currElem != 0) {
                    graph.add(new Edge(i, j, currElem));
                }
            }
        }
        String output = BellmanFord(graph, V, E, src, dest);
        FileWriter outFile = new FileWriter("output.txt");
        outFile.write(output);
        outFile.close();
    }
}

