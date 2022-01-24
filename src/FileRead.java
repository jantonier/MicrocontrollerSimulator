import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class FileRead {

    static ReadFromFile2 reader;
    static ArrayList<String> text = new ArrayList<String>();
    static String FText = "Direccion\tCantidad\n";

//    public static ArrayList<String> getText() {
//        int index = 0;
//        while (reader.hasNext()){
//            text.add(reader.getNext());
//            System.out.println(text.get(index++));
//
//        }
//
//        return text;
//    }

    public static String getFText(File file){
        int index = 0;
        reader = new ReadFromFile2(file.getPath());

        while (reader.hasNext()) {
            String a = reader.getNext();
            String b = a.substring(0,2);
            String c = a.substring(3);
//            System.out.println(a);
//            System.out.println(b);
//            System.out.println(c);
            // FText = FText + index++ + " " + b + "\n" ;
//            FText = FText + "|_____________________|\n";
            FText = FText + String.format("      %02d", index++) + "\t     " + b + "\n" ;
//            FText = FText + "|_____________________|\n";
            FText = FText + String.format("      %02d", index++) + "\t     " + c + "\n" ;

        }
        return FText;
    }

    public static String[] getArrayText(){
        String Text [] = new String[4096];
        for (int i=0; i<Text.length; i++)
            Text[i] = "00";
        int count = 0;

        while (reader.hasNext()){
            String a = reader.getNext();
            Text[count++] = a.substring(0,1);
            Text[count++] = a.substring(2);
        }


        return Text;
    }

    public static String[] getArrayText(File file){
//        URL url = FileRead.class.getResource("test1.obj");
//        file = new File(url.getPath());
        reader = new ReadFromFile2(file.getPath());
        String Text [] = new String[2048];
        for (int i=0; i<Text.length; i++)
            Text[i] = "0000";
        int count = 0;

        while (reader.hasNext()){
            String a = reader.getNext();
            Text[count++] =  a;
//            Text[count++] = a.substring(0,2);
//            Text[count++] = a.substring(2);
        }


        return Text;
    }

    public FileRead() {
    }

    public String getText(File file){
        String result = "";
        reader = new ReadFromFile2(file.getPath());
        while (reader.hasNext()) {
            result = result + reader.getNext();
            result = result + "\n";
        }


        return result;
    }

//    public FileRead(File file){
//        reader = new ReadFromFile2(file.getPath());
//    }
}
