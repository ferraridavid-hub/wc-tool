package org.example;

import picocli.CommandLine;

import javax.xml.stream.events.Characters;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "ccwc", mixinStandardHelpOptions = true, version = "ccwc 1.0",
        description = "Counts the number of lines, words and characters")
public class WcTool implements Callable<Integer> {

    @CommandLine.Option(names = {"-c", "--count"}, description = "Count bytes in the file")
    private boolean countBytes;

    @CommandLine.Option(names = {"-l", "--line"}, description = "Count lines in the file")
    private boolean countLines;

    @CommandLine.Option(names = {"-w", "--word"}, description = "Count words in the file")
    private boolean countWords;

    @CommandLine.Option(names = {"-m", "--chars"}, description = "Count characters in the file")
    private boolean countChars;

    @CommandLine.Parameters(index = "0", description = "The file whose lines, words and characters are to be counted.")
    private String filePath;

    @Override
    public Integer call() throws Exception {

        if(!countChars && !countLines && !countWords && !countBytes) {
            countBytes = countLines = countWords = true;
        }

        StringBuilder outputBuilder = new StringBuilder();
        var path = Paths.get(filePath);
        if (countBytes) {
            var bytes = Files.readAllBytes(path);
            outputBuilder.append("\t");
            outputBuilder.append(bytes.length);
        }

        if (countLines) {
            var lines = Files.readAllLines(path);
            outputBuilder.append("\t");
            outputBuilder.append(lines.size());
        }

        if (countWords) {
            var lines = Files.readAllLines(path);
            var words = 0;
            for (var line : lines) {
                if (!line.trim().isEmpty()) {
                    words += line.trim().split("\\s+").length;
                }
            }
            outputBuilder.append("\t");
            outputBuilder.append(words);
        }

        if (countChars) {
            int chars = 0;
            try (
                    BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path));
                    InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr)
            ) {
                int codeUnit;
                while ((codeUnit = br.read()) != -1) {
                    chars++;
                }
            }

        outputBuilder.append("\t");
        outputBuilder.append(chars);
    }

        outputBuilder.append("\t");
        outputBuilder.append(path.getFileName().

    toString());
        System.out.println(outputBuilder.toString());

        return 0;
}
}
