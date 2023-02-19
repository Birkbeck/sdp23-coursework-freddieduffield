package sml.instruction;

import org.junit.jupiter.api.*;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class MovInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setup() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
    }

    @Test
    void validExecution() {
        Assertions.assertEquals(0, registers.get(EBP));
        Instruction instruction = new MovInstruction(null, EBP, 8);
        instruction.execute(machine);
        Assertions.assertEquals(8, registers.get(EBP));
    }
}
