package Sprint1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
public class ReadFromFile2
{
    String st;
    List<String> lines = Collections.emptyList();
    Iterator<String> itr;

    public ReadFromFile2(String filename) {
        try
        {
            lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        itr = lines.iterator();
    }
    public boolean hasNext() {
        return itr.hasNext();
    }

    public String getNext() {
        return itr.next();
    }
}
