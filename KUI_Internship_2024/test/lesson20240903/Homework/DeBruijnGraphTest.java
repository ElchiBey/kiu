package lesson20240903.Homework;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DeBruijnGraphTest {

    private String originalGenome;
    private int k;
    DeBruijnGraph graph;
    List<String> deBruijnPath;

    @Before
    public void setup() {
        // example from given notes
        originalGenome = "GATCACAGGTCTATCACCCTATTAACCACTCACGGGAGCTCTCCATGCATTTGGTATTTTCGTCTGGGGGGTGTGCACGCGATAGCATTGCGAGACGCTGGAGCCGGAGCACCCTATGTCGCAGTATCTGTCTTTGATTCCTGCCTCATTCTATTATTTATCGCACCTACGTTCAATATTACAGGCGAACATACCTACTAAAGTGTGTTAATTAATTAATGCTTGTAGGACATAATAATAACAATTGAAT";
        k = 4;
        graph = new DeBruijnGraph();
        graph.buildGraph(originalGenome, k);
        deBruijnPath = graph.findEulerianPath();
    }

    @Test
    public void testReconstructedGenome() {
        String reconstructedGenome = graph.reconstructGenome(deBruijnPath, k);
        assertEquals(originalGenome, reconstructedGenome);
    }

}