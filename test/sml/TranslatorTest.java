package sml;

import org.junit.jupiter.api.*;
import sml.instruction.AddInstruction;

import java.io.IOException;
import java.util.*;

import static sml.Registers.Register.*;

public class TranslatorTest {
    private Translator translator;
    private List<Instruction> program;
    private Labels labels;
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setup() {
        program = new ArrayList<>();
        labels = new Labels();
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    @DisplayName("Add")
    void testAdd() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new AddInstruction(null, EAX, EBX ));
        registers.set(EAX, 3);
        registers.set(EBX, 8);

        translator = new Translator("testADD.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }
}
