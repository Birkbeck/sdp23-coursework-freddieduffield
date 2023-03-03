package sml.instruction;

import org.junit.jupiter.api.*;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class DivInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    @DisplayName("Divide two positive integers and store them in the given register: 9 / 3")
    void executeValid() {
        registers.set(EAX, 9);
        registers.set(EBX, 3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(3, registers.get(EAX));
    }

    @Test
    @DisplayName("Divide negative from a positive integer and store them in the given register: 9 / -3")
    void executeValidTwo() {
        registers.set(EAX, 9);
        registers.set(EBX, -3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-3, registers.get(EAX));
    }

    @Test
    @DisplayName("Divide a negative with a positive integer and store them in the given register: -9 / 3")
    void executeValidThree() {
        registers.set(EAX, -9);
        registers.set(EBX, 3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-3, registers.get(EAX));
    }

    @Test
    @DisplayName("Divide two negative integers and store them in the given register: -9 / -3")
    void executeValidFour() {
        registers.set(EAX, -9);
        registers.set(EBX, -3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(3, registers.get(EAX));
    }
}
