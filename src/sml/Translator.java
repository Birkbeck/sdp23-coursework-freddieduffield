package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName = fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    // TODO: Next, use dependency injection to allow this machine class
    //   to work with different sets of opcodes (different CPUs)

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        String opcode = scan();
        String r = scan();
        String s = scan();

        try {
            Class<Instruction> insClass;
            insClass = (Class<Instruction>) Class.forName(getClassNameFromOpcode(opcode));
            Constructor<?> constructor = insClass.getDeclaredConstructors()[0];

            var registerParam = Registers.Register.valueOf(r);
            var thirdParam = getOptionalThirdParam(s, constructor.getParameterTypes());
            Object[] args = {label, registerParam, thirdParam};

            // still not quite open to extension but closed for modification.
            // e.g a new instruction with one param would require this code to modified.
            if (constructor.getParameterCount() < 3) {
                return (Instruction) constructor.newInstance(label, registerParam);
            }

            return (Instruction) constructor.newInstance(args);

        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object getOptionalThirdParam(String s, Class<?>[] paramTypes) {
        if (paramTypes.length > 2) {
            Class<?> paramType = paramTypes[2];

            if (paramType == int.class) {
                return Integer.parseInt(s);
            }

            if (paramType == RegisterName.class) {
                return Registers.Register.valueOf(s);
            }

            if (paramType == String.class) {
                return s;
            }
        }

        return null;
    }

    protected String getClassNameFromOpcode(String opcode) {
        return "sml.instruction." + opcode.substring(0, 1).toUpperCase() + opcode.substring(1) + "Instruction";
    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
    }
}