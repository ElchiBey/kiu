package Homeworks.Peptides;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static Homeworks.Peptides.PeptideInstance.*;

public class BenchmarkWithBinarySearch {


    public static void main(String[] args) {

        System.out.println("Generating data...");

//        String protein = generateRandomProtein(PROTEIN_SIZE);
        String protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
//        List<String> library = generatePeptideLibrary(LIBRARY_SIZE);
        List<String> library = List.of("RNLKDGHI","ORNLKDGH", "ABCDEFGH");
        library = List.of("ABCDEFGH");


        System.out.println("Initializing peptide search with RadixSort and Binary Search...");
        long initStart = System.currentTimeMillis();
        PeptidesWithBinarySearch peptidesSearch = new PeptidesWithBinarySearch(
                PeptideInstance.DEFAULT_PEPTIDE_SIZE,
                protein,
                library
        );
        long initEnd = System.currentTimeMillis();
        System.out.println("Initialization time: " + (initEnd - initStart) + " ms");

        System.out.println("Searching peptides in protein...");
        long searchStart = System.currentTimeMillis();
        List<Integer> matchingPositions = peptidesSearch.searchProtein();
        long searchEnd = System.currentTimeMillis();
        System.out.println("Search time: " + (searchEnd - searchStart) + " ms");

        System.out.println("Total matches found: " + matchingPositions.size());

    }
}
