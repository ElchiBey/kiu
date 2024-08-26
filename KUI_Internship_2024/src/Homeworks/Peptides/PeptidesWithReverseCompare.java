package Homeworks.Peptides;

import java.util.*;

public class PeptidesWithReverseCompare {

    private String protein;
    private int peptideSize;

    public HashMap<String, List<Integer>> peptidesMap = new LinkedHashMap<>();

    private List<String> library;

    public PeptidesWithReverseCompare(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createPeptideDictionary();
    }

    void createPeptideDictionary() {
        for (String peptide : library) {
            peptidesMap.computeIfAbsent(peptide, k -> new ArrayList<>());
        }
    }

    public List<Integer> search(String peptide) {
        return peptidesMap.getOrDefault(peptide, List.of());
    }

    public void slideAndCheck() {
        for (int i = 0; i <= protein.length() - peptideSize; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            List<Integer> positions = peptidesMap.get(kmer);
            if (null != positions) {
                positions.add(i);
            }
        }
    }

    public Map<String, List<Integer>> searchPeptideLibrary() {
        LinkedHashMap<String, List<Integer>> existingPeptides = new LinkedHashMap<>();
        for (String peptide : library) {
            List<Integer> positions = peptidesMap.get(peptide);

            if (!positions.isEmpty())
                existingPeptides.put(peptide, positions);
        }

        return existingPeptides;
    }
}
