package ex6;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.deploy.util.StringUtils;
import interfaces.IllegalCommandException;
import interfaces.Parser;

public class AssemblerParser extends Parser{


    private static final String DEST = "(([ADM]|AMD|AD|MD|AM|null)=)?", JUMP = "(;(null|JGT|JEQ|JGE|JLT|JNE|JLE|JMP))?",
    COMP = "((D[\\&\\|][AM])|([DAM][\\+-][1ADM])|(!?[ADM])|(0|1|-1)|)", SHIFT = "([ADM](<<|>>))",
            ALWAYS_JMP = ";(null|JGT|JEQ|JGE|JLT|JNE|JLE|JMP)", NULL_STR = "", SHTRUDLE="@", EQUALS="=", JUMP_SEP=";",
            COMMENT_SEP = "\\/\\/", BLANK = "\\s", L_COMMAND_PARENTHESIS = "\\(|\\)",
            C_COMMAND_STRING = DEST + COMP + JUMP, SHIFT_COMMAND_STRING="("+DEST+SHIFT+JUMP +")|("+SHIFT+ALWAYS_JMP+")";
    private static final int JUMP_LENGTH = 2, COMP_PART = 1, JUMP_PART=1, DEST_PART=0;
    private static final Pattern A_COMMAND_PATTERN = Pattern.compile("(@)(\\d+|\\S+)"),
            C_COMMAND_PATTERN = Pattern.compile(C_COMMAND_STRING),
            L_COMMAND_PATTERN = Pattern.compile("(\\()(\\S+)(\\))"),
            SHIFT_PATTERN = Pattern.compile(SHIFT_COMMAND_STRING);

    public AssemblerParser(String filename) throws FileNotFoundException {
        super(filename);
    }
    @Override
    public void advance() {
        super.advance();
        currInstruction = currInstruction.replaceAll(BLANK, NULL_STR);
    }
    @Override
    public CommandType commandType() throws IllegalCommandException {
        Matcher aMatcher = A_COMMAND_PATTERN.matcher(currInstruction);
        Matcher cMatcher = C_COMMAND_PATTERN.matcher(currInstruction);
        Matcher lMatcher = L_COMMAND_PATTERN.matcher(currInstruction);
        Matcher shiftMatcher = SHIFT_PATTERN.matcher(currInstruction);
        if (aMatcher.matches()) {
            return HackTypes.A_COMMAND;
        }
        if (cMatcher.matches()) {
            return HackTypes.C_COMMAND;
        }
        if (lMatcher.matches()) {
            return HackTypes.L_COMMAND;
        }
        if (shiftMatcher.matches()) {
            return HackTypes.SHIFT_COMMAND;
        }
        else {
            throw new IllegalCommandException(currInstruction); //TODO: write unique exception
        }
    }
    public String symbol() throws IllegalCommandException {//assume we get a or l commands. checking is on the user's responsibility
        if (commandType() == HackTypes.A_COMMAND) {
            return currInstruction.substring(currInstruction.indexOf(SHTRUDLE) + 1).trim();
        }
        else {
            return currInstruction.replaceAll(L_COMMAND_PARENTHESIS, NULL_STR);
        }
    }
    public String dest() { //assume we get c command. checking is on the user's responsibility
        String dest = NULL_STR;
        if (currInstruction.contains(EQUALS)) {
            String[] parts = currInstruction.split(EQUALS);
            dest = parts[DEST_PART];
        }
        return dest;
    }
    public String comp() { //assume we get c command. checking is on the user's responsibility
        String compPart;
        if (!dest().equals(NULL_STR)) {
            String[] parts = currInstruction.split(EQUALS);
            compPart = parts[COMP_PART];
        }
        else  {
            compPart = currInstruction;
        }
        return compPart.split(JUMP_SEP)[0];

    }
    public String jump() { //assume we get c command. checking is on the user's responsibility
        String compPart;
        if (!dest().equals(NULL_STR)) {
            String[] parts = currInstruction.split(EQUALS);
            compPart = parts[COMP_PART];
        }
        else  {
            compPart = currInstruction;
        }
        String[] jumpParts = compPart.split(JUMP_SEP);
        if (jumpParts.length < JUMP_LENGTH) {
            return NULL_STR;
        }
        else {
           return jumpParts[JUMP_PART];
        }
    }
}