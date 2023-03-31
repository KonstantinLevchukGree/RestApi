package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ZipCodesTest extends BaseTest {
    @Test
    public void checkAvailableZipCodes() {
        List<String> zipCodes = ApplicationClient.getZipCodes();
        Assertions.assertNotNull(zipCodes, "Zip codes are not available");
    }

    @Test
    public void checkAddZipCodes() {
        String zipCodes = ApplicationClient.getBodyZipCodes();
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(zipCodes);
        List<String> afterExpandZipCodes = ApplicationClient.getZipCodes();
        Assertions.assertTrue(afterExpandZipCodes.containsAll(expandZipCodes), "Zip codes not added");
    }

    @Test
    public void checkDuplicateZipCodes() {
        String allZipCodes = ApplicationClient.getBodyZipCodes();
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(allZipCodes);
        List<String> afterExpandZipCodes = ApplicationClient.getZipCodes();
        Set<String> set = new LinkedHashSet<>(afterExpandZipCodes);
        //Zip code are duplicated. Bug
        Assertions.assertEquals(set.size(), expandZipCodes.size(), "Zip codes are duplicated");
    }

    @Test
    public void checkDuplicateAvailableAndUsedZipCodes() {
        String allZipCodes = ApplicationClient.getBodyZipCodes();
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(allZipCodes);
        List<String> afterExpandZipCodes = ApplicationClient.getZipCodes();
        //Zip code are duplicated. Bug
        Assertions.assertFalse(expandZipCodes.containsAll(afterExpandZipCodes), "Zip codes are duplicated");
    }
}
