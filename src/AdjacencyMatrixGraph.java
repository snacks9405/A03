import java.util.HashMap;

public class AdjacencyMatrixGraph<E,D> implements Graph<E,D> {
    private HashMap<E, Vertex<E>> vertices = new HashMap<>();
    private HashMap<D, Edge<E,D>> edges = new HashMap<>();

    public int numVertices() {
        return vertices.size();
    }

    public int numEdges() {
        return edges.size();
    }

    public Iterable<Vertex<E>> vertices() {
        return vertices.values();
    }

    public Iterable<Edge<E, D>> edges() {
        return edges.values();
    }

    public Edge<E, D> getEdge(Vertex<E> u, Vertex<E> v) {
        for (Edge<E,D> edge : edges.values()){
            if ((edge.source == u && edge.destination == v) ||
                (edge.source == v && edge.destination == u)) {
                return edge;
            }
        }

        return null;
    }

    public Edge<E, D> getEdge(E source, E destination) {
        return getEdge(vertices.get(source), vertices.get(destination));
    }

    public int outDegree(Vertex<E> vertex) {
        int count = 0;
        for (Edge<E,D> edge : edges.values()){
            if (edge.getSource() == vertex) {
                count++;
            }
        }
        return count;
    }

    public int inDegree(Vertex<E> vertex) {
        int count = 0;
        for (Edge<E,D> edge : edges.values()){
            if (edge.getDestination() == vertex) {
                count++;
            }
        }
        return count;
    }

    public Vertex<E> insertVertex(E element) {
        return vertices.put(element, new Vertex<E>(element));
    }

    public Edge<E, D> insertEdge(E source, E destination, D x) {
        return edges.put(x, new Edge<E,D>(source, destination, x));
    }

    public boolean deleteVertex(Vertex<E> u) {
        return vertices.remove(u.getElement(), u);
    }

    public boolean deleteVertex(E element) {
        return vertices.remove(element, vertices.get(element));
    }

    public boolean deleteEdge(E source, E destination) {
        Edge<E,D> edgeToDelete = getEdge(source, destination);
        return edges.remove(edgeToDelete.getElement(), edgeToDelete);
    }

    public void printAdjacencyMatrix() {
        // TODO
    }

    public void drawGraph() {
        // TODO
    }
    
}
