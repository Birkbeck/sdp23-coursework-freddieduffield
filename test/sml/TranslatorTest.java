package sml;

import org.junit.jupiter.api.*;
import sml.instruction.AddInstruction;
import sml.instruction.DivInstruction;
import sml.instruction.MulInstruction;
import sml.instruction.SubInstruction;

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

    @Test
    @DisplayName("Subtract")
    void testSub() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new SubInstruction(null, EAX, EBX));
        registers.set(EAX, 3);
        registers.set(EBX, 8);

        translator = new Translator("testSUB.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }

    @Test
    @DisplayName("Multiply")
    void testMultiply() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new MulInstruction(null, EAX, EBX));
        registers.set(EAX, 3);
        registers.set(EBX, 8);

        translator = new Translator("testMUL.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }

    @Test
    @DisplayName("Divide")
    void testDivide() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new DivInstruction(null, EAX, EBX));
        registers.set(EAX, 9);
        registers.set(EBX, 3);

        translator = new Translator("testDiv.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }


}
