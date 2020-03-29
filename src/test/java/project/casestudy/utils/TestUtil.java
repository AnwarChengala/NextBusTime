package project.casestudy.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TestUtil {

    public static String getFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
        if (resourceAsStream == null) {
            throw new FileNotFoundException("Unable to load file: " + fileName);
        }
        return IOUtils.toString(resourceAsStream);
    }

}
