package net.yellowstrawberry.solveana.solve;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProblemTester {

    public static void main(String[] args) throws ClassNotFoundException {
        test("", """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("wow");
                    }
                }
                """, 0);
    }

    public static float test(String testcases, String code, int timeout) throws ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        List<JavaFileObject> sourceFiles = Collections.singletonList(new StringJava("Main", code));

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics()
                    .forEach(System.err::println);
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass("Main");
            try {
                TimeLimitedCodeBlock.runWithTimeout(
                        () -> {
                            try {
                                clazz.getMethod("main", String[].class).invoke(null, new Object[]{new String[0]});
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        timeout,
                        TimeUnit.SECONDS
                );
            } catch (Exception e) {
                if(e instanceof TimeoutException) {
                    System.out.println("Timeout");
                }else throw new RuntimeException(e);
            }
        }

        return 1f;
    }
}
