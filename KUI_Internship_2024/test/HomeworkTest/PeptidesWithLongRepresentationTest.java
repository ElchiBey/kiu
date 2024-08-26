package HomeworkTest;

import Homeworks.Peptides.PeptidesWithLongRepresentation;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lesson20240814.Peptides.DEFAULT_PEPTIDE_SIZE;
import static org.junit.Assert.*;

public class PeptidesWithLongRepresentationTest {

    private PeptidesWithLongRepresentation peptides;
    private String peptide;
    private String protein;
    private Map<String, List<Integer>> foundDuplicatePeptides = new HashMap<>();


    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        var library = List.of(peptide, "ORNLKDGH", "ABCDEFGH");
        peptides = new PeptidesWithLongRepresentation(DEFAULT_PEPTIDE_SIZE, protein, library);
        peptides.slideAndCheck();
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
        assertEquals(List.of(), peptides.peptidesMap.get(peptides.encode("ABCDEFGH")));
    }

    @Test
    public void testFoundPeptides() throws Exception {
        assertEquals(2, foundDuplicatePeptides.size());
    }
}