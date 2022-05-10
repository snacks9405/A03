public class App {
    public static <E, D> void main(String[] args) throws Exception {
        AdjacencyMatrixGraph<String, Integer> myGraph = GraphBuilder.buildGraphFromFile("graph.txt");
        myGraph.printVerticesAndEdges();
        myGraph.printAdjacencyMatrix();
        myGraph.drawGraph();
        myGraph.deleteEdge("ATW", "ORD");
        myGraph.printVerticesAndEdges();
        myGraph.drawGraph();
        myGraph.deleteVertex("ATW");
        myGraph.drawGraph();
        myGraph.printVerticesAndEdges();
        myGraph.deleteEdge("MSN", "ORD");
        myGraph.drawGraph();
    }
}
