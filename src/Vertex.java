/**
 * Vertex<E> class built for the AdjacenyMatrixGraph class
 * 
 */
public class Vertex<E> {
    E element;

    /**
     * Constructor for Vertex class
     * @param element
     */
    Vertex(E element) {
        this.element = element;
    }//Vertex constructor

    /**
     * returns the element 
     * @return
     */
    E getElement() {
        return element;
    }//getElement method

    /**
     * returns the element in a string
     */
    @Override
    public String toString() {
        return element.toString();
    }//toString method
}//Vertex class