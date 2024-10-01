package net.yellowstrawberry.solveana.solve.io;


import net.yellowstrawberry.solveana.solve.test.TestCases;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class IOManager {
    static final Map<String, TestCases> testCases = new ConcurrentHashMap<>();

    static String findUnique() {
        Optional<StackTraceElement> elem = Arrays.stream(new Exception().getStackTrace()).parallel().filter(e-> e.getClassName().startsWith("SA_")).findAny();
        return elem.map(StackTraceElement::getClassName).orElse(null);
    }

    public static void registerTestCase(String id, TestCases testCase) {
        testCases.put(id, testCase);
    }

    public static boolean passTestCase(String id) {
        return testCases.get(id).next();
    }

    public static boolean checkTestCase(String id) {
        return testCases.get(id).getCurrent().checkOutput();
    }

    public static void unregisterTestCase(String id) {
        testCases.remove(id);
    }
}
