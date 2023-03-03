package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

public class MovInstruction extends Instruction {
    private RegisterName register;
    private int value;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, RegisterName register, int value) {
        super(label, OP_CODE);
        this.register = register;
        this.value = value;
    }

    /**
     *
     * This method places a given value in a register address.
     *
     * @param machine the machine the instruction runs on
     * @return a normal program counter update
     */
    @Override
    public int execute(Machine machine) {
        machine.getRegisters().set(register, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + " " + getOpcode() + " " + register + " " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MovInstruction ins) {
            return Objects.equals(this.register, ins.register)
                    && Objects.equals(this.value, ins.value)
                    && Objects.equals(this.label, ins.label)
                    && Objects.equals(this.opcode, ins.opcode);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(register, value);
    }
}
