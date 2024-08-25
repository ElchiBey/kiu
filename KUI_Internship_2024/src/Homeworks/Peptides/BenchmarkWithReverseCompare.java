package Homeworks.Peptides;

import java.util.List;

public class BenchmarkWithReverseCompare {

    private static final int PROTEIN_SIZE = 10_000;
    private static final int LIBRARY_SIZE = 100_000;
    static final byte[] ALPHABET = new byte[26];

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    public static void main(String[] args) {

        System.out.println("Generating data...");

        String protein = PeptideInstance.generateRandomProtein(PROTEIN_SIZE);
        List<String> library = PeptideInstance.generatePeptideLibrary(LIBRARY_SIZE);
        PeptidesWithReverseCompare peptides = new PeptidesWithReverseCompare(PeptideInstance.DEFAULT_PEPTIDE_SIZE, protein, library);

        System.out.println("Searching peptides...");
        long start = System.currentTimeMillis();
        peptides.slideAndCheck();
        long stop = System.currentTimeMillis();

        peptides.searchPeptideLibrary();
        peptides.peptidesMap.forEach((peptide, pos) -> {
            System.out.println("Peptide: " + peptide + " found at peptidesMap: " + pos);
        });

        System.out.println("Elapsed: " + (stop - start) + " ms");

    }

}
