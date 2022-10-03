package it.bip.fabrick.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;

import static com.google.common.io.Resources.getResource;

public class TestJsonDocumentLoader {

    public static String loadTestJson(String fileName, Class clazz) {
        URL url = getResource(clazz, fileName);
        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file.", e);
        }
    }

}