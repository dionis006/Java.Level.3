import testing.AfterSuite;
import testing.BeforeSuite;
import testing.Test;

public class TestPet {
    @BeforeSuite
    void setUp() {
        System.out.println("Setting up...");
    }

    @AfterSuite
    void tearDown() {
        System.out.println("Finalizing test class...");
    }

    @Test(order = 1)
    void shouldTestSmthOne() {
        System.out.println("Test 1");
    }

    @Test(order = 3)
    void shouldTestSmthThree() {
        System.out.println("Test 3");
    }

    @Test(order = 2)
    void shouldTestSmthTwo() {
        System.out.println("Test 2");
    }

}
