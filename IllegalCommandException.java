package interfaces;

public class IllegalCommandException extends Exception {
    String instruction;
    public IllegalCommandException(String command) {
        this.instruction = command;
    }
    public String getMessage() {
        return "Illegal line in file: " + instruction;
    }
}
