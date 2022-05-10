import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyMatrixGraph<E, D> implements Graph<E, D> {
    private HashMap<E, Vertex<E>> vertices = new HashMap<>(); //stores vertices and their element with their unique element being the key
    private HashMap<Edge<E, D>, D> edges = new HashMap<>(); //stores edges and their element with the edge as the key

    /**
     * returns number of vertices
     */
    public int numVertices() {
        return vertices.size();
    }// numVertices method

    /**
     * returns number of edges
     */
    public int numEdges() {
        return edges.size();
    }// numEdges method

    /**
     * returns iterable of vertices
     */
    public Iterable<Vertex<E>> vertices() {
        return vertices.values();
    }// vertices method

    /**
     * returns iterable of edges
     */
    public Iterable<Edge<E, D>> edges() {
        return edges.keySet();
    }// edges method

    /**
     * returns vertex if present else null
     * 
     * @param element
     * @return
     */
    public Vertex<E> getVertex(E element) {
        for (Vertex<E> vertex : vertices()) {
            if (vertex.getElement().equals(element)) {
                return vertex;
            }
        }
        return null;
    }// getVertex method

    /**
     * returns edge if present else null
     */
    public Edge<E, D> getEdge(Vertex<E> u, Vertex<E> v) {
        return (getEdge(u.getElement(), v.getElement()));
    }// getEdge method

    /**
     * returns edge if present else null
     */
    public Edge<E, D> getEdge(E source, E destination) {
        for (Edge<E, D> edge : edges()) {
            if ((edge.source.equals(source) && edge.destination.equals(destination))) {
                return edge;
            }
        }
        return null;
    }// getEdge method

    /**
     * compiles an ArrayList of all edges attached to vertex: source.
     * if outOnly, compiles only the outward edges.
     * 
     * @param source
     * @param outOnly
     * @return
     */
    public ArrayList<Edge<E, D>> getInOutEdges(Vertex<E> source, boolean outOnly) {
        ArrayList<Edge<E, D>> inOutEdges = new ArrayList<>();
        for (Vertex<E> destination : vertices()) {
            Edge<E, D> outMatchingEdge = getEdge(source, destination);
            Edge<E, D> inMatchingEdge = getEdge(destination, source);
            if (outMatchingEdge != null) {
                inOutEdges.add(outMatchingEdge);
            }
            if (inMatchingEdge != null && !outOnly) {
                inOutEdges.add(inMatchingEdge);
            }
        }
        return inOutEdges;
    }// getInOutEdges method

    /**
     * returns the out degree of the vertex
     */
    public int outDegree(Vertex<E> vertex) {
        int count = 0;
        for (Edge<E, D> edge : edges()) {
            if (edge.getSource().equals(vertex.element)) {
                count++;
            }
        }
        return count;
    }// outDegree method

    /**
     * returns the in degree of the vertex
     */
    public int inDegree(Vertex<E> vertex) {
        int count = 0;
        for (Edge<E, D> edge : edges()) {
            if (edge.getDestination().equals(vertex.element)) {
                count++;
            }
        }
        return count;
    }// inDegree method

    /**
     * inserts a new vertex with element
     */
    public Vertex<E> insertVertex(E element) {
        Vertex<E> vertex = new Vertex<>(element);
        vertices.put(element, vertex);
        return vertex;
    }// insertVertex method

    /**
     * inserts an edge between Vertex: source and Vertex: destination
     * with value: element
     */
    public Edge<E, D> insertEdge(E source, E destination, D element) {
        if (!vertices.containsKey(source) || !vertices.containsKey(destination)) {
            return null;
        }
        Edge<E, D> edge = new Edge<>(source, destination, element);
        edges.put(edge, element);
        return edge;
    }// insertEdge method

    /**
     * returns true if vertex is deleted. else false
     */
    public boolean deleteVertex(Vertex<E> u) {
        return deleteVertex(u.getElement());
    }// deleteVertex method

    /**
     * returns true if vertex is deleted. else false
     * find vertex by element
     */
    public boolean deleteVertex(E element) {
        for (Edge<E, D> outEdge : getInOutEdges(getVertex(element), false)) {
            deleteEdge(outEdge.source, outEdge.destination);
        }
        return vertices.remove(element, vertices.get(element));
    }// deleteVertex method

    /**
     * deletes edge between Vertex: source and Vertex: destination
     * true if successful
     */
    public boolean deleteEdge(E source, E destination) {
        Edge<E, D> edgeToDelete = getEdge(source, destination);
        return edges.remove(edgeToDelete, edgeToDelete.getElement());
    }// deleteEdge method

    /**
     * prints vertex and edge data to the console
     * by iterating through each hashmap
     */
    public void printVerticesAndEdges() {
        int printIndex = 1; // assists in formatting the last entry in each output.
        System.out.printf("# of vertices: %d\n", vertices.size());
        System.out.printf("# of edges: %d\n", edges.size());
        System.out.printf("vertices: ");
        for (Vertex<E> printVertex : vertices()) { // vertices first...
            System.out.printf("(%s, %d, %d)", printVertex.element, inDegree(printVertex), outDegree(printVertex));
            if (printIndex < numVertices()) {
                System.out.printf(", ");
                printIndex++;
            } else {
                System.out.println();
                printIndex = 1; // reset for edges.
            }
        }

        System.out.printf("edges: "); // then the edges.
        for (Edge<E, D> printEdge : edges()) {
            System.out.printf(printEdge.toString());
            if (printIndex < numEdges()) {
                System.out.printf(", ");
                printIndex++;
            } else {
                System.out.println();
            }
        }
    }// printVerticesAndEdges method

    /**
     * prints something resembling an adjacency matrix to the console
     */
    public void printAdjacencyMatrix() {
        for (Vertex<E> source : vertices()) {
            for (Vertex<E> destination : vertices()) {
                System.out.printf("%15s|",
                        getEdge(source, destination) == null ? "null" : getEdge(source, destination).toString()); // check
                                                                                                                  // for
                                                                                                                  // nulls!

            }
            System.out.println();
        }
    }// printAdjacencyMatrix method

    /**
     * draws the graph using the StdDraw class.
     */
    public void drawGraph() {
        StdDraw.setCanvasSize(500, 500); // hardcoded dimensions in to suit our particular needs for this project.
        StdDraw.setScale(-250, 250);
        HashMap<Vertex<E>, double[]> verticesCoords = new HashMap<>(); // going to store each vertex's coordinates in
                                                                       // this.
        double RADIUS = 200; // the radius of the circle we will attach each vertex to to ensure even
                             // spacing.

        double vertexIndex = 0; // used to calculate the unique angle of each vertex added to the graph.
        for (Vertex<E> vertex : vertices()) {
            double angleDegrees = vertexIndex * (360 / numVertices()); // each angle in radians is 360 divided by the
                                                                       // total number of vertices multiplied by the
                                                                       // index.
            double[] coordsXY = new double[2]; // store the coordinates in this {x, y}
            coordsXY[0] = RADIUS * (Math.cos(Math.toRadians(angleDegrees))); // x = r(cos(angle))
            coordsXY[1] = RADIUS * (Math.sin(Math.toRadians(angleDegrees))); // y = r(sin(angle))
            verticesCoords.put(vertex, coordsXY); // use the vertex as the key containing the coordinates.
            vertexIndex++;
        }

        drawEdges(verticesCoords);
        makeVerticesPretty(verticesCoords, RADIUS);
    }// drawGraph method

    /**
     * helper method for drawGraph. draws the edges between each vertex now that
     * we've calculated the coordinates of each vertex.
     * 
     * @param verticesCoords
     */
    private void drawEdges(HashMap<Vertex<E>, double[]> verticesCoords) {
        ArrayList<Edge<E, D>> twoWayLinks = new ArrayList<>(); // used to store any "special case" edges that go both
                                                               // ways.
        for (Vertex<E> vertex : verticesCoords.keySet()) { // iterate through each vertex stored in verticesCoords
            double[] originXY = verticesCoords.get(vertex); // grab their xy coordinates
            double originX = originXY[0];
            double originY = originXY[1];
            for (Edge<E, D> edge : getInOutEdges(vertex, true)) { // then iterate through edges attached to this vertex
                                                                  // using getInOutEdges().
                Vertex<E> destination = vertices.get(edge.destination); // the vertex at the other end of this
                                                                        // particular edge.
                if (getEdge(destination, vertices.get(edge.source)) == null) { // check if this edge is a special case.
                    double[] destinationXY = verticesCoords.get(destination); // the coordinates of the destination edge
                    double destinationX = destinationXY[0];
                    double destinationY = destinationXY[1];
                    double textX = (originX + destinationX) / 2; // put the text halfway between x1 and x2...
                    double textY = (originY + destinationY) / 2; // and y1 and y2
                    StdDraw.line(originX, originY, destinationX, destinationY); //put the line between them..
                    StdDraw.setPenColor(StdDraw.WHITE); 
                    StdDraw.filledCircle(textX, textY, 20); //make a blank circle..
                    StdDraw.setPenColor();
                    StdDraw.circle(textX, textY, 20); //outline it..
                    StdDraw.text(textX, textY, edge.getElement().toString()); //write the edges element in it.
                } else {
                    twoWayLinks.add(edge); //if it's a special case, add it to the arrayList for processing later.
                }
            }
        }
        drawTwoWayLinks(twoWayLinks, verticesCoords); //send special cases to be handled by drawTwoWayLinks()
    }//drawEdges method

    /**
     * helps to keep edges from overlapping and looking awful.
     * as being a pair is a strict condition of being in the twoWayLinks array,
     * this method will pull pairs of edges out of twoWayLinks and draw them extra special.
     * 
     * @param twoWayLinks
     * @param verticesCoords
     */
    private void drawTwoWayLinks(ArrayList<Edge<E, D>> twoWayLinks, HashMap<Vertex<E>, double[]> verticesCoords) {
        while (!twoWayLinks.isEmpty()) {
            Edge<E, D> edge1 = twoWayLinks.remove(0); //pull and remove the edge we want to work with.            
            double[] originXY = verticesCoords.get(vertices.get(edge1.source)); //since the origin and destination will be the "same" for each pair. we only need one copy.
            double[] destinationXY = verticesCoords.get(vertices.get(edge1.destination));
            double originX = originXY[0];
            double originY = originXY[1];
            double destinationX = destinationXY[0];
            double destinationY = destinationXY[1];
            double textX = (originX + destinationX) / 2;
            double textY = (originY + destinationY) / 2;
            Edge<E, D> edgeToGrab = getEdge(edge1.destination, edge1.source); //get the partner edge's profile to compare.
            Edge<E, D> edge2 = new Edge<E, D>(null, null, null); //generate a null edge to keep errors away.
            for (int i = 0; i < twoWayLinks.size(); i++) {
                if (twoWayLinks.get(i).equals(edgeToGrab)) { //iterate through twoWayLinks to find the edge matching our profile.
                    edge2 = twoWayLinks.remove(i); //nab it and lets draw!
                    break;
                }
            }

            final double XOFFSET = 20; //1/2 the amount we'll offset these edges apart. 

            StdDraw.line(originX, originY, textX - XOFFSET, textY); //origin -> text location (MINUS XOFFSET)
            StdDraw.line(textX - XOFFSET, textY, destinationX, destinationY); //text location -> destination
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(textX - XOFFSET, textY, 20);
            StdDraw.setPenColor();
            StdDraw.circle(textX - XOFFSET, textY, 20);
            StdDraw.text(textX - XOFFSET, textY, edge1.element.toString()); //make a circle and fill it with element.

            //I chose to leave these two seperate despite their "iterableness?" to better illustrate how the process works. 

            StdDraw.line(originX, originY, textX + XOFFSET, textY); //origin -> text location (PLUS OFFSET)
            StdDraw.line(textX + XOFFSET, textY, destinationX, destinationY);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(textX + XOFFSET, textY, 20);
            StdDraw.setPenColor();
            StdDraw.circle(textX + XOFFSET, textY, 20);
            StdDraw.text(textX + XOFFSET, textY, edge2.getElement().toString());
        }
    }//drawTwoWayLinks method

    /**
     * draw the vertex circles and values last so that it overwrites
     * all of those pesky lines. just looks a lot cleaner.
     * @param verticesCoords
     * @param radius
     */
    private void makeVerticesPretty(HashMap<Vertex<E>, double[]> verticesCoords, double radius) {
        for (Vertex<E> vertex : verticesCoords.keySet()) { //iterates through verticesCoords and draws the circles and their values.
            double[] coordsXY = verticesCoords.get(vertex);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(coordsXY[0], coordsXY[1], radius / vertices.size());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.circle(coordsXY[0], coordsXY[1], radius / vertices.size());
            StdDraw.text(coordsXY[0], coordsXY[1], vertex.toString());
        }
    }//makeVerticesPretty method
}//AdjacencyMatrixGraph class