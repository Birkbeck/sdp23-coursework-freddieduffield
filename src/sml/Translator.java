package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

import sml.Registers.Register;

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

                int argumentCount = line.split("\\s+").length;

                Instruction instruction = getInstruction(label, argumentCount);
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
     * @param label         the instruction label
     * @param argumentCount
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label, int argumentCount) {
        if (line.isEmpty())
            return null;


        String opcode = scan();

        try {
            Class<?> instructionClass = Class.forName(getClassNameFromOpcode(opcode));
            Constructor<?> constructor = instructionClass.getDeclaredConstructors()[0];
            Object[] parameterObjs = new Object[argumentCount];
            // get the candidate constructor parameters
            Class<?>[] paramCons = constructor.getParameterTypes();
            for (int i = 0; i < argumentCount; i++) {
                // attempt to type the parameters using any available string constructors
                // NoSuchMethodException will be thrown where retyping isn't possible
                Class<?> c = toWrapper(paramCons[i]);
                parameterObjs[i] = c.getConstructor(String.class).newInstance(scan());
            }
            // return instance ob object using the successful constructor
            // and parameters of the right class types.
            return (Instruction) constructor.newInstance(parameterObjs);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Class<?> instructionClass = Class.forName(getClassNameFromOpcode(opcode));
//            Constructor<?> constructor = instructionClass.getDeclaredConstructors()[0];
//            Object[] args = IntStream.range(0, constructor.getParameterCount())
//                    .mapToObj(i -> {
//                        String paramName = constructor.getParameterTypes()[i].getName();
//                        if (i == 0) {
//                            return label;
//                        }
//
//                        if (paramName == "sml.RegisterName") {
//                            return Register.valueOf(scan());
//                        }
//
//                        if (paramName == "int") {
//                            return Integer.parseInt(scan());
//                        }
//
//                        return scan();
//                    }).toArray();
//
//            return (Instruction) constructor.newInstance(args);
//
//
//        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

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

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPERS = Map.of(
            int.class, Integer.class,
            long.class, Long.class,
            boolean.class, Boolean.class,
            byte.class, Byte.class,
            char.class, Character.class,
            float.class, Float.class,
            double.class, Double.class,
            short.class, Short.class,
            void.class, Void.class
    );

    /**
     * Return the correct Wrapper class if testClass is primitive
     *
     * @param testClass class being tested
     * @return Object class or testClass
     */
    private static Class<?> toWrapper(Class<?> testClass) {
        return PRIMITIVE_TYPE_WRAPPERS.getOrDefault(testClass, testClass);
    }
}