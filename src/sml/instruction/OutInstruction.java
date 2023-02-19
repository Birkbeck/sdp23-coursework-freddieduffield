package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

public class OutInstruction extends Instruction {
    private final RegisterName source;

    public static final String OP_CODE = "out";

    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    @Override
    public int execute(Machine machine) {
        System.out.println("Register " + source + ": ");
        System.out.println(machine.getRegisters().get(source));
        return 0;
    }

    @Override
    public String toString() {
        return "Print contents of register -> " + source;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OutInstruction ins) {
            return Objects.equals(this.source, ins.source)
                    && Objects.equals(this.label, ins.label)
                    && Objects.equals(this.opcode, ins.opcode);
        }

        return false;
    }
}
