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

    @CommandLine.Parameters(index="0", description = "The file whose lines, words and characters are to be counted.")
    private String filePath;

    @Override
    public Integer call() throws Exception {
        if (countBytes) {
            var path = Paths.get(filePath);
            var bytes = Files.readAllBytes(path);
            System.out.println(bytes.length + " " + path.getFileName().toString());
        }

        return 0;
    }
}
