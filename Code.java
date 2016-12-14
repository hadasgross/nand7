package ex6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Code {
    public static final Map<String, String> DEST_MAP, COMP_MAP, JUMP_MAP;
    public static HashMap<String, Integer> SYMBOL_TABLE;
    public static int SYMBOL_TABLE_INDEX = 16;
    static {
        DEST_MAP= new HashMap<String, String>();
        COMP_MAP = new HashMap<String, String>();
        JUMP_MAP = new HashMap<String, String>();
        DEST_MAP.put("", "000");
        DEST_MAP.put("null", "000");
        DEST_MAP.put("M", "001");
        DEST_MAP.put("D", "010");
        DEST_MAP.put("MD", "011");
        DEST_MAP.put("A", "100");
        DEST_MAP.put("AM", "101");
        DEST_MAP.put("AD", "110");
        DEST_MAP.put("AMD", "111");
        Collections.unmodifiableMap(DEST_MAP);
        COMP_MAP.put("0", "0101010");
        COMP_MAP.put("1", "0111111");
        COMP_MAP.put("-1", "0111010");
        COMP_MAP.put("D", "0001100");
        COMP_MAP.put("A", "0110000");
        COMP_MAP.put("M", "1110000");
        COMP_MAP.put("!D", "0001101");
        COMP_MAP.put("!A", "0110001");
        COMP_MAP.put("!M", "1110001");
        COMP_MAP.put("-D", "0001111");
        COMP_MAP.put("-A", "0110011");
        COMP_MAP.put("-M", "1110011");
        COMP_MAP.put("D+1", "0011111");
        COMP_MAP.put("A+1", "0110111");
        COMP_MAP.put("M+1", "1110111");
        COMP_MAP.put("D-1", "0001110");
        COMP_MAP.put("A-1", "0110010");
        COMP_MAP.put("M-1", "1110010");
        COMP_MAP.put("D+A", "0000010");
        COMP_MAP.put("D+M", "1000010");
        COMP_MAP.put("D-A", "0010011");
        COMP_MAP.put("D-M", "1010011");
        COMP_MAP.put("A-D", "0000111");
        COMP_MAP.put("M-D", "1000111");
        COMP_MAP.put("D&M", "1000000");
        COMP_MAP.put("D&A", "0000000");
        COMP_MAP.put("D|M", "1010101");
        COMP_MAP.put("D|A", "0010101");
        COMP_MAP.put("D<<", "0110000");
        COMP_MAP.put("A<<", "0100000");
        COMP_MAP.put("M<<", "1100000");
        COMP_MAP.put("D>>", "0010000");
        COMP_MAP.put("A>>", "0000000");
        COMP_MAP.put("M>>", "1000000");
        Collections.unmodifiableMap(COMP_MAP);
        JUMP_MAP.put("", "000");
        JUMP_MAP.put("null", "000");
        JUMP_MAP.put("JGT", "001");
        JUMP_MAP.put("JEQ", "010");
        JUMP_MAP.put("JGE", "011");
        JUMP_MAP.put("JLT", "100");
        JUMP_MAP.put("JNE", "101");
        JUMP_MAP.put("JLE", "110");
        JUMP_MAP.put("JMP", "111");
        Collections.unmodifiableMap(JUMP_MAP);
        SYMBOL_TABLE = new HashMap<String, Integer>();
        SYMBOL_TABLE.put("R0", 0);
        SYMBOL_TABLE.put("R1", 1);
        SYMBOL_TABLE.put("R2", 2);
        SYMBOL_TABLE.put("R3", 3);
        SYMBOL_TABLE.put("R4", 4);
        SYMBOL_TABLE.put("R5", 5);
        SYMBOL_TABLE.put("R6", 6);
        SYMBOL_TABLE.put("R7", 7);
        SYMBOL_TABLE.put("R8", 8);
        SYMBOL_TABLE.put("R9", 9);
        SYMBOL_TABLE.put("R10", 10);
        SYMBOL_TABLE.put("R11", 11);
        SYMBOL_TABLE.put("R12", 12);
        SYMBOL_TABLE.put("R13", 13);
        SYMBOL_TABLE.put("R14", 14);
        SYMBOL_TABLE.put("R15", 15);
        SYMBOL_TABLE.put("SP", 0);
        SYMBOL_TABLE.put("LCL", 1);
        SYMBOL_TABLE.put("ARG", 2);
        SYMBOL_TABLE.put("THIS", 3);
        SYMBOL_TABLE.put("THAT", 4);
        SYMBOL_TABLE.put("SCREEN", 16384);
        SYMBOL_TABLE.put("KBD", 2476);
    }

    public static String dest(String destString) {
        return DEST_MAP.get(destString);
    }

    public static String comp(String compString) { return COMP_MAP.get(compString); }

    public static String jump(String jumpString) {
        return JUMP_MAP.get(jumpString);
    }
}