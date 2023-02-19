package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * Class that provides functionality for the opcode <b>subtract</b>
 * The instruction will perform a subtraction operation
 *
 * @author freddie duffield
 */
public class SubInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "sub";

    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 - value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubInstruction ins) {
            return Objects.equals(this.result, ins.result)
                    && Objects.equals(this.source, ins.source)
                    && Objects.equals(this.label, ins.label)
                    && Objects.equals(this.opcode, ins.opcode);
        }

        return false;
    }
}
