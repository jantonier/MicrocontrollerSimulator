//package Sprint1;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//import org.junit.jupiter.api.Test;
//
//class JTest1 {
//	TestFormNuestro tfn=new TestFormNuestro();
//	Subs s=new Subs();
//    Subs s2=new Subs();
//    Subs s3=new Subs();
//    Subs s4=new Subs();
//    Subs s5=new Subs();
//    Subs s6=new Subs();
//    Subs s7=new Subs();
//    Subs s8=new Subs();
//	ReferenceTable rt= new ReferenceTable();
//
//
//
//	String sentence1=" ADD r1, r2, r3";
//	String array1[]=s.makeTokens(s.trimLine(sentence1).toUpperCase());
//	@Test
//	void test1_a() {
//		assertEquals(sentence1.toUpperCase()," ADD R1, R2, R3");
//	}
//	@Test
//	void test1_b() {
//		assertEquals(array1[0],"ADD");
//	}
//	@Test
//	void test1_c() {
//		assertEquals(array1[1],"R1");
//	}
//	@Test
//	void test1_d() {
//		assertFalse(array1[2].equals("r2"));
//	}
//	@Test
//	void test1_e() {
//		assertEquals(array1[3],"R3");
//	}
//String sentence2=" JMPADDR valor";
//String array2[]=s2.makeTokens(s2.trimLine(sentence2).toUpperCase());
//
//	@Test
//	void test2_a() {
//		assertEquals(s2.InstructionBit(array2[0]),"10101");
//	}
//	@Test
//	void test2_b() {
//		tfn.table.add(new ReferenceTable("VALOR", "CONST", "10","5"));
//		String t2=array2[1];
//		for(ReferenceTable r : tfn.table) {
//			if(r.name.equals(t2)) {
//				t2 = r.getAddress();
//				assertEquals(t2,"10");
//			}
//		}
//	}
//	@Test
//	void test2_c() {
//	assertEquals(s.TransformToFormatBits(array2[0], array2[1], array2[2], array2[3],tfn.table ),"1010100000010000");
//	}
//	@Test
//	void test2_d() {
//	assertFalse(s.TransformToFormatBits(array2[0], array2[1], array2[2], array2[3],tfn.table )=="1010100000001010");
//	}
//	@Test
//	void test2_e() {
//	assertTrue(array2[0].equals("JMPADDR"));
//	assertTrue(array2[1].equals("VALOR"));
//	assertFalse(array2[1].equals("valor"));
//	assertTrue(array2[2].equals(""));
//	assertTrue(array2[3].equals(""));
//	}
//
//
//	String sentence3=" rEturn";
//	String array3[]=s3.makeTokens(s.trimLine(sentence3).toUpperCase());
//	@Test
//	void test3_a() {
//	assertTrue(sentence3.toUpperCase().equals(" RETURN"));
//	assertFalse(sentence3.toUpperCase().equals("RETURN"));
//	}
//	@Test
//	void test3_b() {
//		assertEquals(array3[0],"RETURN");
//		}
//	@Test
//	void test3_c() {
//		assertEquals(array3[1],"");
//		}
//	@Test
//	void test3_d() {
//		assertEquals(array3[2],"");
//		}
//	@Test
//	void test3_e() {
//		assertEquals(array3[3],"");
//		}
//	@Test
//	void test3_f() {
//		assertEquals(s3.TransformToFormatBits(array3[0], array3[1], array3[2], array3[3],tfn.table ),"1111100000000000");
//		assertFalse(s3.TransformToFormatBits(array3[0], array3[1], array3[2], array3[3],tfn.table ).equals("1111100001000000"));
//	}
//
//	String sentence4="      pop r3";
//	String array4[]=s4.makeTokens(s.trimLine(sentence4).toUpperCase());
//	@Test
//	void test4_a() {
//	assertEquals((s.trimLine(sentence4).toUpperCase()),"POP R3");
//	assertFalse((s.trimLine(sentence4).toUpperCase()).equals(" POP R3"));
//	}
//	@Test
//	void test4_b() {
//		assertFalse(s4.InstructionBit(array4[0]).equals("0001000000000000"));
//		assertTrue(s4.InstructionBit(array4[0]).equals("00010"));
//	}
//
//	String sentence5=" Xor r1,R1,r3";
//	String array5[]=s5.makeTokens(s5.trimLine(sentence5).toUpperCase());
//
//	@Test
//	void test5_a() {
//	assertEquals((s5.trimLine(sentence5).toUpperCase()),"XOR R1 R1 R3");
//	assertFalse((s5.trimLine(sentence5).toUpperCase()).equals(" XOR R1 R1 R3"));
//	}
//	@Test
//	void test5_b() {
//		assertEquals(s5.TransformToFormatBits(array5[0], array5[1], array5[2], array5[3],tfn.table ),"0110100100101100");
//		assertFalse(s5.TransformToFormatBits(array5[0], array5[1], array5[2], array5[3],tfn.table ).equals("1111100001000000"));
//		}
//}
//
