package src;

import java.util.*;

class WeightedGraph<V> {
    private Map<V, Vertex<V>> vertices;

    public WeightedGraph() {
        vertices = new HashMap<>();
    }

    public void addVertex(V data) {
        vertices.putIfAbsent(data, new Vertex<>(data));
    }

    public void addEdge(V source, V dest, double weight) {
        addVertex(source);
        addVertex(dest);
        Vertex<V> sourceVertex = vertices.get(source);
        Vertex<V> destVertex = vertices.get(dest);
        sourceVertex.addAdjacentVertex(destVertex, weight);
        destVertex.addAdjacentVertex(sourceVertex, weight); // Двунаправленное ребро
    }

    public Vertex<V> getVertex(V data) {
        return vertices.get(data);
    }

    public Map<V, Vertex<V>> getVertices() {
        return vertices;
    }

    public List<V> getAdjacentVertices(V data) {
        Vertex<V> vertex = vertices.get(data);
        if (vertex == null) {
            return new ArrayList<>();
        }
        List<V> adjacentData = new ArrayList<>();
        for (Vertex<V> v : vertex.getAdjacentVertices().keySet()) {
            adjacentData.add(v.getData());
        }
        return adjacentData;
    }
}