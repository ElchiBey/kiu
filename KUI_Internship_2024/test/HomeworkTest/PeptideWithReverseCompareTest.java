package HomeworkTest;

import Homeworks.Peptides.PeptideInstance;
import Homeworks.Peptides.PeptidesWithReverseCompare;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PeptideWithReverseCompareTest {
    private PeptidesWithReverseCompare peptides;
    private String peptide;
    private String protein;
    private Map<String, List<Integer>> foundDuplicatePeptides = new HashMap<>();

    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        var library = List.of(peptide, "ORNLKDGH", "ABCDEFGH");
        peptides = new PeptidesWithReverseCompare(PeptideInstance.DEFAULT_PEPTIDE_SIZE, protein, library);
        foundDuplicatePeptides = peptides.searchPeptideLibrary();
    }

    @Test
    public void test() {
        List<Integer> positions = peptides.search(peptide);
        assertTrue(positions.contains(3));
        assertTrue(positions.contains(23));
    }

    @Test
    public void testPeptidesExistAndFilledCorrectly() throws Exception {
        assertNotNull(peptides.peptidesMap);
        assertEquals(3, peptides.peptidesMap.size());
    }

    @Test
    public void testNonExistingPeptide() throws Exception {
        assertEquals(List.of(), peptides.peptidesMap.get("ABCDEFGH"));
    }

    @Test
    public void testFoundPeptides() throws Exception {
        assertEquals(2, foundDuplicatePeptides.size());
    }

    @Test
    public void testPeptidesSearch() throws Exception {
        assertNotNull(foundDuplicatePeptides);
    }
}
