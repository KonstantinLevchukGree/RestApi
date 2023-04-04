package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.ApplicationClient;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ZipCodesTest extends BaseTest {
    @Epic(value = "User")
    @Feature(value = "ZipCodes")
    @Story(value = "Get Available")
    @Description(value = "Test checks Available ZipCodes")
    @Test
    public void checkAvailableZipCodes() {
        List<String> zipCodes = ApplicationClient.getZipCodes();
        Assertions.assertNotNull(zipCodes, "Zip codes are not available");
    }

    @Epic(value = "User")
    @Feature(value = "ZipCodes")
    @Story(value = "Add")
    @Description(value = "Test checks Add ZipCodes")
    @Test
    public void checkAddZipCodes() {
        String zipCodes = ApplicationClient.getBodyZipCodes();
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(zipCodes);
        List<String> afterExpandZipCodes = ApplicationClient.getZipCodes();
        Assertions.assertTrue(afterExpandZipCodes.containsAll(expandZipCodes), "Zip codes not added");
    }

    @DisplayName("Test failed, Zip codes are duplicated")
    @Epic(value = "User")
    @Feature(value = "ZipCodes")
    @Story(value = "Duplicate")
    @Description(value = "Test checks Duplicate ZipCodes")
    @Test
    public void checkDuplicateZipCodes() {
        String allZipCodes = ApplicationClient.getBodyZipCodes();
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(allZipCodes);
        List<String> afterExpandZipCodes = ApplicationClient.getZipCodes();
        Set<String> set = new LinkedHashSet<>(afterExpandZipCodes);
        Assertions.assertEquals(set.size(), expandZipCodes.size(), "Zip codes are duplicated");
    }

    @DisplayName("Test failed, Zip codes are duplicated")
    @Epic(value = "User")
    @Feature(value = "ZipCodes")
    @Story(value = "Duplicate")
    @Description(value = "Test checks Duplicate Available And Used ZipCodes")
    @Test
    public void checkDuplicateAvailableAndUsedZipCodes() {
        String allZipCodes = ApplicationClient.getBodyZipCodes();
        List<String> expandZipCodes = ApplicationClient.expandZipCodes(allZipCodes);
        List<String> afterExpandZipCodes = ApplicationClient.getZipCodes();
        Assertions.assertFalse(expandZipCodes.containsAll(afterExpandZipCodes), "Zip codes are duplicated");
    }
}
