package Homeworks.Peptides;

import java.util.*;

import static Homeworks.Peptides.PeptideInstance.ALPHABET_SIZE;

public class PeptidesWithBinarySearch {
    private final List<String> library;
    private final String protein;
    private final int peptideSize;
    public ArrayList<Long> peptidesListOfLong = new ArrayList<>();

    public PeptidesWithBinarySearch(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createSortedPeptidesList(library);
    }

    public long encode(String peptide) {
        long result = Character.getNumericValue(peptide.charAt(0));
        for (int i = 1; i < peptide.length(); i++) {
            result |= Character.getNumericValue(peptide.charAt(i));
            result <<= 8;
        }
        return result;
    }

    private void createSortedPeptidesList(List<String> library) {
        for (String peptide : library) {
            long encodedPeptide = encode(peptide);
            peptidesListOfLong.add(encodedPeptide);
        }
        RadixSort.sort(peptidesListOfLong);
    }

    private boolean binarySearch(long key) {
        int low = 0;
        int high = peptidesListOfLong.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            long midVal = peptidesListOfLong.get(mid);

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return true;
        }
        return false;
    }

    public List<Integer> searchProtein() {
        List<Integer> matchingPositions = new ArrayList<>();
        for (int i = 0; i <= protein.length() - peptideSize; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long encodedKmer = encode(kmer);
            if (binarySearch(encodedKmer)) {
                matchingPositions.add(i);
            }
        }
        return matchingPositions;
    }
}
