package test;

import main.Solution;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTest {
    private String test0 = "STWSWTPPTPTTPWPP";
    @Test
    void getResult0() {
        assertEquals(10, Solution.getResult(test0, "Human"));
    }
    @Test
    void getResult1() {
        assertEquals(15, Solution.getResult(test0, "Swamper"));
    }
    @Test
    void getResult2() {
        assertEquals(12, Solution.getResult(test0, "Woodman"));
    }
    //the program must not throw an exception
    @Test
    void exceptionTest() {
        System.out.println(Solution.getResult("asdf", "Swamper"));
        System.out.println(Solution.getResult("TWSPPTWSWWSSPPTT", ""));
    }
}