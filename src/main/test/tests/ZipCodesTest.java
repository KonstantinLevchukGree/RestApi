package tests;

import configurate.RequestApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ZipCodesTest extends BaseTest {
    @Test
    public void checkAvailableZipCodes() {
        //must be 200. Bug
        List<String> zipCodes = RequestApi.getListZipCodes();
        Assertions.assertNotNull(zipCodes, "Zip codes are not available");
    }

    @Test
    public void checkAddZipCodes() {
        String allZipCodes = RequestApi.getJsonZipCodes();
        List<String> expandZipCodes = RequestApi.expandZipCodes(allZipCodes);
        List<String> afterExpandAllZipCodes = RequestApi.getListZipCodes();
        Assertions.assertTrue(afterExpandAllZipCodes.containsAll(expandZipCodes), "Zip codes not added");
    }

    @Test
    public void checkDuplicateZipCodes() {
        String allZipCodes = RequestApi.getJsonZipCodes();
        List<String> expandZipCodes = RequestApi.expandZipCodes(allZipCodes);
        List<String> afterExpandAllZipCodes = RequestApi.getListZipCodes();
        Set<String> set = new LinkedHashSet<>(afterExpandAllZipCodes);
        //Zip code are duplicated. Bug
        Assertions.assertEquals(set.size(), expandZipCodes.size(), "Zip codes are duplicated");
    }

    @Test
    public void checkDuplicateAvailableAndUsedZipCodes() {
        String allZipCodes = RequestApi.getJsonZipCodes();
        List<String> expandZipCodes = RequestApi.expandZipCodes(allZipCodes);
        List<String> afterExpandAllZipCodes = RequestApi.getListZipCodes();
        //Zip code are duplicated. Bug
        Assertions.assertFalse(expandZipCodes.containsAll(afterExpandAllZipCodes), "Zip codes are duplicated");
    }
}
