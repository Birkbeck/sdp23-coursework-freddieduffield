package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class provides functionality for jump operation.
 * This means given a register location and label as operands it jumps the program counter to specified label
 * if the register is not empty
 */
public class JnzInstruction extends Instruction {
    private final String nextLabel;
    private final RegisterName source;

    public static final String OPP_CODE = "jnz";

    public JnzInstruction(String label, RegisterName source,  String nextLabel) {
        super(label, OPP_CODE);
        this.nextLabel = nextLabel;
        this.source = source;
    }


    /**
     *
     * Based on a given register lookup - if the register is not 0 the program counter
     * then the address of the register is return otherwise the program counter is updated as normal
     *
     * @param machine the machine the instruction runs on
     * @return either a normal program counter update or the address of the label
     */
    @Override
    public int execute(Machine machine) {
        return machine.getRegisters().get(source) != 0
                ? machine.getLabels().getAddress(nextLabel)
                : NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source + " " + nextLabel;
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
