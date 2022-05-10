/**
 * Description: Assignment 3
 * Author: alex rodriguez 
 * Date: 5.9.22
 * Bugs: n/a
 * Reflection: so tired of the stdDraw. didn't put arrows because I needed
 * to push back against that class somehow. assume you'd have to find delta and build a 
 * polygon and more math I'm not going to do tonight.
 * 
 * the assignment was great. having to draw it was dumb and I don't think it added to my knowledge of a graph
 * structure in any meaningful way let alone a 5/20 points meaningful way. 
 */
public class Assignment3 {
    public static <E, D> void main(String[] args) throws Exception {
        AdjacencyMatrixGraph<String, Integer> myGraph = GraphBuilder.buildGraphFromFile("graph.txt");
        myGraph.printVerticesAndEdges();
        myGraph.printAdjacencyMatrix();
        myGraph.drawGraph();
    }
}
