import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyMatrixGraph<E, D> implements Graph<E, D> {
    private HashMap<E, Vertex<E>> vertices = new HashMap<>();
    private HashMap<Edge<E, D>, D> edges = new HashMap<>();

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
        return edges.keySet();
    }

    public Vertex<E> getVertex(E element) {
        for (Vertex<E> vertex : vertices()) {
            if (vertex.getElement().equals(element)) {
                return vertex;
            }
        }
        return null;
    }

    public Edge<E, D> getEdge(Vertex<E> u, Vertex<E> v) {
        return (getEdge(u.getElement(), v.getElement()));
    }

    public Edge<E, D> getEdge(E source, E destination) {
        for (Edge<E, D> edge : edges()) {
            if ((edge.source.equals(source) && edge.destination.equals(destination))) {
                return edge;
            }
        }
        return null;
    }

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
        vertices.put(element, vertex);
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
        for (Edge<E, D> outEdge : getInOutEdges(getVertex(element), false)) {
            deleteEdge(outEdge.source, outEdge.destination);
        }
        return vertices.remove(element, vertices.get(element));
    }

    public boolean deleteEdge(E source, E destination) {
        Edge<E, D> edgeToDelete = getEdge(source, destination);
        return edges.remove(edgeToDelete, edgeToDelete.getElement());
    }

    public void printVerticesAndEdges() {
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
                printIndex = 1;
            }
        }
        // add 2d matrix here. populate with above and below loops.
        System.out.printf("edges: ");

        for (Edge<E, D> printEdge : edges()) {
            System.out.printf(printEdge.toString());
            if (printIndex < edges.size()) {
                System.out.printf(", ");
                printIndex++;
            } else {
                System.out.println();
            }
        }
        // TODO
    }

    public void printAdjacencyMatrix() {
        for (Vertex<E> source : vertices()) {
            if (!source.element.equals("ORD")) {
                System.out.print(" ");
            }
            for (Vertex<E> destination : vertices()) {
                System.out.printf("%15s|",
                        getEdge(source, destination) == null ? "null" : getEdge(source, destination).toString());

            }
            System.out.println();
        }

    }

    public void drawGraph() {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setScale(-250, 250);
        HashMap<Vertex<E>, double[]> verticesCoords = new HashMap<>();
        double RADIUS = 200;

        double vertexIndex = 0;
        for (Vertex<E> vertex : vertices()) {
            double angleDegrees = vertexIndex * (360 / vertices.size());
            double[] coordsXY = new double[2];
            coordsXY[0] = RADIUS * (Math.cos(Math.toRadians(angleDegrees)));
            coordsXY[1] = RADIUS * (Math.sin(Math.toRadians(angleDegrees)));
            verticesCoords.put(vertex, coordsXY);
            vertexIndex++;
        }

        drawEdges(verticesCoords);
        makeVerticesPretty(verticesCoords, RADIUS);
    }

    private void drawEdges(HashMap<Vertex<E>, double[]> verticesCoords) {
        ArrayList<Edge<E, D>> twoWayLinks = new ArrayList<>();
        for (Vertex<E> vertex : verticesCoords.keySet()) {
            double[] originXY = verticesCoords.get(vertex);
            double originX = originXY[0];
            double originY = originXY[1];
            for (Edge<E, D> edge : getInOutEdges(vertex, true)) {
                Vertex<E> destination = vertices.get(edge.destination);
                double[] destinationXY = verticesCoords.get(destination);
                double destinationX = destinationXY[0];
                double destinationY = destinationXY[1];
                double textX = (originX + destinationX) / 2;
                double textY = (originY + destinationY) / 2;
                if (getEdge(destination, vertices.get(edge.source)) == null) {
                    StdDraw.line(originX, originY, destinationX, destinationY);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledCircle(textX, textY, 20);
                    StdDraw.setPenColor();
                    StdDraw.circle(textX, textY, 20);
                    StdDraw.text(textX, textY, edge.getElement().toString());
                } else {
                    twoWayLinks.add(edge);
                    // StdDraw.line(originX, originY, textX + 20, textY + 20);
                    // StdDraw.line(textX + 20, textY + 20, destinationX, destinationY);
                }
                // StdDraw.line(x0, y0, x1, y1);
            }
        }

        drawTwoWayLinks(twoWayLinks, verticesCoords);
    }

    private void drawTwoWayLinks(ArrayList<Edge<E,D>> twoWayLinks, HashMap<Vertex<E>, double[]> verticesCoords){
        while (!twoWayLinks.isEmpty()) {
            Edge<E, D> edge1 = twoWayLinks.get(0);
            double[] originXY = verticesCoords.get(vertices.get(edge1.source));
            double[] destinationXY = verticesCoords.get(vertices.get(edge1.destination));
            double originX = originXY[0];
            double originY = originXY[1];
            double destinationX = destinationXY[0];
            double destinationY = destinationXY[1];
            double textX = (originX + destinationX) / 2;
            double textY = (originY + destinationY) / 2;
            Edge<E, D> edgeToGrab = getEdge(edge1.destination, edge1.source);
            Edge<E, D> edge2 = new Edge<E, D>(null, null, null);
            twoWayLinks.remove(0);
            for (int i = 0; i < twoWayLinks.size(); i++) {
                if (twoWayLinks.get(i).equals(edgeToGrab)) {
                    edge2 = twoWayLinks.get(i);
                    twoWayLinks.remove(i);
                    break;
                }
            }
            double xOffset = 20;

            StdDraw.line(originX, originY, textX - xOffset, textY);
            StdDraw.line(textX - xOffset, textY, destinationX, destinationY);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(textX - xOffset, textY, 20);
            StdDraw.setPenColor();
            StdDraw.circle(textX - xOffset, textY, 20);
            StdDraw.text(textX - xOffset, textY, edge1.element.toString());

            
            StdDraw.line(originX, originY, textX + xOffset, textY);
            StdDraw.line(textX + xOffset, textY, destinationX, destinationY);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(textX + xOffset, textY, 20);
            StdDraw.setPenColor();
            StdDraw.circle(textX + xOffset, textY, 20);
            StdDraw.text(textX + xOffset, textY, edge2.getElement().toString());
        }
    }

    private void makeVerticesPretty(HashMap<Vertex<E>, double[]> verticesCoords, double radius) {
        for (Vertex<E> vertex : verticesCoords.keySet()) {
            double[] coordsXY = verticesCoords.get(vertex);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledCircle(coordsXY[0], coordsXY[1], radius / vertices.size());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.circle(coordsXY[0], coordsXY[1], radius / vertices.size());
            StdDraw.text(coordsXY[0], coordsXY[1], vertex.toString());
        }
    }

}
