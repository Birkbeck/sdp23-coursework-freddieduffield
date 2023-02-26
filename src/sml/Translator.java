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
        this.fileName =  fileName;
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

    // TODO: Then, replace the switch by using the Reflection API

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

        String[] args = new String[]{label, r, s};
        try {
           Class<?> c = Class.forName(getClassNameFromOpcode(opcode));
           Constructor[] constructors = c.getDeclaredConstructors();
           Class<?>[] params = constructors[0].getParameterTypes();

            Object[] transformedParams = convertArgumentsToParameterType(args);

//           return (Instruction) c.newInstance(args);
            return null;
//        } catch (NoSuchMethodException ignored) {
//            ignored.printStackTrace();
//        } catch (InstantiationException ignored) {
//            ignored.printStackTrace();
//        } catch (IllegalAccessException ignored) {
//            ignored.printStackTrace();
//        } catch (InvocationTargetException ignored) {
//            ignored.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected Object[] convertArgumentsToParameterType(String[] args) {
        // string Register.valueOf(r)
        // int Integer.parseInt(v)
        Object[] convertedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals(null)) {
                if (isInteger(args[i])) {
                    convertedArgs[i] = Integer.parseInt(args[i]);
                } else if (Registers.Register.valueOf(args[i]) != null) {
                    convertedArgs[i] = Registers.Register.valueOf(args[i]);
                } else {
                    convertedArgs[i] = args[i];
                }
            } else {
                convertedArgs[i] = args[i];
            }
        }

        return convertedArgs;
    }

    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    protected String getClassNameFromOpcode(String opcode) {
        return "sml.instruction." + opcode.substring(0,1).toUpperCase() + opcode.substring(1) + "Instruction";
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