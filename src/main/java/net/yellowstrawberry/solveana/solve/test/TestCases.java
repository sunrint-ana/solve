package net.yellowstrawberry.solveana.solve.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestCases {

    private final Queue<TestCase> testCases = new LinkedList<>();
    private TestCase current;

    public TestCases(TestCase... testCases) {
        this.testCases.addAll(List.of(testCases));
    }

    public boolean next() {
        if(testCases.isEmpty()) return false;
        current = testCases.poll();
        return true;
    }

    public TestCase getCurrent() {
        return current;
    }
}
