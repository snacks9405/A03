public class App {
    public static <E, D> void main(String[] args) throws Exception {
        AdjacencyMatrixGraph<String, Integer> myGraph = GraphBuilder.buildGraphFromFile("graph.txt");
        myGraph.printVerticesAndEdges();
        myGraph.printAdjacencyMatrix();
        myGraph.drawGraph();
    }
}
