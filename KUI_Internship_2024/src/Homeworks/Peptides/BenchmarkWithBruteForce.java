package Homeworks.Peptides;

import java.util.List;

import static Homeworks.Peptides.PeptideInstance.*;

public class BenchmarkWithBruteForce {

    public static void main(String[] args) {

        System.out.println("generating data...");

        String protein = PeptideInstance.generateRandomProtein(PROTEIN_SIZE);
        List<String> library = PeptideInstance.generatePeptideLibrary(LIBRARY_SIZE);
        PeptidesWithBruteForce peptides = new PeptidesWithBruteForce(DEFAULT_PEPTIDE_SIZE, protein, library);

        System.out.println("searching peptides...");
        long start = System.currentTimeMillis();
        peptides.searchLibrary();
        long stop = System.currentTimeMillis();

        System.out.println(peptides.searchLibrary());

        System.out.println("Elapsed: " + (stop - start) + " ms");
    }

}
