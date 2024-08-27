package HomeworkTest;

import Homeworks.Peptides.PeptidesWithBinarySearch;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static lesson20240814.Peptides.DEFAULT_PEPTIDE_SIZE;
import static org.junit.Assert.*;

public class PeptideWithBinarySearchTest {

    private PeptidesWithBinarySearch peptides;
    private String peptide;
    private String protein;
    private List<Integer> duplicatePositions = new ArrayList<>();

    @Before
    public void setup() {
        peptide = "RNLKDGHI";
        protein = "ABERNLKDGHIHWEPOGCVNWOORNLKDGHIMXVNXMCWERY";
        var library = List.of(peptide, "ORNLKDGH", "ABCDEFGH");
        peptides = new PeptidesWithBinarySearch(DEFAULT_PEPTIDE_SIZE, protein, library);
        duplicatePositions = peptides.searchProtein();
    }

    @Test
    public void testSearch() {
        assertEquals(List.of(3, 22, 23), duplicatePositions);
    }

    @Test
    public void testPeptideExistsAndFound() {
        assertNotNull(duplicatePositions);
    }

    @Test
    public void testPeptideNotFound() {
        var library = List.of("ABCDEFGH");
        peptides = new PeptidesWithBinarySearch(DEFAULT_PEPTIDE_SIZE, protein, library);
        duplicatePositions = peptides.searchProtein();
        assertEquals(List.of(), duplicatePositions);
    }

}
