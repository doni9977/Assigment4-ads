package src;

import java.util.*;

class BreadthFirstSearch<V> implements Search<V> {
    private Map<V, V> edgeTo;
    private Set<V> marked;
    private final V source;
    private WeightedGraph<V> graph;

    public BreadthFirstSearch(WeightedGraph<V> graph, V source) {
        this.graph = graph;
        this.source = source;
        this.edgeTo = new HashMap<>();
        this.marked = new HashSet<>();
        bfs(source);
    }

    private void bfs(V source) {
        Queue<V> queue = new LinkedList<>();
        queue.add(source);
        marked.add(source);

        while (!queue.isEmpty()) {
            V v = queue.poll();
            for (V w : graph.getAdjacentVertices(v)) {
                if (!marked.contains(w)) {
                    edgeTo.put(w, v);
                    marked.add(w);
                    queue.add(w);
                }
            }
        }
    }

    @Override
    public List<V> pathTo(V target) {
        if (!marked.contains(target)) {
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
        List<V> path = pathTo(target);
        return path.isEmpty() ? Double.POSITIVE_INFINITY : path.size() - 1;
    }
}