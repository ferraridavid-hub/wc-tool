package org.example;

import picocli.CommandLine;

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

    private final StringBuilder outputBuilder = new StringBuilder();

    @Override
    public Integer call() throws Exception {

        if (!countChars && !countLines && !countWords && !countBytes) {
            countBytes = countLines = countWords = true;
        }

        Counter counter;
        Path path = null;
        if (filePath == null) {
            counter = new Counter(System.in);
        } else {
            path = Paths.get(filePath);
            counter = new Counter(Files.newInputStream(path));
        }

        if (countBytes) {
            addItemToOutput(counter.getBytesCount());
        }

        if (countLines) {
            addItemToOutput(counter.getLinesCount());
        }

        if (countWords) {
            addItemToOutput(counter.getWordsCount());
        }

        if (countChars) {
            addItemToOutput(counter.getCharsCount());
        }

        if (path != null) {
            addItemToOutput(path.getFileName().toString());
        }

        System.out.println(outputBuilder);

        return 0;
    }

    private void addItemToOutput(int item) {
        outputBuilder.append("\t");
        outputBuilder.append(item);
    }

    private void addItemToOutput(String item) {
        outputBuilder.append("\t");
        outputBuilder.append(item);
    }
}
