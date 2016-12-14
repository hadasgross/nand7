package ex6;

import interfaces.IllegalCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Assembler {
    //TODO: MY ASSEMBLER DOESN'T WORK WITH JUMPS!!!
    private static final String A_INSTRUCTION_START = "0", C_INSTRUCTION_START = "111", SHIFT_INSTRUCTION_START = "101",
            HACK_FILE_SUFFIX = ".hack", USAGE_MESSAGE = "Usage: Assembler <filename or directory name>";
    private static final int NUM_OF_BITS = 16;
    private static final Pattern ASM_FILENAME_PATTERN = Pattern.compile("\\S+.asm");
    public static void main(String[] args) {
        try {
            File path = new File(args[0]);
            String filename;
            if (path.isDirectory()) {
                File[] listOfFiles = path.listFiles();
                if (listOfFiles != null) {
                    for (File file: listOfFiles) {
                        filename = file.getAbsolutePath();
                        if (checkFileType(filename)) {
                            updateVariables(filename);
                            convertFile(filename);
                        }
                    }
                }
            }
            else {
                filename = path.getAbsolutePath();
                if (checkFileType(filename)){
                    updateVariables(filename);
                    convertFile(filename);
                }
            }

        }
        catch (IOException e) {
            System.out.println(USAGE_MESSAGE);
        }
        catch (IllegalCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean checkFileType(String path) {
        return ASM_FILENAME_PATTERN.matcher(path).matches();
    }

    private static void updateVariables(String filename) throws FileNotFoundException, IllegalCommandException{
        AssemblerParser parser = new AssemblerParser(filename);
        int lineNum = 1;
        while (parser.hasMoreCommands()) {
            parser.advance();
            switch ((HackTypes)parser.commandType()) {
                case L_COMMAND:
                    String variable = parser.symbol();
                    if (!Code.SYMBOL_TABLE.containsKey(variable)) {
                        Code.SYMBOL_TABLE.put(variable, lineNum+1);
                    }
                    break;
                default:
                    lineNum++;
                    break;
            }
        }
        parser.close();
    }
    private static void convertFile(String filename) throws IllegalCommandException, IOException {
        BufferedWriter outputFile;
        File file = new File(filename.split(".asm")[0] + HACK_FILE_SUFFIX);
        outputFile = new BufferedWriter(new FileWriter(file));
        AssemblerParser parser = new AssemblerParser(filename);
        String currLine = null;
        while (parser.hasMoreCommands()) {
            parser.advance();
            switch ((HackTypes)parser.commandType()) {
                case A_COMMAND:
                    String addressOrVariable = parser.symbol();
                    int address;
                    Matcher m = Pattern.compile("\\d+").matcher(addressOrVariable);
                    if (m.matches()) {
                        address = Integer.parseInt(addressOrVariable);
                    }
                    else {
                        if (!Code.SYMBOL_TABLE.containsKey(addressOrVariable)) {
                            Code.SYMBOL_TABLE.put(addressOrVariable, Code.SYMBOL_TABLE_INDEX);
                            Code.SYMBOL_TABLE_INDEX++;
                        }
                        address = Code.SYMBOL_TABLE.get(addressOrVariable);
                    }
                    String binaryAddress = Integer.toBinaryString(address);
                    if (binaryAddress.length() < NUM_OF_BITS-1) {
                        String zeros = new String(new char[NUM_OF_BITS-1-binaryAddress.length()]).replace("\0", "0");
                        binaryAddress = zeros + binaryAddress;
                    }
                    currLine = A_INSTRUCTION_START + binaryAddress;
                    break;
                case C_COMMAND:
                    currLine = C_INSTRUCTION_START + Code.comp(parser.comp()) + Code.dest(parser.dest()) +
                            Code.jump(parser.jump());
                    break;
                case SHIFT_COMMAND:
                    currLine = SHIFT_INSTRUCTION_START + Code.comp(parser.comp()) + Code.dest(parser.dest()) +
                            Code.jump(parser.jump());
                    break;
                case L_COMMAND:
                    continue;
            }
            outputFile.write(currLine);
            if (parser.hasMoreCommands()) {
                outputFile.newLine();
            }
        }
        outputFile.close();
        parser.close();
    }

}
