package uk.ac.hope.csc.greeno.quizparser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    // Full path (or relative path) to the input file
    protected String file;
    protected List<String> fileLines;

    public static void main(String[] args) {

        FileReader reader = new FileReader("./textfile.txt");

    }

    public FileReader(String file) {
        this.file = file;
    }

    public List<String> readFile() {

        try {
            Path path = Paths.get(file);
            fileLines = Files.readAllLines(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileLines;
    }

}
