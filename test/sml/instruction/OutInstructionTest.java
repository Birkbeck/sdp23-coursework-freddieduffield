package sml.instruction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.EBX;

public class OutInstructionTest {
    private Machine machine;
    private Registers registers;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Prints the contents of register")
    void validExecution() {
        registers.set(EBX, 8);
        Instruction instruction = new OutInstruction(null, EBX);
        instruction.execute(machine);
        Assertions.assertEquals("8", outputStreamCaptor.toString().trim());
    }
}
