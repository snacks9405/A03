public class Edge<E,D> {
    E source;
    E destination;
    D element;

    Edge(E source, E destination, D element) {
        this.source = source;
        this.destination = destination;
        this.element = element;
    }

    D getElement() {
        return element;
    }

    E getSource() {
        return source;
    }

    E getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return (source + "->" + destination + "(" + element + ")");
    }
}
