package com.lukec.currency.util;

import static com.lukec.currency.util.Constants.CONTROLLER_TEST_URL_PATTERN;
import static com.lukec.currency.util.Constants.LOCAL_TEST_URL_PATTERN;

public class TestUrlUtil {
	
	private TestUrlUtil() {}

    public static String getControllerTestUrl(String urlServicePart) {
        return String.format(CONTROLLER_TEST_URL_PATTERN, urlServicePart);
    }

    public static String getIntegrationTestUrl(String urlServicePart, int port) {
        return String.format(LOCAL_TEST_URL_PATTERN, port, urlServicePart);
    }

}
