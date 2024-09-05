package org.example;

import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(name="ccwc", mixinStandardHelpOptions = true, version = "ccwc 1.0",
description = "Counts the number of lines, words and characters")
public class WcTool implements Callable<Integer> {

    @CommandLine.Option(names={"-c", "--count"}, description = "Count bytes in the file")
    private boolean countBytes;

    @CommandLine.Option(names={"-l", "--line"}, description = "Count lines in the file")
    private boolean countLines;

    @CommandLine.Option(names={"-w", "--word"}, description = "Count words in the file")
    private boolean countWords;

    @CommandLine.Parameters(index="0", description = "The file whose lines, words and characters are to be counted.")
    private String filePath;

    @Override
    public Integer call() throws Exception {
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

        outputBuilder.append("\t");
        outputBuilder.append(path.getFileName().toString());
        System.out.println(outputBuilder.toString());

        return 0;
    }
}
