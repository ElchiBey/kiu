package Homeworks.Peptides;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PeptidesWithBruteForce {

    private final String protein;
    private final int peptideSize;

    public HashMap<String, List<Integer>> kmers = new LinkedHashMap<>();

    private final List<String> library;

    public PeptidesWithBruteForce(int peptideSize, String protein, List<String> library) {
        this.peptideSize = peptideSize;
        this.protein = protein;
        this.library = library;
        createKMersDictionary();
    }

    void createKMersDictionary() {
        for (int i = 0; i < protein.length() - peptideSize + 1; i++) {
            String kmer = protein.substring(i, i + peptideSize);
            List<Integer> positions = kmers.computeIfAbsent(kmer, k -> new ArrayList<>());
            positions.add(i);
        }
    }

    public List<Integer> search(String peptide) {
        return kmers.getOrDefault(peptide, List.of());
    }

    public Map<String, List<Integer>> searchLibrary() {
        LinkedHashMap<String, List<Integer>> existingPeptides = new LinkedHashMap<>();
        for (String peptide : library) {
            List<Integer> positions = kmers.get(peptide);
            if (null == positions) {
                continue;
            }
            existingPeptides.put(peptide, positions);
        }
        return existingPeptides;
    }
}
