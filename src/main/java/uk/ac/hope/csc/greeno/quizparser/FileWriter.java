package uk.ac.hope.csc.greeno.quizparser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileWriter {

    // Full path (or relative path) to the input file
    protected String file;

    public FileWriter(String file) {
        this.file = file;
    }

    public boolean writeFile(String xml) {
        boolean ret = false;
        try {
            Path path = Paths.get(file);
            Files.write(path, xml.getBytes());
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean writeBufferedFile(String xml) {
        boolean ret = false;
        try{
            Path path = Paths.get(file);
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
            writer.write(xml);
            ret = true;
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
