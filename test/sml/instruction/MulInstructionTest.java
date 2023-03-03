package sml.instruction;

import org.junit.jupiter.api.*;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

public class MulInstructionTest {
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
    @DisplayName("Multiply two positive integers and store them in the given register: 5 * 5")
    void executeValid() {
        registers.set(EAX, 5);
        registers.set(EBX, 5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(25, machine.getRegisters().get(EAX));
    }

    @Test
    @DisplayName("Multiply negative and positive integer and store them in the given register: 5 * -5")
    void executeValidTwo() {
        registers.set(EAX, -5);
        registers.set(EBX, -5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-25, machine.getRegisters().get(EAX));
    }

    @Test
    @DisplayName("Multiply two negative integer and store them in the given register: -5 * - 5")
    void executeValidThree() {
        registers.set(EAX, -5);
        registers.set(EBX, -5);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(25, machine.getRegisters().get(EAX));
    }
}
