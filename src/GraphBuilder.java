import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GraphBuilder {
    static int numVertices;
    static int numEdges;

    public static AdjacencyMatrixGraph<String, Integer> buildGraphFromFile(String fileName) {
        Scanner scan = null;
        AdjacencyMatrixGraph<String, Integer> myGraph = new AdjacencyMatrixGraph<>();
        try { 
            scan = new Scanner(new File(fileName));
            String[] numsData = scan.nextLine().split(",");
            numVertices = Integer.parseInt(numsData[0]);
            numEdges = Integer.parseInt(numsData[1]);
            for (int i = 0; i < numVertices; i++) {
                myGraph.insertVertex(scan.nextLine());
            }
            for (int i = 0; i < numEdges; i++) {
                String[] lineData = scan.nextLine().split(",");
                myGraph.insertEdge(lineData[0], lineData[1], Integer.parseInt(lineData[2]));
            }
        } catch (IOException e) {
            System.out.println("File not found");
        } finally {
            if (scan != null) {
                scan.close();
            }
        }
       
        return myGraph;
    }
}
