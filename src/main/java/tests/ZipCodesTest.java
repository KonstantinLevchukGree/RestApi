package tests;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ZipCodesTest extends BaseTest {
    @Test
    public void checkAvailableZipCodes() {
        //must be 200. Bug
        List<String> zipCodes = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        Assertions.assertNotNull(zipCodes, "Zip codes are not available");
    }

    @Test
    public void checkAddZipCodes() {
        String zipCodes = ApplicationClient.getBodyZipCodes(HttpStatus.SC_OK);
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(zipCodes,HttpStatus.SC_CREATED);
        List<String> afterExpandAllZipCodes = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        Assertions.assertTrue(afterExpandAllZipCodes.containsAll(expandZipCodes), "Zip codes not added");
    }

    @Test
    public void checkDuplicateZipCodes() {
        String allZipCodes = ApplicationClient.getBodyZipCodes(HttpStatus.SC_OK);
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(allZipCodes,HttpStatus.SC_CREATED);
        List<String> afterExpandAllZipCodes = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        Set<String> set = new LinkedHashSet<>(afterExpandAllZipCodes);
        //Zip code are duplicated. Bug
        Assertions.assertEquals(set.size(), expandZipCodes.size(), "Zip codes are duplicated");
    }

    @Test
    public void checkDuplicateAvailableAndUsedZipCodes() {
        String allZipCodes = ApplicationClient.getBodyZipCodes(HttpStatus.SC_OK);
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(allZipCodes,HttpStatus.SC_CREATED);
        List<String> afterExpandAllZipCodes = ApplicationClient.getZipCodes(HttpStatus.SC_OK);
        //Zip code are duplicated. Bug
        Assertions.assertFalse(expandZipCodes.containsAll(afterExpandAllZipCodes), "Zip codes are duplicated");
    }
}
