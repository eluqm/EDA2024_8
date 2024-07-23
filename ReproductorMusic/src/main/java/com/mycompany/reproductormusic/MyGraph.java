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

    // Convertir a GraphStream Graph
    public Graph toGraphStream() {
        // Crear el gráfico de GraphStream
        Graph graph = new SingleGraph("MyGraph");

        // Añadir nodos y aristas al gráfico
        for (String node : adjList.keySet()) {
            // Añadir nodo si no existe
            if (graph.getNode(node) == null) {
                graph.addNode(node).setAttribute("ui.label", node);
            }
            for (String adj : adjList.get(node)) {
                // Añadir nodo adyacente si no existe
                if (graph.getNode(adj) == null) {
                    graph.addNode(adj).setAttribute("ui.label", adj);
                }
                // Añadir arista si no existe
                String edgeId = node + "-" + adj;
                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, node, adj, true);
                }
            }
        }

        // Aplicar estilos CSS
        String css = "node { size: 20px; text-size: 16px; text-color: black; text-style: bold; }" +
                     "node.song { fill-color: blue; size: 40px; }" +
                     "node.artist { fill-color: red; size: 40px; }" +
                     "edge { text-size: 12px; }";
        graph.setAttribute("ui.stylesheet", css);

        // Asignar clases a los nodos
        for (String node : adjList.keySet()) {
            graph.getNode(node).setAttribute("ui.class", getNodeType(node));
        }

        return graph;
    }
    

    private String getNodeType(String node) {
        if (node.startsWith("Song")) {
            return "song";
        } else if (node.startsWith("Artist")) {
            return "artist";
        } else {
            return "";
        }
    }
}

