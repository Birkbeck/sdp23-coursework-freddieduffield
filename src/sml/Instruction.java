package sml;


/**
 * Represents an abstract instruction.
 * An instruction consists of an optional label,
 * a mandatory opcode and a number of operands.
 * The central method that must be overridden in subclass is execute.
 * It also contains a static field that contains the value of normal program counter update.
 *
 * @author ...
 */
public abstract class Instruction {
	protected final String label;
	protected final String opcode;

	/**
	 * Constructor: an instruction with a label and an opcode
	 * (opcode must be an operation of the language)
	 *
	 * @param label optional label (can be null)
	 * @param opcode operation name
	 */
	public Instruction(String label, String opcode) {
		this.label = label;
		this.opcode = opcode;
	}

	public String getLabel() {
		return label;
	}

	public String getOpcode() {
		return opcode;
	}

	public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	/**
	 * Executes the instruction in the given machine.
	 *
	 * @param machine the machine the instruction runs on
	 * @return the new program counter (for jump instructions)
	 *          or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *          the instruction with the next address is to be executed
	 */

	public abstract int execute(Machine machine);

	protected String getLabelString() {
		return (getLabel() == null) ? "" : getLabel() + ": ";
	}

	// abstract in this context means that the method body will need to be defined in each of the subclasses
	// that extend this class. It is only possible to declare an abstract method in an abstract class.
	@Override
	public abstract String toString();

	@Override
	public abstract boolean equals(Object o);
}
