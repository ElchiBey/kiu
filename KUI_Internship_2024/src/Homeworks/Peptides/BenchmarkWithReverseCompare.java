package Homeworks.Peptides;

import java.util.List;

import static Homeworks.Peptides.PeptideInstance.*;

public class BenchmarkWithReverseCompare {

    public static void main(String[] args) {

        System.out.println("Generating data...");

        String protein = PeptideInstance.generateRandomProtein(PROTEIN_SIZE);
        List<String> library = PeptideInstance.generatePeptideLibrary(LIBRARY_SIZE);
        PeptidesWithReverseCompare peptides = new PeptidesWithReverseCompare(DEFAULT_PEPTIDE_SIZE, protein, library);

        System.out.println("Searching peptides...");
        long start = System.currentTimeMillis();
        peptides.slideAndCheck();
        long stop = System.currentTimeMillis();

        System.out.println("Elapsed: " + (stop - start) + " ms");

    }

}
