package sml.instruction;

import org.junit.jupiter.api.*;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

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
    @DisplayName("3 * 2 = 6")
    void executeValid() {
        registers.set(EAX, 3);
        registers.set(EBX, 2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(6, machine.getRegisters().get(EAX));
    }

    @Test
    @DisplayName("-3 * 2 = -15")
    void executeValid2() {
        registers.set(EAX, -3);
        registers.set(EBX, 2);
        Instruction instruction = new MulInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-6, machine.getRegisters().get(EAX));

    }

}
