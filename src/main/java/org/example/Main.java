package org.example;

import picocli.CommandLine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new WcTool()).execute(args);
        System.exit(exitCode);
    }
}