package net.yellowstrawberry.solveana.solve.test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestCases {

    private final int m;
    private int c;
    private final Queue<TestCase> testCases = new LinkedList<>();
    private TestCase current;

    public TestCases(TestCase... testCases) {
        this.testCases.addAll(List.of(testCases));
        this.m = testCases.length;
    }

    public boolean next() {
        if(testCases.isEmpty()) return false;
        current = testCases.poll();
        c++;
        return true;
    }

    public String getPercentage() {
        return String.format("%.3f%%", (c/(double)m)*100d);
    }

    public TestCase getCurrent() {
        return current;
    }
}
