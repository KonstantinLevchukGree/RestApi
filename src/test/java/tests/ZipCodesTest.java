package tests;

import endPoint.Point;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.api.ApiUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ZipCodesTest extends BaseTest {
    @Test
    public void checkAvailableZipCodes() {
        //must be 200. Bug
        String json = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_OK);
        Assertions.assertNotNull(json, "Zip codes are not available");
    }

    @Test
    public void checkAddZipCodes() {
        //must be 200. Bug
        String allZipCodes = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_OK);
        String expandZipCodes = ApiUtils.sendPost(writerToken, Point.ZIP_CODES.getApiPoint() + Point.EXPAND_ZIP_CODES.getApiPoint(), allZipCodes, HttpStatus.SC_CREATED);
        String afterExpandAllZipCodes = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        List<String> afterExpandAllZipCodesList = ApiUtils.getListFromJson(afterExpandAllZipCodes);
        List<String> expandZipCodesList = ApiUtils.getListFromJson(expandZipCodes);
        Assertions.assertEquals(afterExpandAllZipCodesList, expandZipCodesList, "Zip codes not added");
    }

    @Test
    public void checkExpandZipCodes() {
        String allZipCodes = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        String expandZipCodes = ApiUtils.sendPost(writerToken, Point.ZIP_CODES.getApiPoint() + Point.EXPAND_ZIP_CODES.getApiPoint(), allZipCodes, HttpStatus.SC_CREATED);
        String allZipCodesAfterExpand = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        List<String> allZipCodesList = ApiUtils.getListFromJson(allZipCodesAfterExpand);
        List<String> expandZipCodesList = ApiUtils.getListFromJson(expandZipCodes);
        Set<String> set = new LinkedHashSet<>(allZipCodesList);
        //Zip code are duplicated. Bug
        Assertions.assertEquals(set.size(), expandZipCodesList.size(), "Zip codes are duplicated");
    }

    @Test
    public void checkDuplicateZipCodes() {
        String allZipCodes = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        String expandZipCodes = ApiUtils.sendPost(writerToken, Point.ZIP_CODES.getApiPoint() + Point.EXPAND_ZIP_CODES.getApiPoint(), allZipCodes, HttpStatus.SC_CREATED);
        String allZipCodesAfterExpand = ApiUtils.sendGet(readerToken, Point.ZIP_CODES.getApiPoint(), HttpStatus.SC_CREATED);
        List<String> allZipCodesList = ApiUtils.getListFromJson(allZipCodesAfterExpand);
        List<String> expandZipCodesList = ApiUtils.getListFromJson(expandZipCodes);
        //Zip code are duplicated. Bug
        Assertions.assertFalse(expandZipCodesList.containsAll(allZipCodesList), "Zip codes are duplicated");
    }
}
