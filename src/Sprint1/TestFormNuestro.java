package Sprint1;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic.Kind;
public class TestFormNuestro {

    static int lenght;
    static int c1;
    static int begin=0;
    static public ArrayList<Integer> errores;
    static LexerGrupo3 parser = null;
    static ReadFromFile2 reader; //= new ReadFromFile2("example.asm");
    static int counter = 1;
    static boolean isValid = true;
    static ArrayList<ReferenceTable> table = new ArrayList<>();
    static ArrayList<String> program = new ArrayList<>();

    static String [] memory = new String[4096];//<<<<<<<<
    static int pc = 0;
    static Subs claseSubs=new Subs();
    static File f;

    static ProcessingEnvironment env;

    public TestFormNuestro(){

    }

    public TestFormNuestro(File file){
        reader = new ReadFromFile2(file.getPath());
        System.out.println(file.getPath());
        memory = new String[4096];
        pc = 0;
        table = new ArrayList<>();
        program = new ArrayList<>();
        counter = 1;
        isValid = true;
        errores = new ArrayList<>();
    }
    //+++++++++++++++++++++++++++++++++++++MAIN ALL+++++++++++++++++++++++++++++++++++++++++++=======
//    public static void main(String[] args) {
        public void Run(){

        for(int j=0;j<4096;j++)//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        {memory[j] = "00";}//rellena espacios memoriac con 00s
        while(reader.hasNext()) {
            System.out.println("Line : "+counter++);
            String sentence = reader.getNext();
            sentence = sentence.toUpperCase();
            String temp = sentence;
            while(temp.contains(" ")) {
                temp = temp.replaceFirst(" ", "");
            }
            if(!temp.isEmpty()) {
                program.add(sentence);
                InputStream is = new ByteArrayInputStream(sentence.getBytes());
                if(parser == null) parser = new LexerGrupo3(is);
                else LexerGrupo3.ReInit(is);
                try
                {
                    switch (LexerGrupo3.start())
                    {
                        case 0 :
                            System.out.println("expression parsed ok.");
                            break;
                        default :
                            break;
                    }
                }
                catch (Exception e)
                {
                    isValid = false;
                    System.out.println("error in expression.\n"+
                            e.getMessage());
                    errores.add(counter-1);
                }
                catch (Error e)
                {
                    isValid = false;
                    System.out.println("error in expression.\n"+
                            e.getMessage());
                    errores.add(counter-1);
                }
                finally
                {

                }
            }
        }

        if(isValid) { //SI EL LEXER DETERMINO QUE LA SINTAXIS ES CORRECTA
            //PARA HACER EL REFERENCE TABLE*************************************************************************
            int pcTemp = pc; //Se crea un counter temporero para saber donde iria whatever...
            Iterator<String> itr = program.iterator();
            while(itr.hasNext()) {
                String temp = itr.next();

                String hex = Integer.toHexString(pcTemp);
                if(temp.contains("//")) {
                    temp = temp.substring(0, temp.indexOf("/"))+" ";//arregla line
                }
                if(temp.charAt(0) != ' ') { // ********************label, variable o db

                    if(temp.contains(" DB ")){
                        table.add(new ReferenceTable(temp.substring(0, temp.indexOf(" ")), "DB", hex," "));
                        int countCommas=0;
                        for(int i=0; i<temp.length();i++) {
                            if(temp.charAt(i)==','){
                                countCommas++;
                            }//CONTINUAR LUEGO
                        }
                        pcTemp=pcTemp+countCommas;//a�ade espacios para Db
                        //detectar cantidad.numeros

                        //System.out.println(temp);
                    }
                    else if(temp.contains(":"))
                    {
                        if(pcTemp %2 != 0) {
                            pcTemp++;
                        }
                        table.add(new ReferenceTable(temp.substring(0, temp.indexOf(":")), "LABEL", Integer.toHexString(pcTemp)," "));
                        pcTemp=pcTemp-2;;
                        //System.out.println(temp);
                        //System.out.println(pcTemp);
                    }
                    else
                    if(temp.contains("CONST")) {
                        int index = temp.indexOf(" ") +1;
                        temp = temp.substring(index);
                        table.add(new ReferenceTable((temp.substring(0, temp.indexOf(" "))), "CONST", "N/A",temp.substring(temp.indexOf(" ")+1)));
                        //System.out.println((temp.substring(0, temp.indexOf(" "))));
                        pcTemp--;
                    }
                }
                else {// Es instruccion o ORG o espacio de comentario
                    if(temp.charAt(0)==' '& temp.length()==1) {
                        //Era espacio de coment,lo omite
                        pcTemp--;

                    }
                    //Anadir space case between
                    else {
                        if(temp.contains(" ORG ")) {
                            temp = temp.substring(temp.indexOf("G")+1);
                            while(temp.contains(" ")) {
                                temp = temp.replaceFirst(" ", "");
                            }
                            //System.out.println(temp);

                            pcTemp = Integer.parseInt(temp, 16);
                            pcTemp--;


                        }
                        else {//instruccion
                            if(pcTemp%2!=0) {
                                pcTemp++;
                            }

                            //atiende Inst(no ref.Table)
                            pcTemp++;}
                    }
                }pcTemp++;
                //	System.out.println(pcTemp);
            }
            //******************************************************************************************************************************
            //								SE LLENO EL REFERENCE TABLE

            //PARA HACER EL PROCESAMIENTO*************************************************************************
            pcTemp = pc; //Donde empieza el ORG
            itr = program.iterator();

            while(itr.hasNext()) { //este while navega todas las instrucciones linea por linea (BARRA POR BARRA)
                String temp = itr.next();
                if(begin ==0) {
                    temp = temp.substring(2);//elimina ?
                    //	System.out.println("temp= "+ temp);
                    begin++;
                }
                if(temp.contains("//")) {

                    //System.out.println("temp1= "+ temp);
                    temp = temp.substring(0, temp.indexOf("/"))+" ";//arregla line
                    lenght=temp.length();
                    //System.out.println("temp2= "+ temp);
                }



                if(temp.charAt(0)!=' ')  // ********************label, variable(const)  o db
                {

                    if(temp.contains(" DB ")) {
                        //2 casos: 1 #,|| mas de 1
                        String numPart= claseSubs.takeAtferDBPart(claseSubs.trimDBLine(temp));//da string pegado
                        //hasta FF
                        if(numPart.contains(","))//tiene mas de 1 byte
                        {
                            int i=0;int countCommas=0;
                            ArrayList<String> bytes= new ArrayList<>();

                            while(numPart.length()>i) {
                                if(numPart.charAt(i)==','){
                                    //coge pedazo de DB y lo guarda en arra
                                    bytes.add(numPart.substring(0,i));
                                    numPart=numPart.substring(i+1);
                                    //chequea array y trns. de hex a binary,luego a hex y acomoda en Mem
                                    String binarybyte =claseSubs.TransfromDBtoByte(bytes.get(countCommas));
                                    String b0_3=binarybyte.substring(0,4);
                                    String b4_7=binarybyte.substring(4);
                                    memory[pcTemp +countCommas]=claseSubs.binToHex(b0_3) +claseSubs.binToHex(b4_7);//guarda 1 byte en mem
                                    countCommas++;

                                    i=0;
                                }
                                else {i++;}

                            }
                            bytes.add(numPart);//aÒade ultimo byte
                            String binarybyte =claseSubs.TransfromDBtoByte(bytes.get(countCommas));
                            String b0_3=binarybyte.substring(0,4);
                            String b4_7=binarybyte.substring(4);
                            memory[pcTemp +countCommas]=claseSubs.binToHex(b0_3) +claseSubs.binToHex(b4_7);//guarda 1 byte en mem
                            pcTemp=pcTemp+countCommas-1;//
                        }
                        else {
                            String binarybyte =claseSubs.TransfromDBtoByte(numPart);
                            String b0_3=binarybyte.substring(0,4);
                            String b4_7=binarybyte.substring(4);
                            memory[pcTemp ]=claseSubs.binToHex(b0_3) +claseSubs.binToHex(b4_7);//guarda 1 byte en mem
                            pcTemp --;
                        }

                    }

                    else {//CONST or label(:)
                        //Do nothing...//No se guarda en memoria
                        pcTemp =pcTemp -2;
                    }
                }//End if the label, db or variable

                else {// Es instruccion o ORG o return
                    c1=0;
                    for(int b=0;b<temp.length();b++) {

                        if(temp.charAt(b)==' ') {
                            c1++;
                        }
                    }

                    if(temp.contains(" ORG ")) {
                        temp = temp.substring(temp.indexOf("G")+1);
                        while(temp.contains(" ")) {
                            temp = temp.replaceFirst(" ", "");
                        }

                        pcTemp = Integer.parseInt(temp, 16);//String hexadecimal a entero
                        //Check if is rewrite***ERROR
                        pcTemp =pcTemp -2;
                        if(!memory[pcTemp+2].equals("00")) {
                            System.out.println("Overwriting on memory adress[ "+(pcTemp+2)+ "] with   "+memory[pcTemp+2]);
                            System.out.println();
                        }

                    }

                    else if(temp.length()==c1) {
                        //Do nothing,comment
                        pcTemp =pcTemp -2;
                    }
                    else {//IntrucciÛn
                        if(pcTemp %2!=0) {
                            memory[pcTemp]="00";//rellena memoria basura impar
                            pcTemp ++;
                        }
                        System.out.println("temp: "+temp);//imprimo la linea de inst en asm

                        String[] array=claseSubs.makeTokens(claseSubs.trimLine(temp));//devuelve 4 tokens de la linea en asm
                        String sFix=claseSubs.TransformToFormatBits(array[0],array[1],array[2] ,array[3],table );//a bit format
                        //System.out.println(	"bitFormat: "+sFix);
                        String s1=sFix.substring(0,4);
                        String s2=sFix.substring(4,8);
                        String s3=sFix.substring(8,12);
                        String s4=sFix.substring(12,16);

                        String index0A =claseSubs.binToHex(s1)+claseSubs.binToHex(s2);
                        String index1A=claseSubs.binToHex(s3)+claseSubs.binToHex(s4);
                        System.out.println(index0A+" "+index1A );
                        memory[pcTemp ] = index0A;
                        memory[pcTemp +1] = index1A;

                    }

                }
                pcTemp =pcTemp +2;// actualiza

            }//++++++++++++++++end  While(int.hasNext()) +++++++++++++++++++++++++

            int beginMemStore=0;

            try {

                // Get the file
                f = new File("./run.obj");
//                File f = new File("/Users/joshuabonilla/Documents/GitHub/ICOM4009GRUPO3/Sprint2_ICOM4009/src/test1.obj");
//                URL url = getClass().getResource("test1.obj");
//                f = new File(url.getPath());

                // Create new file
                // if it does not exist
                if (f.exists())
                    f.delete();
                if (f.createNewFile())
                {System.out.println("File created");}
                else
                {System.out.println("File already exists");}


                WriteFile data = new WriteFile(f, true);
                while( beginMemStore!= 1020)
                {
                    data.writeToFile(memory[beginMemStore]+memory[beginMemStore+1]);
                    beginMemStore=beginMemStore+2;
                }
//                data.writeToFile("END");

            }
            catch (Exception e) {
                System.err.println(e);
            }

        }// end if(isValid)
        else //EL LEXER DICE QUE NO SE PUDO COMPILAR!!!!
            System.out.println("There is an error in the code. Can't be compiled");

    }
}

