package sml.instruction;

import org.junit.jupiter.api.*;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class AddInstructionTest {
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
  }

  @Test
  @DisplayName("It should add two positive integers and store them in the given register: 5 + 6")
  void executeValid() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(11, registers.get(EAX));
  }

  @Test
  @DisplayName("It should add one positive and one negative integers and store them in the given register: -5 + 6")
  void executeValidTwo() {
    registers.set(EAX, -5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, registers.get(EAX));
  }

  @Test
  @DisplayName("It should add two negative integers and store them in the given register: -5 + -6")
  void executeValidThree() {
    registers.set(EAX, -5);
    registers.set(EBX, -6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(-11, registers.get(EAX));
  }
}