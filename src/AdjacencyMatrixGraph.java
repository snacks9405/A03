import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyMatrixGraph<E, D> implements Graph<E, D> {
    private HashMap<Vertex<E>, E> vertices = new HashMap<>();
    private HashMap<Edge<E, D>, D> edges = new HashMap<>();

    public int numVertices() {
        return vertices.size();
    }

    public int numEdges() {
        return edges.size();
    }

    public Iterable<Vertex<E>> vertices() {
        return vertices.keySet();
    }

    public Iterable<Edge<E, D>> edges() {
        return edges.keySet();
    }

    public Vertex<E> getVertex(E element){
        for (Vertex<E> vertex : vertices()) {
            if (vertex.getElement().equals(element)){
                return vertex;
            }
        }
        return null;
    }

    public Edge<E, D> getEdge(Vertex<E> u, Vertex<E> v) {
        for (Edge<E, D> edge : edges()) {
            if ((edge.source == u && edge.destination == v)) {
                return edge;
            }
        }
        return null;
    }

    public Edge<E, D> getEdge(E source, E destination) {
        return getEdge(vertices.get(source), vertices.get(destination));
    }

    public ArrayList<Edge<E, D>> getOutEdges(Vertex<E> source) {
        ArrayList<Edge<E, D>> outEdges = new ArrayList<>();
        for (Vertex<E> destination : vertices()) {
            Edge<E, D> matchingEdge = getEdge(source, destination);
            if (matchingEdge != null) {
                outEdges.add(outEdges.size(), matchingEdge);
            }
        }
        return outEdges;
    }

    public int outDegree(Vertex<E> vertex) {
        int count = 0;
        for (Edge<E, D> edge : edges()) {
            if (edge.getSource().equals(vertex.element)) {
                count++;
            }
        }
        return count;
    }

    public int inDegree(Vertex<E> vertex) {
        int count = 0;
        for (Edge<E, D> edge : edges()) {
            if (edge.getDestination().equals(vertex.element)) {
                count++;
            }
        }
        return count;
    }

    public Vertex<E> insertVertex(E element) {
        Vertex<E> vertex = new Vertex<>(element);
        vertices.put(vertex, element);
        return vertex;
    }

    public Edge<E, D> insertEdge(E source, E destination, D element) {        
        Edge<E, D> edge = new Edge<>(source, destination, element);
        edges.put(edge, element);
        return edge;
    }

    public boolean deleteVertex(Vertex<E> u) {
        return deleteVertex(u.getElement());
    }

    public boolean deleteVertex(E element) {
        for (Edge<E, D> outEdge : getOutEdges(getVertex(element))) {
            deleteEdge(outEdge.source, outEdge.destination);
        }
        return vertices.remove(element, vertices.get(element));
    }

    public boolean deleteEdge(E source, E destination) {
        Edge<E, D> edgeToDelete = getEdge(source, destination);
        return edges.remove(edgeToDelete.getElement(), edgeToDelete);
    }

    public void printAdjacencyMatrix() {
        int printIndex = 1;
        System.out.printf("# of vertices: %d\n", vertices.size());
        System.out.printf("# of edges: %d\n", edges.size());
        System.out.printf("vertices: ");

        for (Vertex<E> printVertex : vertices()) {
            System.out.printf("(%s, %d, %d)", printVertex.element, inDegree(printVertex), outDegree(printVertex));
            if (printIndex < vertices.size()) {
                System.out.printf(", ");
                printIndex++;
            } else {
                System.out.println();
                printIndex = 0;
            }
        }
                //add 2d matrix here. populate with above and below loops. 
        System.out.printf("edges: ");

        for (Edge<E, D> printEdge : edges()) {
            System.out.printf("%s->%s(%s)", printEdge.source, printEdge.destination, printEdge.element);
            if (printIndex < edges.size()) {
                System.out.printf(", ");
                printIndex++;
            } else {
                System.out.println();
            }
        }
        // TODO
    }

    public void drawGraph() {
        // TODO
    }

}
