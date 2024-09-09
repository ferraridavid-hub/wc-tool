package org.example;

import picocli.CommandLine;

import javax.xml.stream.events.Characters;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @CommandLine.Parameters(index = "0", description = "The file whose lines, words and characters are to be counted.", arity = "0..1")
    private String filePath = null;

    @Override
    public Integer call() throws Exception {

        if (!countChars && !countLines && !countWords && !countBytes) {
            countBytes = countLines = countWords = true;
        }

        StringBuilder outputBuilder = new StringBuilder();

        Counter counter;
        Path path = null;
        if (filePath == null) {
            counter = new Counter(System.in);
        } else {
            path = Paths.get(filePath);
            counter = new Counter(Files.newInputStream(path));
        }

        if (countBytes) {
            var bytes = counter.getBytesCount();
            outputBuilder.append("\t");
            outputBuilder.append(bytes);
        }

        if (countLines) {
            var lines = counter.getLinesCount();
            outputBuilder.append("\t");
            outputBuilder.append(lines);
        }

        if (countWords) {
            var words = counter.getWordsCount();
            outputBuilder.append("\t");
            outputBuilder.append(words);
        }

        if (countChars) {
            var chars = counter.getCharsCount();
            outputBuilder.append("\t");
            outputBuilder.append(chars);
        }

        outputBuilder.append("\t");
        if (path != null) {
            outputBuilder.append(path.getFileName().toString());
        }
        System.out.println(outputBuilder);

        return 0;
    }
}
