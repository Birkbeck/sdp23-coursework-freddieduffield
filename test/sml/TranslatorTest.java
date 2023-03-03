package sml;

import org.junit.jupiter.api.*;
import sml.instruction.*;

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
        program = null;
        labels = null;
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
        registers.set(EAX,0);
        registers.set(EBX, 3);

        translator = new Translator("testDIV.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }

    @Test
    @DisplayName("Move")
    void testMov() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new MovInstruction(null, EAX, 10));
        registers.set(EAX, 10);

        translator = new Translator("testMOV.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }

    @Test
    @DisplayName("Jump")
    void testJNZ() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new JnzInstruction(null, EAX, "label"));
        registers.set(EAX, 10);

        translator = new Translator("testJNZ.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }

    @Test
    @DisplayName("Out")
    void testOut() throws IOException {
        List<Instruction> expectedAddProgram = List.of(new OutInstruction(null, EAX));
        registers.set(EAX, 10);

        translator = new Translator("testOUT.sml");
        translator.readAndTranslate(labels, program);

        Assertions.assertEquals(expectedAddProgram, program);
    }


}
