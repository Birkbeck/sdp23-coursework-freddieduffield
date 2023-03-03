package sml;

import org.junit.jupiter.api.*;

// an address can only have one label
// a label can only be one name

public class LabelsTest {
    private Labels labels;
    @BeforeEach
    void setup() {
        labels = new Labels();
    }

    @AfterEach
    void teardown() {
        labels = null;
    }


    @Test
    @DisplayName("No two instructions can have the same label")
    void testAddLabelChecksForDuplicates1() {
        labels.addLabel("test", 101);
        labels.addLabel("test", 102);

        Assertions.assertEquals(101, labels.getAddress("test"));
    }

    @Test
    @DisplayName("Two labels with different names can be added")
    void testAddLabelChecksForDuplicates2() {
        labels.addLabel("test1", 101);
        labels.addLabel("test2", 102);

        Assertions.assertEquals(101, labels.getAddress("test1"));
        Assertions.assertEquals(102, labels.getAddress("test2"));
    }

}
