import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static String [] memory = new String[4096];//<<<<<<<<
	final static String r0 = "00"; //R0 es 0 en todo momento. Su valor no cambia
	public static String [] register; //= {r0,"23","31","F1","82","88","83","FF"};
	public static String instructionRegister = "";
	public static String stackPointer;//HAY QUE INICIALIZAR EL SP
	public static int pc = 0;
	public static ReadFromFile2 reader; //= new ReadFromFile2("src/testing.txt");
	public static String stringToDisplay;
	public static String t1, t2, t3, t4, temp;
	public static String cond = "0";
	public static ArrayList<Snapshot> timeLine = new ArrayList<>();

	public Main() {
		//FOR TESTING PURPOSE (JUnit)
		register = new String[]{r0, "23", "31", "F1", "82", "04", "03", "FF"};
		for(int i = 0; i < 40; i++){
			memory[i] = Integer.toHexString(i).toUpperCase();
			while(memory[i].length()<2)
				memory[i] = "0"+memory[i];
		}
	}

	public Main(File file) {
		timeLine = new ArrayList<>();
//		URL url = getClass().getResource("test1.obj");
//		file = new File(url.getPath());
		reader = new ReadFromFile2(file.getPath());
		register = new String[]{r0, "00", "00", "00", "00", "00", "00", "FF"};
		pc = 0;
	}



	//public ArrayList<Snapshot> run(){
	public boolean initialize() {
		for (int i = 0; i < memory.length; i++)
			memory[i] = "00";

		// TODO Auto-generated method stub
		int i = 0;
		String tempo;
		while (reader.hasNext()) {                //CARGO EL FILE A MEMORIA (LINEA POR LINEA) -> BARRA POR BARRA
			tempo = reader.getNext();
			if(tempo.length() != 4)
				return false;
			memory[i++] = tempo.substring(0, 2);
			memory[i++] = tempo.substring(2);
		}

		for (int j = 0; j < i; j++)                //LE ELIMINO LOS ESPACIOS EN BLANCO A CADA LINEA DE MEMORIA
			while (memory[j].contains(" "))
				memory[j] = memory[j].replaceFirst(" ", "");

		System.out.println("STACK POINTER INITIALIZED TO: 255 (FF in Hex)");
		int y = 0;
		while (pc == 0) {
			if (!(memory[y] + memory[y + 1]).equals("0000")) {
				pc = y;
				break;
			}
			y = y + 2;
		}
		return true;
	}
	//for(int j = 0; j < i; j=j+2) {		//ESTE FOR VA A RECORRER NUESTRO PROGRAMA POR COMPLETO

//		boolean isEnded = false;
//		int counterr = 0;
	//while (!isEnded) {
//			if(instructionRegister.equals(memory[pc]+memory[pc+1])){
//				counterr++;
//				if(counterr >= 20){
//					isEnded = true;
//				}
//			}
	//else
	//	counterr = 0;

	public Snapshot run(){

		instructionRegister = memory[pc] + memory[pc+1];
		//if(!instructionRegister.equals("0000")) { //IGNORA LAS LINEAS QUE NO TIENEN CONTENIDO (LAS QUE SON "0000")
			temp = "";
			stringToDisplay = Integer.toHexString(pc).toUpperCase();
			while(stringToDisplay.length()<3)
				stringToDisplay = "0"+ stringToDisplay;

			stringToDisplay =  stringToDisplay	+": "+instructionRegister+": ";
			//*********************************************//SE PARTICIONA Y SE TRADUCE CADA VALOR HEX A BINARIO

			t1 = hexTo4Bit(instructionRegister.substring(0, 1));
			t2 = hexTo4Bit(instructionRegister.substring(1, 2));
			t3 = hexTo4Bit(instructionRegister.substring(2, 3));
			t4 = hexTo4Bit(instructionRegister.substring(3, 4));
			temp = t1 + t2 + t3 + t4;		//ESTA ES LA INSTRUCCION COMPLETAMENTE CONVERTIDA A BINARIO

			t1 = temp.substring(0, 5);  //AHORA t1 CONTIENE EL OPCODE DE LA INSTRUCCION
			t1 = InstructionBit(t1);   //AQUI SE TRADUCE t1 DE BINARIO A LA INSTRUCCION CORRESPONDIENTE AL OPCODE EN PALABRAS

			switch(t1) {

				case "LOAD":
					System.out.println("LOAD");
					//System.out.println(temp.substring(8,16));
					load(temp.substring(5, 8), temp.substring(8,16));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1F2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "LOADIM":
					System.out.println("LOADIM");
					loadim(temp.substring(5, 8), temp.substring(8,16));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2F2();
					//***********************************************************************************************
					break;

				case "POP":
					System.out.println("POP");
					pop(temp.substring(5, 8));
					break;

				case "STORE":
					System.out.println("STORE");
					store(temp.substring(5, 8), temp.substring(8,16));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1F2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "PUSH":
					System.out.println("PUSH");
					push(temp.substring(5, 8));
					break;

				case "LOADRIND":
					System.out.println("LOADRIND");
					loadrind(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "STORERIND":
					System.out.println("STORERIND");
					storerind(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "ADD":
					System.out.println("ADD");
					add(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "SUB":
					System.out.println("SUB");
					sub(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "ADDIM":
					System.out.println("ADDIM");
					addim(temp.substring(5, 8), temp.substring(8,16));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2F2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "SUBIM":
					System.out.println("SUBIM");
					subim(temp.substring(5, 8), temp.substring(8,16));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2F2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "AND":
					System.out.println("AND");
					and(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "OR":
					System.out.println("OR");
					or(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "XOR":
					System.out.println("XOR");
					xor(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "NOT":
					System.out.println("NOT");
					not(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "NEG":
					System.out.println("NEG");
					neg(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "SHIFTR"://Jose
					System.out.println("SHIFTR");
					shiftr(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "SHIFTL"://Jose
					System.out.println("SHIFTL");
					shiftl(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "ROTAR"://Jose
					System.out.println("ROTAR");
					rotar(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "ROTAL"://Jose
					System.out.println("ROTAL");
					rotal(temp.substring(5, 8), temp.substring(8, 11), temp.substring(11, 14));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "JMPRIND":
					System.out.println("JMPRIND");
					jmprind(temp.substring(5, 8));
					break;

				case "JMPADDR":
					System.out.println("JMPADDR");
					jmpaddr("0" + temp.substring(5));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1F3();
					//***********************************************************************************************
					break;

				case "JCONDRIN":
					System.out.println("JCONDRIN");
					jcondrin(temp.substring(5, 8));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay2F3();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "JCONDADDR":
					System.out.println("JCONDADDR");
					jcondaddr("0" + temp.substring(5));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1F3();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "LOOP": //Jose
					System.out.println("LOOP");
					loop(temp.substring(5, 8), temp.substring(8));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1F2();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "GRT":
					System.out.println("GRT");
					grt(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "GRTEQ":
					System.out.println("GRTEQ");
					grteq(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "EQ":
					System.out.println("EQ");
					eq(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "NEQ":
					System.out.println("NEQ");
					neq(temp.substring(5, 8), temp.substring(8, 11));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1();
					//***********************************************************************************************
					break;

				case "NOP":
					System.out.println("NOP");
					nop();
					break;

				case "CALL"://Jose
					System.out.println("CALL");
					call(temp.substring(4,16));
					//*************************IMPRIMIR EN PANTALLA LA INSTRUCCION***********************************
					updateStringToDisplay1F3();
					//***********************************************************************************************
					System.out.println(stringToDisplay);
					break;

				case "RETURN":
					System.out.println("RETURN");
					ireturn();
					break;

				default:
					break;
			}
		//}
		//timeLine.add(new Snapshot(memory, register, instructionRegister, stackPointer,  pc,  stringToDisplay,  cond));
		return new Snapshot(memory, register, instructionRegister, stackPointer,  pc,  stringToDisplay,  cond);

		//}
		//return null;
		//return timeLine;
	}



	//*****************************************************************************************************************

	public void toMemory(int index, String value){
		memory[index] = value;
	}

	public String[] getMemory(){
		return memory;
	}

	public static String binToHex(String s) {
		Map<String,String> map1= new HashMap<String,String>();
		map1.put("0000","0");map1.put("0001","1");map1.put("0010","2");map1.put("0011","3");
		map1.put("0100","4");map1.put("0101","5");map1.put("0110","6");map1.put("0111","7");
		map1.put("1000","8");map1.put("1001","9");map1.put("1010","A");map1.put("1011","B");
		map1.put("1100","C");map1.put("1101","D");map1.put("1110","E");map1.put("1111","F");
		return map1.get(s);
	}

	public static String hexTo4Bit(String s) {
		Map<String,String> map1= new HashMap<String,String>();
		map1.put("0","0000");map1.put("1","0001");map1.put("2","0010");map1.put("3","0011");
		map1.put("4","0100");map1.put("5","0101");map1.put("6","0110");map1.put("7","0111");
		map1.put("8","1000");map1.put("9","1001");map1.put("A","1010");map1.put("B","1011");
		map1.put("C","1100");map1.put("D","1101");map1.put("E","1110");map1.put("F","1111");
		return map1.get(s);
	}


	static String InstructionBit(String T1) {
		Map<String,String> map= new HashMap<String,String>();
		map.put("00000", "LOAD");map.put("00001","LOADIM");map.put("00010","POP");map.put("00011","STORE");
		map.put("00100", "PUSH");map.put("00101", "LOADRIND");map.put("00110", "STORERIND");map.put("00111", "ADD");
		map.put("01000", "SUB");	map.put("01001", "ADDIM");map.put("01010", "SUBIM");map.put("01011", "AND");
		map.put("01100", "OR");map.put("01101", "XOR");map.put("01110", "NOT");map.put("01111", "NEG");
		map.put("10000", "SHIFTR");map.put("10001", "SHIFTL");map.put("10010", "ROTAR");map.put("10011", "ROTAL");
		map.put("10100", "JMPRIND");map.put("10101", "JMPADDR");map.put("10110", "JCONDRIN");map.put("10111", "JCONDADDR");
		map.put("11000", "LOOP");map.put("11001", "GRT");map.put("11010", "GRTEQ");map.put("11011", "EQ");
		map.put("11100", "NEQ");map.put("11101", "NOP");map.put("11110", "CALL");map.put("11111", "RETURN");
		return map.get(T1);
	}

	static String RegisterBit(String T2) {
		Map<String,String> map1= new HashMap<String,String>();
		map1.put("000","R0");map1.put("001","R1");map1.put("010","R2");map1.put("011","R3");
		map1.put("100","R4");map1.put("101","R5");map1.put("110","R6");map1.put("111","R7");
		return map1.get(T2);
	}

	static String BinaryToDecimal(String T2) {
		Map<String,String> map1= new HashMap<String,String>();
		map1.put("000","0");map1.put("001","1");map1.put("010","2");map1.put("011","3");
		map1.put("100","4");map1.put("101","5");map1.put("110","6");map1.put("111","7");
		return map1.get(T2);
	}

	static void updateStringToDisplay1() {
		t2 = temp.substring(5, 8);//CONTIENE EL PRIMER PARAMETRO EN BINARIO
		t2 = RegisterBit(t2);//CONTIENE EL PRIMER PARAMETRO EN PALABRA
		t3 = temp.substring(8, 11);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t3 = RegisterBit(t3);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		stringToDisplay = stringToDisplay + t1 + " " + t2 + ", " + t3;
	}

	static void updateStringToDisplay2() {
		t2 = temp.substring(5, 8);//CONTIENE EL PRIMER PARAMETRO EN BINARIO
		t2 = RegisterBit(t2);//CONTIENE EL PRIMER PARAMETRO EN PALABRA
		t3 = temp.substring(8, 11);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t3 = RegisterBit(t3);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		t4 = temp.substring(11, 14);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t4 = RegisterBit(t4);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		stringToDisplay = stringToDisplay + t1 + " " + t2 + ", " + t3 + ", " + t4;
	}

	static void updateStringToDisplay1F2() {
		t2 = temp.substring(5, 8);//CONTIENE EL PRIMER PARAMETRO EN BINARIO
		t2 = RegisterBit(t2);//CONTIENE EL PRIMER PARAMETRO EN PALABRA
		t3 = temp.substring(8, 12);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t3 = binToHex(t3);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		t4 = temp.substring(12, 16);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t4 = binToHex(t4);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		stringToDisplay = stringToDisplay + t1 + " " + t2 + ", " + t3 + t4;
	}

	static void updateStringToDisplay2F2() {
		t2 = temp.substring(5, 8);//CONTIENE EL PRIMER PARAMETRO EN BINARIO
		t2 = RegisterBit(t2);//CONTIENE EL PRIMER PARAMETRO EN PALABRA
		t3 = temp.substring(8, 12);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t3 = binToHex(t3);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		t4 = temp.substring(12, 16);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t4 = binToHex(t4);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		stringToDisplay = stringToDisplay + t1 + " " + t2 + ", #" + t3 + t4;
	}

	static void updateStringToDisplay1F3() {
		t2 = temp.substring(5, 8);//CONTIENE EL PRIMER PARAMETRO EN BINARIO
		t2 = binToHex("0"+t2);//CONTIENE EL PRIMER PARAMETRO EN PALABRA
		t3 = temp.substring(8, 12);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t3 = binToHex(t3);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		t4 = temp.substring(12, 16);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t4 = binToHex(t4);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		stringToDisplay = stringToDisplay + t1 + " " + t2 + t3 + t4;
	}

	static void updateStringToDisplay2F3() {
		//		t2 = temp.substring(8, 12);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		//		t2 = binToHex(t2);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		t2 = temp.substring(13, 16);//CONTIENE EL SEGUNDO PARAMETRO EN BINARIO
		t2 = binToHex("0"+t2);//CONTIENE EL SEGUNDO PARAMETRO EN PALABRA
		stringToDisplay = stringToDisplay + t1 + " 00" + t2;
	}

	static void pcAdder(){

		if(pc%2 != 0)
			pc++;
		else
			pc = pc + 2;
		//System.out.println("pc = "+pc);
	}





	//*************************FUNCIONES**********************************************************************



	void load(String Ra, String address) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			//aqddress es binario

			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			String result = memory[Integer.parseInt(address, 2)];
			register[rA] = result;

			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];

			System.out.println(Integer.parseInt(address,2) +" ----- "+ GUI.KeyDir);
			if(Integer.parseInt(address,2) == GUI.KeyDir){
				memory[GUI.KeyDir] = memory[GUI.KeyDir].substring(0,1)+"0";
				System.out.println("new add -- "+ memory[GUI.KeyDir]);
			}

			System.out.println("Register R"+ rA + " = " +register[rA]);

			//register[rA] = binToHex(address.substring(8, 12)) + binToHex(address.substring(12, 16));
		}
		pcAdder();
	}

	void loadim(String Ra, String cons) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			register[rA] = binToHex(cons.substring(0,4)).toUpperCase() + binToHex(cons.substring(4)).toUpperCase();

			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void pop(String Ra) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int r7 = Integer.parseInt(register[7], 16);
			register[rA] = memory[r7];

			r7++;
			register[7] = Integer.toHexString(r7).toUpperCase();
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void store(String Ra, String address) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int ad = Integer.parseInt(address, 2);
			memory[ad] = register[rA];

			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];

			System.out.println("Register R"+ rA + " = " +register[rA]);

		}
		pcAdder();
	}

	void push(String Ra) {
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		int r7 = Integer.parseInt(register[7], 16);
		r7--;
		register[7] = Integer.toHexString(r7).toUpperCase();
		memory[r7] = register[rA];
		System.out.println("memory["+r7+"] = " + memory[r7]);
		pcAdder();
	}

	void loadrind(String Ra, String Rb) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			register[rA] = memory[rB];

			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void storerind(String Ra, String Rb) {
		if(!Rb.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			register[rA] = memory[rB];

			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void add(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			int sum;
			sum = Integer.parseInt(register[rB],16);
			sum = sum + Integer.parseInt(register[rC],16);
			register[rA] = Integer.toHexString(sum).toUpperCase();
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void sub(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			int total;
			total=Integer.parseInt(register[rB],16);
			total = total - Integer.parseInt(register[rC],16);
			register[rA] = Integer.toHexString(total).toUpperCase();
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
			if(register[rA].length()>2)
				register[rA] = register[rA].substring(register[rA].length()-2);
		}
		pcAdder();
	}

	void addim(String Ra, String cons) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int temp = Integer.parseInt(register[rA], 16);
			temp = temp + Integer.parseInt(cons, 2);
			register[rA] = Integer.toHexString(temp).toUpperCase();

			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void subim(String Ra, String cons) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int temp = Integer.parseInt(register[rA], 16);
			temp = temp - Integer.parseInt(cons, 2);
			register[rA] = Integer.toHexString(temp).toUpperCase();

			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void and(String Ra, String Rb, String Rc){
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1));
			String tempoRc = hexTo4Bit(register[rC].substring(0, 1)) + hexTo4Bit(register[rC].substring(1));
			String result = "";
			for(int i = 0; i < 8; i++) {
				if(tempoRb.substring(i, i+1).equals("1") && tempoRc.substring(i, i+1).equals("1"))
					result = result + "1";
				else
					result = result + "0";
			}
			register[rA] = binToHex(result.substring(0, 4)) + binToHex(result.substring(4));
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void or(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));
			String tempoRc = hexTo4Bit(register[rC].substring(0, 1)) + hexTo4Bit(register[rC].substring(1, 2));
			String result = "";
			for(int i = 0; i < 8; i++) {
				if(tempoRb.substring(i, i+1).equals("1") || tempoRc.substring(i, i+1).equals("1"))
					result = result + "1";
				else
					result = result + "0";
			}
			register[rA] = binToHex(result.substring(0, 4)) + binToHex(result.substring(4, 8));
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void xor(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));
			String tempoRc = hexTo4Bit(register[rC].substring(0, 1)) + hexTo4Bit(register[rC].substring(1, 2));
			String result = "";
			for(int i = 0; i < 8; i++) {
				if(tempoRb.substring(i, i+1).equals(tempoRc.substring(i, i+1)))
					result = result + "0";
				else
					result = result + "1";
			}
			register[rA] = binToHex(result.substring(0, 4)) + binToHex(result.substring(4, 8));
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void not(String Ra, String Rb) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));
			String result = "";
			for(int i = 0; i < 8; i++) {
				if(tempoRb.substring(i, i+1).equals("1"))
					result = result + "0";
				else
					result = result + "1";
			}
			register[rA] = binToHex(result.substring(0, 4)) + binToHex(result.substring(4, 8));
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void neg(String Ra, String Rb) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			String tempoRbBin = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));
			String result = "";
			for(int i = 0; i < 8; i++) {
				if(tempoRbBin.substring(i, i+1).equals("1"))
					result = result + "0";
				else
					result = result + "1";
			}
			int temp = Integer.parseInt(result, 2);
			temp ++;
			register[rA] = Integer.toHexString(temp).toUpperCase();
			while(register[rA].length()<2)
				register[rA] = "0"+ register[rA];
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void shiftr(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			int times = Integer.parseInt(register[rC], 16);

			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));

			for(int i = 0; i < times; i++)
				tempoRb = "0" + tempoRb.substring(0,7);

			tempoRb = binToHex(tempoRb.substring(0, 4)) + binToHex(tempoRb.substring(4));
			register[rA] = tempoRb;
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void shiftl(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			int times = Integer.parseInt(register[rC], 16);

			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));

			for(int i = 0; i < times; i++)
				tempoRb = tempoRb.substring(1) + "0";

			tempoRb = binToHex(tempoRb.substring(0, 4)) + binToHex(tempoRb.substring(4));
			register[rA] = tempoRb;
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void rotar(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			int times = Integer.parseInt(register[rC], 16);

			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));

			for(int i = 0; i < times; i++) {
				if(tempoRb.substring(7).equals("1"))
					tempoRb = "1" + tempoRb.substring(0, 7);
				else
					tempoRb = "0" + tempoRb.substring(0, 7);
			}
			tempoRb = binToHex(tempoRb.substring(0, 4)) + binToHex(tempoRb.substring(4));
			register[rA] = tempoRb;
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void rotal(String Ra, String Rb, String Rc) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int rB = Integer.parseInt(BinaryToDecimal(Rb));
			int rC = Integer.parseInt(BinaryToDecimal(Rc));
			int times = Integer.parseInt(register[rC], 16);

			String tempoRb = hexTo4Bit(register[rB].substring(0, 1)) + hexTo4Bit(register[rB].substring(1, 2));

			for(int i = 0; i < times; i++) {
				if(tempoRb.substring(0,1).equals("1"))
					tempoRb = tempoRb.substring(1) + "1";
				else
					tempoRb = tempoRb.substring(1) + "0";
			}
			tempoRb = binToHex(tempoRb.substring(0, 4)) + binToHex(tempoRb.substring(4));
			register[rA] = tempoRb;
			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		pcAdder();
	}

	void jmprind(String Ra) { //VERIFICAR
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		pc = Integer.parseInt(register[rA], 16);
		//pc = register[rA];
		System.out.println("pc: "+pc);
	}

	void jmpaddr(String address) {
		pc = Integer.parseInt(address, 2);
		//pc = address;
		System.out.println("pc content is: "+pc);
	}

	void jcondrin(String Ra) { //VERIFICAR
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		if(cond.equals("1"))
			pc = Integer.parseInt(register[rA], 16);
		else
			pcAdder();
		//pc = register[rA]
		System.out.println("pc content is: "+pc);
	}

	void jcondaddr(String address) { //VERIFICAR
		if(cond.equals("1"))
			pc = Integer.parseInt(address, 2);
		else
			pcAdder();
		System.out.println("pc content is: "+pc);
	}

	void loop(String Ra, String address) {
		if(!Ra.equals("000")) { //VERIFICA QUE NO SE ESCRIBA EN R0
			int rA = Integer.parseInt(BinaryToDecimal(Ra));
			int temp = Integer.parseInt(register[rA], 16);
			temp --;
			register[rA] = Integer.toHexString(temp).toUpperCase();
			if(register[rA].length()<2)
				register[rA] = "0"+register[rA];
			if(temp != 0)
				pc = Integer.parseInt(address, 2);

			System.out.println("Register R"+ rA + " = " +register[rA]);
		}
		else
			pcAdder();
	}

	void grt(String Ra, String Rb) {
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		int rB = Integer.parseInt(BinaryToDecimal(Rb));

		int RA = Integer.parseInt(register[rA], 16);
		int RB = Integer.parseInt(register[rB], 16);

		if(RA > RB)
			cond = "1";
		else
			cond = "0";
		System.out.println("cond content is: "+cond);
		pcAdder();
	}

	void grteq(String Ra, String Rb) {
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		int rB = Integer.parseInt(BinaryToDecimal(Rb));

		int RA = Integer.parseInt(register[rA], 16);
		int RB = Integer.parseInt(register[rB], 16);

		if(RA >= RB)
			cond = "1";
		else
			cond = "0";
		System.out.println("cond content is: "+cond);
		pcAdder();
	}

	void eq(String Ra, String Rb) {
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		int rB = Integer.parseInt(BinaryToDecimal(Rb));

		if(register[rA].equals(register[rB]))
			cond = "1";
		else
			cond = "0";
		System.out.println("cond content is: "+cond);
		pcAdder();
	}

	void neq(String Ra, String Rb) {
		int rA = Integer.parseInt(BinaryToDecimal(Ra));
		int rB = Integer.parseInt(BinaryToDecimal(Rb));

		if(!register[rA].equals(register[rB]))
			cond = "1";
		else
			cond = "0";
		System.out.println("cond content is: "+cond);
		pcAdder();
	}

	void nop() {
		//DONE!
		pcAdder();
	}

	void call(String address) { //VERIFICAR ESTO PORQUE EL PC TIENE 12 BITS Y MEMORY SOLO TIENE 8
		int r7 = Integer.parseInt(register[7], 16);
		r7 = r7-2;
		register[7] = Integer.toHexString(r7).toUpperCase();
		if(register[7].length()<2)
			register[7] = "0" + register[7];
		memory[r7] = Integer.toHexString(pc).toUpperCase();
		pc = Integer.parseInt("0" + address, 2);

	}

	void ireturn() {
		int r7 = Integer.parseInt(register[7], 16);
		pc = r7;

		r7 = r7 + 2;
		register[7] = Integer.toHexString(r7).toUpperCase();
		if(register[7].length() < 2)
			register[7] = "0" + register[7];
	}

}
