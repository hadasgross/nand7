package interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public abstract class Parser {

    public interface CommandType{}
    private static final String NULL_STR = "", COMMENT_SEP = "\\/\\/";

        protected Scanner fileReader = null;
        protected String currInstruction = null;

        public Parser(String filename) throws FileNotFoundException {
            File inputFile = new File(filename);
            fileReader = new Scanner(inputFile);
        }
        public boolean hasMoreCommands() {
            return fileReader.hasNextLine();
        }
        public void advance() {
            if (hasMoreCommands()) {
                do {
                    currInstruction = fileReader.nextLine();
                    String[] parts=currInstruction.split(COMMENT_SEP);
                    currInstruction=parts[0];
                } while (currInstruction.equals(NULL_STR));

                currInstruction = currInstruction.trim();
            }
        }
        public abstract CommandType commandType() throws IllegalCommandException;


        public void close() { fileReader.close();}
}
