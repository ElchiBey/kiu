package lesson20240903.Homework;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String text = "AAGATTCTCTAAGA";
        int k = 4;

        DeBruijnGraph graph = new DeBruijnGraph();
        graph.buildGraph(text, k);
        graph.printGraph();

        List<String> eulerianPath = graph.findEulerianPath();
        String reconstructedGenome = graph.reconstructGenome(eulerianPath, k);

        System.out.println("Reconstructed Genome: " + reconstructedGenome);
    }
}

