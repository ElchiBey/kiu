package Homeworks.Peptides;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeptideInstance {

    public static final int DEFAULT_PEPTIDE_SIZE = 8;
    public static final int ALPHABET_SIZE = 26;
    public static final int PROTEIN_SIZE = 10_000;
    public static final int LIBRARY_SIZE = 100_000;

    static final byte[] ALPHABET = new byte[ALPHABET_SIZE];

    static {
        for (byte c = 'A'; c <= 'Z'; c++) {
            ALPHABET[c - 'A'] = c;
        }
    }

    static String generateRandomProtein(int proteinSize) {
        Random r = new Random();
        var data = new byte[proteinSize];
        for (int i = 0; i < proteinSize; i++) {
            data[i] = ALPHABET[r.nextInt(ALPHABET.length)];
        }
        return new String(data);
    }

    static List<String> generatePeptideLibrary(int librarySize) {
        var library = new ArrayList<String>(librarySize);
        for (int i = 0; i < librarySize; i++) {
            var peptide = generateRandomProtein(DEFAULT_PEPTIDE_SIZE);
            library.add(peptide);
        }
        return library;
    }
}
