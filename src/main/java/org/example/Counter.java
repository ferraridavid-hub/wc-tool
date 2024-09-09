package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Counter {

    private int bytesCount;
    private int charsCount;
    private int linesCount;
    private int wordsCount;

    public Counter (InputStream sourceInputStream) throws IOException {
        linesCount = 0;
        charsCount = 0;
        bytesCount = 0;
        wordsCount = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(sourceInputStream, StandardCharsets.UTF_8));
        String line;
        while((line = reader.readLine()) != null) {
            linesCount ++;
            if (!line.trim().isEmpty()) {
                wordsCount += line.trim().split("\\s+").length;
            }
            bytesCount += line.getBytes().length + 1;
            charsCount += line.codePointCount(0, line.length()) + 1;
        }
    }

    public int getBytesCount() {
        return bytesCount;
    }

    public int getCharsCount() {
        return charsCount;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public int getWordsCount() {
        return wordsCount;
    }

}
