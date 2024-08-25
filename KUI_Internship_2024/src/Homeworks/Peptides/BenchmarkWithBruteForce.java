package Homeworks.Peptides;

import java.util.List;

public class BenchmarkWithBruteForce {

    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    public static final byte[] ALPHABET = new byte[26];

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    public static void main(String[] args) {

        System.out.println("generating data...");

        String protein = PeptideInstance.generateRandomProtein(PROTEIN_SIZE);
        List<String> library = PeptideInstance.generatePeptideLibrary(LIBRARY_SIZE);
        PeptidesWithBruteForce peptides = new PeptidesWithBruteForce(PeptideInstance.DEFAULT_PEPTIDE_SIZE, protein, library);

        System.out.println("searching peptides...");
        long start = System.currentTimeMillis();
        peptides.searchLibrary();
        long stop = System.currentTimeMillis();

        System.out.println(peptides.searchLibrary());

        System.out.println("Elapsed: " + (stop - start) + " ms");
    }

}
