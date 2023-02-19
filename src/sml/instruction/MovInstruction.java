package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.Registers.Register;

import java.util.Objects;

/**
 * Class that provides functionality for the opcode <b>mov</b>
 * The instruction will move a value to a specified register
 * @author freddie duffield
 */
public class MovInstruction extends Instruction {
    private Register register;
    private int value;

    public static final String OPP_CODE = "mov";

    public MovInstruction(String label,  Register register, int value) {
        super(label, OPP_CODE);
        this.register = register;
        this.value = value;
    }

    @Override
    public int execute(Machine machine) {
        machine.getRegisters().set(register, value);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return null;
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
}
