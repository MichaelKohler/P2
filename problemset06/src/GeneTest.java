import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class GeneTest {

    @Test
    public void attributesShouldBeCorrect() {
        Gene gene = new Gene("testgene", 5, 1);
        
        assertTrue(gene.getGene().equalsIgnoreCase("testgene"));
        assertTrue(gene.getPrice() == 5);
        assertTrue(gene.getMutationPoints() == 1);
    }
}