package sml;

import org.junit.jupiter.api.*;

public class TranslatorTest {
    Translator t;

    @BeforeEach
    void setup() {
        t = new Translator("TEST");
    }

    @Test
    @DisplayName("getClassNameFromOpcode name for mov")
    void testGetClassName1() {
        String result = t.getClassNameFromOpcode("mov");

        Assertions.assertEquals("sml.instruction.MovInstruction", result);
    }

    @Test
    @DisplayName("getClassNameFromOpcode name for add")
    void testGetClassName2() {
        String result = t.getClassNameFromOpcode("add");

        Assertions.assertEquals("sml.instruction.AddInstruction", result);
    }

    @Test
    @DisplayName("getClassNameFromOpcode name for sub")
    void testGetClassName3() {
        String result = t.getClassNameFromOpcode("sub");

        Assertions.assertEquals("sml.instruction.SubInstruction", result);
    }

    @Test
    @DisplayName("getClassNameFromOpcode name for div")
    void testGetClassName4() {
        String result = t.getClassNameFromOpcode("div");

        Assertions.assertEquals("sml.instruction.DivInstruction", result);
    }

    @Test
    @DisplayName("getClassNameFromOpcode name for mul")
    void testGetClassName5() {
        String result = t.getClassNameFromOpcode("mul");

        Assertions.assertEquals("sml.instruction.MulInstruction", result);
    }

    @Test
    @DisplayName("getClassNameFromOpcode name for out")
    void testGetClassName6() {
        String result = t.getClassNameFromOpcode("out");

        Assertions.assertEquals("sml.instruction.OutInstruction", result);
    }


    @Test
    @DisplayName("getClassNameFromOpcode name for jnz")
    void testGetClassName7() {
        String result = t.getClassNameFromOpcode("jnz");

        Assertions.assertEquals("sml.instruction.JnzInstruction", result);
    }

    @Test
    @DisplayName("convertArgumentsToParameterType: Converts an int to string")
    void testConvertArgumentsToParameterType1() {
        String[] args = {"88"};
        Object[] result = t.convertArgumentsToParameterType(args);
        Assertions.assertEquals(88, result[0]);
    }

    @Test
    @DisplayName("convertArgumentsToParameterType: Converts an a registerName to a Register")
    void testConvertArgumentsToParameterType2() {
        String[] args = {"EBX"};
        Object[] result = t.convertArgumentsToParameterType(args);
        Assertions.assertEquals(Registers.Register.EBX, result[0]);
    }

    @Test
    @DisplayName("convertArgumentsToParameterType: does not convert a string that not registerName")
    void testConvertArgumentsToParameterType3() {
        String[] args = {"f3:"};
        Object[] result = t.convertArgumentsToParameterType(args);
        Assertions.assertEquals("f3:", result[0]);
    }

}
