package sml.instruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;

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
    @DisplayName("When source register > 0, returns label address")
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
