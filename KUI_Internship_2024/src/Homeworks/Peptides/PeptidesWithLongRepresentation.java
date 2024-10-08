package Homeworks.Peptides;

import java.util.*;

import static Homeworks.Peptides.PeptideInstance.ALPHABET_SIZE;

public class PeptidesWithLongRepresentation {

    private final List<String> library;
    private final String protein;
    private final int peptideSize;
    public Map<Long, List<Integer>> peptidesMap = new HashMap<>();

    public PeptidesWithLongRepresentation(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createPeptidesDictionary(library);
    }

    public long encode(String peptide) {
        long result = Character.getNumericValue(peptide.charAt(0));
        for (int i = 1; i < peptide.length(); i++) {
            result |= Character.getNumericValue(peptide.charAt(i));
            result <<= 8;
        }
        return result;
    }

    private void createPeptidesDictionary(List<String> library) {

        for (String peptide : library) {
            long encodedPeptide = encode(peptide);
            if (!peptidesMap.containsKey(encodedPeptide)) {
                peptidesMap.put(encodedPeptide, new ArrayList<>());
            }
        }
    }

    public List<Integer> search(String peptide) {
        return peptidesMap.getOrDefault(encode(peptide), List.of());
    }

    public void slideAndCheck() {
        for (int i = 0; i <= protein.length() - peptideSize; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            long encodedKmer = encode(kmer);
            List<Integer> positions = peptidesMap.get(encodedKmer);
            if (null != positions) {
                positions.add(i);
            }
        }
    }

    public Map<String, List<Integer>> searchPeptideLibrary() {
        LinkedHashMap<String, List<Integer>> existingPeptides = new LinkedHashMap<>();
        for (String peptide : library) {
            long encodedPeptide = encode(peptide);
            List<Integer> positions = peptidesMap.get(encodedPeptide);

            if (!positions.isEmpty())
                existingPeptides.put(peptide, positions);
        }

        return existingPeptides;
    }
}
