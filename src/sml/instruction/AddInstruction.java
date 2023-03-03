package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 *
 * This class represents an addition instruction.
 * It takes two register names as operands and adds them together
 * in the override of the execute method. A normal program counter update is returned
 *
 * @author Freddie Duffield
 */

public class AddInstruction extends Instruction {
	private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "add";

	public AddInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 + value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AddInstruction ins) {
			return Objects.equals(this.opcode, ins.opcode)
					&& Objects.equals(this.label, ins.label)
					&& Objects.equals(result, ins.result)
					&& Objects.equals(source, ins.source);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(result, source);
	}
}
