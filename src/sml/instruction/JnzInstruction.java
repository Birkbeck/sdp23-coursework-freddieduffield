package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers.Register;

import java.util.Objects;

public class JnzInstruction extends Instruction {
    private final String nextLabel;
    private final RegisterName source;

    public static final String OPP_CODE = "jnz";

    public JnzInstruction(String label, RegisterName source,  String nextLabel) {
        super(label, OPP_CODE);
        this.nextLabel = nextLabel;
        this.source = source;
    }

    @Override
    public int execute(Machine machine) {
        return machine.getRegisters().get(source) != 0
                ? machine.getLabels().getAddress(nextLabel)
                : NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return "    " + getOpcode() + " " + source + " " + nextLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JnzInstruction that = (JnzInstruction) o;
        return Objects.equals(nextLabel, that.nextLabel) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source);
    }
}
