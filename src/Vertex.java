public class Vertex<E> {
    E element;

    Vertex(E element) {
        this.element = element;
    }

    E getElement() {
        return element;
    }

    @Override
    public String toString() {
        return element.toString();
    }

}
