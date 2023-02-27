package sml.instruction;

import org.junit.jupiter.api.*;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class JnzInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setup() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        machine.getLabels().addLabel("t1", 10);
    }

    @Test
    @DisplayName("When source register > 0, moves to label address")
    void validExecution() {
        registers.set(EAX, 3);
        Instruction instruction = new JnzInstruction(null, EAX, "t1");
        int result = instruction.execute(machine);
        Assertions.assertEquals(10, result);
    }

    @Test
    @DisplayName("When source register == 0, return normal program counter update")
    void validExecution2() {
        registers.set(EAX, 0);
        Instruction instruction = new JnzInstruction(null, EAX, "t1");
        int result = instruction.execute(machine);
        Assertions.assertEquals(-1, result);
    }
}
