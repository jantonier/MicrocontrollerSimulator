package Sprint1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Subs {
	/*		public static void main(String args[] ) {

			Subs subs=new Subs("ADD R4,R0,    R7");		
	}*/	
	static String line;
	String[] fixedLine= {"","","",""};
	String numPart="";//buscar Db string(todo lo que esta luego transformarlo en Token
	public Subs() {

		//System.out.println("s: "+s);
		//System.out.println("line : "+line);
		//		ReferenceTable refTable=new ReferenceTable();//metodos buscar address

	}	
	public String trimLine(String line){
		String trimedLine1=line.replace(',', ' ');
		String trimedLine2=trimedLine1.trim();System.out.println(trimedLine2);
		return trimedLine2;
	}
	public String trimDBLine(String line){
		String trimedLine1=line.trim();System.out.println(trimedLine1);
		for(int i=0;i<trimedLine1.length();i++) {//borra espacios entre numeros
			if(trimedLine1.charAt(i)==' ')
			{trimedLine1=trimedLine1.substring(0,i)+trimedLine1.substring(i+1);i--;		
			}		
		}
		return trimedLine1;
	}


	public String takeAtferDBPart(String t2) {
		//coge la linea de DB y le saca el # 
		//
		int endDB=0;

		for(int i=0; i<t2.length()-1;i++) {
			if(t2.charAt(i)=='D') {
				if(t2.charAt(i+1)=='B') {
					endDB=i+1;	
				}
			}
		}
		numPart=t2.substring(endDB+1);
		return numPart;
	}
	public	String[] makeTokens(String trimedLine2){

		int LineLength=trimedLine2.length();System.out.println("lenght: "+LineLength);
		int count=0; int token=0;
		//divide values in temp variables		
		String t1="";		int a=0;	
		while(trimedLine2.charAt(count)!=' ' && token==0&&LineLength !=count&&a==0) {
			t1=t1+trimedLine2.charAt(count);
			count++;
			if(t1.length()==trimedLine2.length()) { a=1;count--;}
		}
		System.out.println("t1:" + t1);		
		while(trimedLine2.charAt(count)==' ') {
			count++;
		}//

		String t2="";
		token++;			
		while( token==1 &&LineLength !=count &&a==0) {
			if(trimedLine2.charAt(count)!=' ' )
			{t2=t2+trimedLine2.charAt(count);
			count++;
			}
			else {
				token++;
			}
		}System.out.println("t2:" + t2);
		String t3=""; //opcional
		String t4="";

		if(LineLength!=count) { //no hay mas en la linea
			while(trimedLine2.charAt(count)==' ') {
				count++;
			}

			while(token==2 &&LineLength !=count) {			
				if(trimedLine2.charAt(count)!=' ' ) {
					t3=t3+trimedLine2.charAt(count);
					count++;
				}
				else{	token++;}
			}System.out.println("t3:" + t3);
		}

		if(LineLength!=count) //no hay mas en la linea
		{while(trimedLine2.charAt(count)==' ') {
			count++;
		}
		while( token==3 &&LineLength !=count) {

			if(trimedLine2.charAt(count)!=' ' ) 
			{t4=t4+trimedLine2.charAt(count);
			count++;}
			else {
				token++;
			}
		}System.out.println("t4:" + t4);
		}		
		fixedLine[0]=t1;
		fixedLine[1]=t2;
		fixedLine[2]=t3;
		fixedLine[3]=t4;

		return fixedLine;


		//}

	}
	//**********************************************************************************************

	// RETURN 1   2  3 or -1
	static int checkFormat(String T1) {
		if(T1.equals("ADD")||T1.equals("SUB")||T1.equals("AND")||T1.equals("XOR")||T1.equals("SHIFTR")||T1.equals("SHIFTL")||T1.equals("ROTAR")||T1.equals("ROTAL")
				||T1.equals("LOADRIND")||T1.equals("STORERIND")||T1.equals("NOT")||T1.equals("NEG")||T1.equals("GRT")||T1.equals("GRTEQ")||T1.equals("EQ")||T1.equals("NEQ")
				||T1.equals("JMPRIND")||T1.equals("NOP")||T1.contentEquals("OR")) {
			return 1;}
		else if(T1.equals("LOAD")||T1.equals("STORE")||T1.equals("LOOP")||T1.equals("LOADIM")||T1.equals("ADDIM")||T1.equals("SUBIM")||T1.equals("POP")||T1.equals("PUSH")) {
			return 2;
		}					//Jump
		else if(T1.equals("JMPADDR")||T1.equals("JCONDRIN")||T1.equals("JCONDADDR")||T1.equals("CALL")) {
			return 3;
		}
		else if(T1.equals("RETURN")) {//falta instruccion Return(31)
			return 0;
		}		
		else
			return -1;		
	}

	static String TransfromDBtoByte(String db) {
		String dbBit="";

		int val =Integer.parseInt(db,16);//cambia hex digit to Integer
		String binaryAddress=Integer.toBinaryString(val);

		while(binaryAddress.length()<8)
		{binaryAddress="0"+binaryAddress;}
		return binaryAddress;
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++METODO TRANSFORMA A FORMATO 1,2 o 3 en BIT+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	static String TransformToFormatBits(String t1,String t2 , String t3,String t4,ArrayList<ReferenceTable> ref)
	{
		ArrayList<ReferenceTable> table = ref ;
		String sF="";	
		if(checkFormat(t1)==1) {

			if(t1.equals("ADD")||t1.equals("SUB")||t1.equals("AND")||t1.contentEquals("OR")||t1.equals("XOR")||t1.equals("SHIFTR")||t1.equals("SHIFTL")||t1.equals("ROTAR")||t1.equals("ROTAL")) {
				sF=InstructionBit(t1)+RegisterBit(t2)+RegisterBit(t3)+RegisterBit(t4)+"00";}
			else if(t1.equals("LOADRIND")||t1.equals("STORERIND")||t1.equals("NOT")||t1.equals("NEG")||t1.equals("GRT")||t1.equals("GRTEQ")||t1.equals("EQ")||t1.equals("NEQ")) {
				sF=InstructionBit(t1)+RegisterBit(t2)+RegisterBit(t3)+"00000";
				//GRT usa Cond(falta)
			}
			else if(t1.equals("JMPRIND")) {
				sF=InstructionBit(t1)+RegisterBit(t2)+"00000000";
			}
			else {//NOP
				sF=InstructionBit(t1)+"00000000000";
			}
		}
		else if(checkFormat(t1)==2) {
			String temp="";
			int i=0; //para coger bit por bit del hex
			//with address
			if(t1.equals("LOAD")||t1.equals("STORE")||t1.equals("LOOP")) 
			{	
				if(t1.equals("LOAD")) {
					//buscar en tabla
					t3=elimHastag(t3);//quita #


					for(ReferenceTable r : table) {
						if(r.name.equalsIgnoreCase(t3)) {
							t3 = r.getAddress();break;							
						}
					}					
					if(t3.length()==1) {
						t3="0"+t3;   }

					int val =Integer.parseInt(t3,16);//cambia hex digit to Integer
					String binaryAddress=Integer.toBinaryString(val);
					System.out.println("binAddress:"+binaryAddress);
					while(binaryAddress.length()!=8) {
						binaryAddress="0"+binaryAddress;
					}				
					sF=InstructionBit(t1)+RegisterBit(t2)+binaryAddress;

				}
				if(t1.equals("STORE")) {
					boolean validVar=false;
					t2=elimHastag(t2);//quita #

					for(ReferenceTable r : table) {
						if(r.name.equalsIgnoreCase(t2)) {
							t2 = r.getAddress();
							validVar=true;
							break;
						}
					}
					if(validVar==false) {
						System.out.println("Variable to store not found");
					}
					int val =Integer.parseInt(t2,16);//cambia hex digit to Integer
					String binaryAddress=Integer.toBinaryString(val);
					System.out.println("binAddress:"+binaryAddress);
					while(binaryAddress.length()!=8) {
						binaryAddress="0"+binaryAddress;
					}				
					sF=InstructionBit(t1)+RegisterBit(t3)+binaryAddress;

				}
				else {//LOOP
					for(ReferenceTable r : table) {
						t3=elimHastag(t3);//quita #

						if(r.name.equalsIgnoreCase(t3)) {
							t3 = r.getAddress();
							break;
						}
					}
					
					int val =Integer.parseInt(t3,16);//cambia hex digit to Integer
					String binaryAddress=Integer.toBinaryString(val);
					System.out.println("binAddress:"+binaryAddress);
					while(binaryAddress.length()!=8) {
						binaryAddress="0"+binaryAddress;
					}
					sF=InstructionBit(t1)+RegisterBit(t2)+binaryAddress;
				}

			}
			//Integer.parseInt(hex,16);    
			//System.out.print(Integer.toBinaryString(hex));
			//with constant (LOADIM,ADDIM,SUBIM )
			else if(t1.equals("LOADIM")||t1.contentEquals("ADDIM")||t1.equals("SUBIM")){ 
				t3=elimHastag(t3);//quita #
				//verificar si en una variable CONST

				for(ReferenceTable r : table) {
					if(r.name.equals(t3)) {
						t3 = r.getContent();
						break;
						//System.out.println(t3);
					}
				}
				if(t3.length()==1) {
					t3="0"+t3;
				}
				int val =Integer.parseInt(t3,16);//cambia hex digit to Integer

				String binaryConstant=Integer.toBinaryString(val);
				System.out.println("binConst:"+binaryConstant);
				while(binaryConstant.length()!=8) {
					binaryConstant="0"+binaryConstant;
				}

				sF=InstructionBit(t1)+RegisterBit(t2)+binaryConstant;				
			}
			else if(t1.equals("PUSH")||t1.contentEquals("POP"))//POP  PUSH
			{
				sF=InstructionBit(t1)+RegisterBit(t2)+"00000000";
			}
		}
		else if(checkFormat(t1)==3) {

			if(t1.equals("JMPADDR")||t1.equals("JCONDADDR")) {
				//buscar en tabla
				for(ReferenceTable r : table) {
					if(r.name.equals(t2)) {
						t2 = r.getAddress();
						break;
						//System.out.println(t3);
					}
				}
				int val =Integer.parseInt(t2,16);//cambia hex digit to Integer
				String binaryAddress=Integer.toBinaryString(val);
				System.out.println("binAddress:"+binaryAddress);
				while(binaryAddress.length()!=11) {//rellena
					binaryAddress="0"+binaryAddress;
				}
				sF=InstructionBit(t1)+binaryAddress;
			}			
			if(t1.equals("JCONDRIN")) {//
				
				sF=InstructionBit(t1)+RegisterBit(t2)+"00000000";

			}
			if(t1.equals("CALL")) {//
				for(ReferenceTable r : table) {
					if(r.name.equals(t2)) {
						t2 = r.getAddress();
						break;
						//System.out.println(t3);
					}
				}
				int val =Integer.parseInt(t2,16);//cambia hex digit to Integer
				String binaryAddress=Integer.toBinaryString(val);
				System.out.println("binAddress:"+binaryAddress);
				while(binaryAddress.length()!=8) {//rellena
					binaryAddress="0"+binaryAddress;
				}
				
				sF=InstructionBit(t1)+"000"+binaryAddress;

			}

		}//hacer metodo 

		else  //Return ""	(checkFormat(t1)==0)				
		{return sF="1111100000000000"; //RETURN //PC <- mem[SP]    //SP <- SP + 2
		}

		return sF;

	}
	static String InstructionBit(String T1) {

		Map<String,String> map= new HashMap<String,String>();			
		map.put("LOAD", "00000");map.put("LOADIM","00001");map.put("POP","00010");map.put("STORE","00011");
		map.put("PUSH", "00100");map.put("LOADRIND", "00101");map.put("STORERIND", "00110");map.put("ADD", "00111");
		map.put("SUB", "01000");	map.put("ADDIM", "01001");map.put("SUBIM", "01010");map.put("AND", "01011");
		map.put("OR", "01100");map.put("XOR", "01101");map.put("NOT", "01110");map.put("NEG", "01111");
		map.put("SHIFTR", "10000");map.put("SHIFTL", "10001");map.put("ROTAR", "10010");map.put("ROTAL", "10011");
		map.put("JMPRIND", "10100");map.put("JMPADDR", "10101");map.put("JCONDRIN", "10110");map.put("JCONDADDR", "10111");
		map.put("LOOP", "11000");map.put("GRT", "11001");map.put("GRTEQ", "11010");map.put("EQ", "11011");
		map.put("NEQ", "11100");map.put("NOP", "11101");map.put("CALL", "11110");map.put("RETURN", "11111");

		//System.out.println(map.get(T1));
		return map.get(T1);
	}

	static String RegisterBit(String T2) {
		Map<String,String> map1= new HashMap<String,String>();
		map1.put("R0", "000");map1.put("R1", "001");map1.put("R2","010");map1.put("R3", "011");
		map1.put("R4", "100");map1.put("R5","101");map1.put("R6", "110");map1.put("R7", "111");
		System.out.println(map1.get(T2));
		return map1.get(T2);
	}

	static String elimHastag(String T3) {
		if(T3.charAt(0)=='#') {
			T3=T3.substring(1, T3.length());
		}
		return T3;
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

	public static String hexTo4Bit(String s) {
		switch(s) {
		case "0":
			return "0000";
		case "1":
			return "0001";
		case "2":
			return "0010";
		case "3":
			return "0011";
		case "4":
			return "0100";
		case "5":
			return "0101";
		case "6":
			return "0110";
		case "7":
			return "0111";
		case "8":
			return "1000";
		case "9":
			return "1001";
		case "A":
			return "1010";
		case "B":
			return "1011";
		case "C":
			return "1100";
		case "D":
			return "1101";
		case "E":
			return "1110";
		default:
			return "1111";//F
		}
	}




}
