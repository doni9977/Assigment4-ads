package src;

import java.util.*;

class DijkstraSearch<V> implements Search<V> {
    private Map<V, Double> distTo;
    private Map<V, V> edgeTo;
    private PriorityQueue<VertexDistance<V>> pq;
    private final V source;
    private WeightedGraph<V> graph;
    private Set<V> visited;

    private static class VertexDistance<V> implements Comparable<VertexDistance<V>> {
        V vertex;
        double distance;

        VertexDistance(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexDistance<V> other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    public DijkstraSearch(WeightedGraph<V> graph, V source) {
        this.graph = graph;
        this.source = source;
        this.distTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.pq = new PriorityQueue<>();
        this.visited = new HashSet<>();

        for (V v : graph.getVertices().keySet()) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        distTo.put(source, 0.0);

        pq.add(new VertexDistance<>(source, 0.0));
        dijkstra();
    }

    private void dijkstra() {
        while (!pq.isEmpty()) {
            VertexDistance<V> vd = pq.poll();
            V v = vd.vertex;
            if (visited.contains(v)) continue;
            visited.add(v);
            relax(v);
        }
    }

    private void relax(V v) {
        Vertex<V> vertex = graph.getVertex(v);
        if (vertex == null) return;

        for (Map.Entry<Vertex<V>, Double> entry : vertex.getAdjacentVertices().entrySet()) {
            V w = entry.getKey().getData();
            double weight = entry.getValue();
            double newDist = distTo.get(v) + weight;

            if (newDist < distTo.get(w)) {
                distTo.put(w, newDist);
                edgeTo.put(w, v);
                pq.add(new VertexDistance<>(w, newDist));
            }
        }
    }

    @Override
    public List<V> pathTo(V target) {
        if (!distTo.containsKey(target) || distTo.get(target) == Double.POSITIVE_INFINITY) {
            return Collections.emptyList();
        }
        List<V> path = new LinkedList<>();
        for (V x = target; x != null && !x.equals(source); x = edgeTo.get(x)) {
            path.add(0, x);
        }
        path.add(0, source);
        return path;
    }

    @Override
    public double distanceTo(V target) {
        return distTo.getOrDefault(target, Double.POSITIVE_INFINITY);
    }
}