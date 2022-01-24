package Sprint1;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class BinToHex {


    static ReadFromFile2 reader = new ReadFromFile2("src/binaryExample");
    static int counter = 0;
    static String[] memory = new String[4096];

    public static void main(String[] args) {

        while(reader.hasNext()) {
            String sentence = reader.getNext();
            String temp = sentence;
            while(temp.contains(" ")) {
                temp = temp.replaceFirst(" ", "");
            }
            String s1="", s2="", s3="", s4="";
            s1 = sentence.substring(0, 4);
            s2 = sentence.substring(4, 8);
            s3 = sentence.substring(8, 12);
            s4 = sentence.substring(12, 16);

            System.out.println(" s1 = "+s1+", s2 = "+s2+", s3 = "+s3+", s4 = "+s4);
            String hex = binToHex(s1) + binToHex(s2) + binToHex(s3) + binToHex(s4);
            memory[counter] = hex;
            counter++;
        }


        for(int i = 0; i < 9; i++) {
            System.out.println(memory[i]);
        }
    }

    public static String binToHex(String s) {
        switch(s) {
            case "0000":
                return "0";
            case "0001":
                return "1";
            case "0010":
                return "2";
            case "0011":
                return "3";
            case "0100":
                return "4";
            case "0101":
                return "5";
            case "0110":
                return "6";
            case "0111":
                return "7";
            case "1000":
                return "8";
            case "1001":
                return "9";
            case "1010":
                return "A";
            case "1011":
                return "B";
            case "1100":
                return "C";
            case "1101":
                return "D";
            case "1110":
                return "E";
            default:
                return "F";
        }
    }
}




