package sml.instruction;

import org.junit.jupiter.api.*;
import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.EAX;
import static sml.Registers.Register.EBX;

class SubInstructionTest {
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
  @DisplayName("It should subtract two positive integers and store them in the given register: 8 - 5")
  void executeValid() {
    registers.set(EAX, 8);
    registers.set(EBX, 5);
    Instruction instruction = new SubInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(3, registers.get(EAX));
  }

  @Test
  @DisplayName("It should subtract negative from a positive integer and store them in the given register: 8 - -5")
  void executeValidTwo() {
    registers.set(EAX, 8);
    registers.set(EBX, -5);
    Instruction instruction = new SubInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(13, registers.get(EAX));
  }

  @Test
  @DisplayName("It should subtract a positive from a negative integer and store them in the given register: -8 - 5")
  void executeValidThree() {
    registers.set(EAX, -8);
    registers.set(EBX, 5);
    Instruction instruction = new SubInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(-13, registers.get(EAX));
  }

  @Test
  @DisplayName("It should subtract two negative integers and store them in the given register: -5 - -6")
  void executeValidFour() {
    registers.set(EAX, -8);
    registers.set(EBX, -5);
    Instruction instruction = new SubInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(-3, registers.get(EAX));
  }
}