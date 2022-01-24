package Sprint1;// Nota: Esta clase deber�a compilar
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.*;

public class WriteFile {

    private File path;
    private boolean append_to_file = false;

    public WriteFile(File file_path) {
        path = file_path;
    }

    public WriteFile(File file_path, boolean append_value) {
        path = file_path;
        append_to_file = append_value;
    }

    public void writeToFile(String textLine) throws IOException{


        FileWriter write = new FileWriter(path, append_to_file);

        PrintWriter print_line = new PrintWriter(write);

        print_line.println(textLine);
//        print_line.printf("%s" + "%n", textLine);

        print_line.close();

    }


}

