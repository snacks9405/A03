public class App {
    public static void main(String[] args) throws Exception {
        AdjacencyMatrixGraph<String, Integer> myGraph = GraphBuilder.buildGraphFromFile("graph.txt");
        myGraph.printAdjacencyMatrix();
    }
}
