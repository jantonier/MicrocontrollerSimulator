//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.Test;
//
//class TesterSim {
//
//    Main m = new Main();
//
//    @Test
//    void test10() {
//        System.out.println("R0: " + Main.register[0]);
//        System.out.println("R1: " + Main.register[1]);
//        System.out.println("R2: " + Main.register[2]);
//        System.out.println("R3: " + Main.register[3]);
//        System.out.println("R4: " + Main.register[4]);
//        System.out.println("R5: " + Main.register[5]);
//        System.out.println("R6: " + Main.register[6]);
//        System.out.println("R7: " + Main.register[7]);
//
//        m.add("001", "001", "001");
//        assertTrue(Main.register[1].equals("46"));
//    }
//
//    @Test
//    void test11() {
//        m.neg("001", "001");
//        assertTrue(Main.register[1].equals("BA"));
//        System.out.println("R7: " + Main.pc);
//    }
//
//    @Test
//    void test12() {
//        m.addim("011", "00000110");
//        assertTrue(Main.register[3].equals("F7"));
//    }
//
//    @Test
//    void test13() {
//        m.subim("010", "00000001");
//        assertTrue(Main.register[2].equals("30"));
//    }
//
//    @Test
//    void test14() {
//        m.and("001", "100", "011");
//        assertTrue(Main.register[1].equals("82"));
//    }
//
//    @Test
//    void test15() {
//        m.or("001", "010", "101");
//        assertTrue(Main.register[1].equals("34"));
//    }
//
//    @Test
//    void test16() {
//        m.xor("001", "010", "001");
//        assertTrue(Main.register[1].equals("04"));
//    }
//
//    @Test
//    void test17() {
//        m.not("111", "001");
//        assertTrue(Main.register[7].equals("FB"));
//    }
//
//    @Test
//    void test18() {
//        m.sub("111", "111", "010");
//        assertTrue(Main.register[7].equals("CB"));
//    }
//
//    @Test
//    void test19() {
//        m.shiftr("001", "111", "001");
//        assertTrue(Main.register[1].equals("0C"));
//    }
//
//    @Test
//    void test1() {
//        Main.register[7]="CB";
//        m.shiftl("001", "111", "101");
//        assertTrue(Main.register[1].equals("B0"));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test2() {
//        m.rotar("001", "111", "110");
//        assertTrue(Main.register[1].equals("79"));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test3() {
//        m.rotal("001", "111", "110");
//        assertTrue(Main.register[1].equals("5E"));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test4() {
//        m.load("001", "01111110");
//        assertTrue(Main.register[1].equals("7E"));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test5() {
//        m.loadim("001", "01010101");
//        assertTrue(Main.register[1].equals("55"));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test6() {
//        m.store("001", "00011010");
//        assertTrue(Main.register[1].equals("1A"));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test7() {
//        m.storerind("001", "010");
//        assertTrue(Main.register[2].equals(Main.memory[1]));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test8() {
//        m.loadrind("001", "111");
//        assertTrue(Main.register[1].equals(Main.memory[7]));
//        System.out.println("pc: " + Main.pc);
//    }
//
//    @Test
//    void test9() {
//        m.jmprind("001");
//        assertTrue(Main.pc==07); //Cantidad de operaciones * 2
//
//    }
//
//    @Test
//    void test20() {
//        m.jmpaddr("00001111");
//        assertTrue(Main.pc==15);
//    }
//
//    @Test
//    void test21() {
//        Main.cond="1";
//        m.jcondrin("001");
//        assertTrue(Main.pc==12); //Cantidad de operaciones * 2
//    }
//
//    @Test
//    void test22() {
//        m.jcondaddr("011");
//        assertTrue(Main.pc==3);
//    }
//
//    @Test
//    void test23() {
//        m.grt("001", "111");
//        assertTrue(Main.cond.equals("0"));
//    }
//
//    @Test
//    void test24() {
//        m.grteq("001", "001");
//        assertTrue(Main.cond.equals("1"));
//    }
//
//    @Test
//    void test25() {
//        m.eq("001", "111");
//        assertTrue(Main.cond.equals("0"));
//    }
//
//    @Test
//    void test26() {
//        m.neq("001", "111");
//        assertTrue(Main.cond.equals("1"));
//    }
//
//    @Test
//    void test27() {
//        Main.register[7]=Main.register[6];
//        m.pop("001");
//        assertTrue(Main.register[1].equals(Main.memory[Integer.parseInt(Main.register[7], 16)-1]));
//
//    }
//
//    @Test
//    void test28() {
//        m.push("001");
//        System.out.println("PUSH " +Main.memory[7]);
//        assertTrue(Main.memory[Integer.parseInt(Main.register[7], 16)].equals(Main.register[1]));
//    }
//
//    @Test
//    void test29() {
//        m.loop("001", "00000111");
//        assertTrue(Main.pc==7);
//    }
//
//    @Test
//    void test30() {
//        m.call("0000011110");
//        assertTrue(Main.pc==30);
//    }
//
//    @Test
//    void test31() {
//        m.not("001", "100");
//        assertTrue(Main.register[1].equals("7D"));
//    }
//
//    @Test
//    void test32() {
//        m.ireturn();
//        assertTrue(Main.pc==Integer.parseInt(Main.register[7], 16)-2);
//    }
//}
//
//
