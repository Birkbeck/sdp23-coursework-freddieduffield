package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.stream.IntStream;

/**
 *
 * This class implements the factory pattern with a singleton. It enables the creation of
 * instruction classes via dependency injection.
 *
 * @author Freddie Duffield
 */

public class InstructionFactory {
    private static final InstructionFactory instance = new InstructionFactory();

    private Instruction instruction;

    public static InstructionFactory getInstance() {
        return instance;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    /**
     *
     * Method to create an instruction and store it in the field instruction.
     *
     * @param className the name of a class and package in the format "sml.instruction.TypeOfInstruction"
     * @param arguments a list of strings in need of conversion to the correct type
     *                  to be used in the instantiation of classes
     * @return this - to enable the chaining of methods.
     */

    public InstructionFactory createInstruction(String className, String... arguments) {
        Properties props = new Properties();
        try {
            try (var fis = InstructionFactory.class.getResourceAsStream("/beans.properties")) {
                props.load(fis);
            }

            String instructionClass = props.getProperty(className + ".class");
            instruction = (Instruction) newInstanceOf(instructionClass, arguments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }


    /**
     *
     * This method is responsible for instantiating a class with arguments of the correct type
     *
     * @param className
     * @param arguments
     * @return a class instantiated with the given arguments of the correct type
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object newInstanceOf(String className, String[] arguments) {
        try {
            Class<?> classObject = Class.forName(className);
            Constructor<?> constructor = classObject.getDeclaredConstructors()[0];
            Object[] parsedArguments = getTypedArguments(constructor, arguments);
            return constructor.newInstance(parsedArguments);
        } catch (ClassNotFoundException |
                InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * This method converts strings into the type required by the given constructor.
     * It really bugs me that it is explicit names the types in the conditionals.
     * It would be preferable if this wasn't the case as it violates the open/closed principle.
     * I will endeavour to find another way 🤔
     *
     * @param constructor
     * @param arguments
     * @return an array of objects that have been converted to the types required by the given constructor
     */

    private Object[] getTypedArguments(Constructor<?> constructor, String[] arguments) {
        return IntStream.range(0, constructor.getParameterCount())
                .mapToObj(i -> {
                    String paramName = constructor.getParameterTypes()[i].getName();

                    if (paramName.equals("sml.RegisterName")) {
                        return Registers.Register.valueOf(arguments[i]);
                    }

                    if (paramName.equals("int")) {
                        return Integer.parseInt(arguments[i]);
                    }
                    return arguments[i];
                }).toArray();
    }
}
