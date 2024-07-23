/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;
/**
 *
 * @author Usuario
 */
public class MyGraph {
    private Map<String, List<String>> adjList = new HashMap<>();

    // Agregar un nodo al grafo
    public void addNode(String node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    // Agregar una arista entre dos nodos
    public void addEdge(String from, String to) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(to);
    }

    // Obtener los nodos adyacentes a un nodo
    public List<String> getAdjNodes(String node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }

    // Imprimir el grafo
    public void printGraph() {
        for (String node : adjList.keySet()) {
            System.out.println(node + " -> " + adjList.get(node));
        }
    }

    
}

