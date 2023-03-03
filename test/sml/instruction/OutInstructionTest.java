package sml.instruction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.*;
import static sml.Registers.Register.*;

public class OutInstructionTest {
    private Machine machine;
    private Registers registers;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void validExecution() {
        registers.set(EBX, 8);
        Instruction instruction = new OutInstruction(null, EBX);
        instruction.execute(machine);
        Assertions.assertEquals("8", outputStreamCaptor.toString().trim());
    }
}
