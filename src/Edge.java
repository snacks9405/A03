/**
 * Edge<E,D> class built for the AdjacenyMatrixGraph class
 * 
 */
public class Edge<E,D> {
    E source; 
    E destination; 
    D element; 

    /**
     * constructor for the Edge class
     * @param source
     * @param destination
     * @param element
     */
    Edge(E source, E destination, D element) {
        this.source = source;
        this.destination = destination;
        this.element = element;
    }//Edge constructor

    /**
     * returns element
     * @return
     */
    D getElement() {
        return element;
    }//getElement method

    /**
     * returns source
     * @return
     */
    E getSource() {
        return source;
    }//getSource method

    /**
     * returns destination
     * @return
     */
    E getDestination() {
        return destination;
    }//getDestination method

    /**
     * returns a string in format source->destination(element)
     */
    @Override
    public String toString() {
        return (source + "->" + destination + "(" + element + ")");
    }//toString method
}//Edge class