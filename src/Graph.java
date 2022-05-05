public interface Graph<E, D> {
    int numVertices();
    int numEdges();

    Iterable<Vertex<E>> vertices();
    Iterable<Edge<E,D>> edges();

    Edge<E,D> getEdge(Vertex<E> u, Vertex<E> V);
    Edge<E,D> getEdge(E source, E destination);

    int outDegree(Vertex<E> vertex);
    int inDegree(Vertex<E> vertex);

    Vertex<E> insertVertex(E element);
    Edge<E,D> insertEdge(E source, E destination, D x);

    boolean deleteVertex(Vertex<E> u);
    boolean deleteVertex(E element);
    boolean deleteEdge(E source, E destination);
}
