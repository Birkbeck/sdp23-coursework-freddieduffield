package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.stream.IntStream;

public class InstructionFactory {
    private static final InstructionFactory instance = new InstructionFactory();

    private Instruction instruction;

    public static InstructionFactory getInstance() {
        return instance;
    }

    public Instruction getInstruction() {
        return instruction;
    }

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
        return null;
    }


    private Object newInstanceOf(String className, String[] arguments) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> classObject = Class.forName(className);
        Constructor<?> constructor = classObject.getDeclaredConstructors()[0];
        Object[] parsedArguments = getTypedArguments(constructor, arguments);
        return constructor.newInstance(parsedArguments);
    }

    private Object[] getTypedArguments(Constructor<?> constructor, String[] arguments) {
        return IntStream.range(0, constructor.getParameterCount())
                .mapToObj(i -> {
                    String paramName = constructor.getParameterTypes()[i].getName();
                    if (paramName == "sml.RegisterName") {
                        return Registers.Register.valueOf(arguments[i]);
                    }

                    if (paramName == "int") {
                        return Integer.parseInt(arguments[i]);
                    }
                    return arguments[i];
                }).toArray();
    }
}
