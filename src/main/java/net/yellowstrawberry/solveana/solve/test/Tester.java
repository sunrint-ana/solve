package net.yellowstrawberry.solveana.solve.test;


import net.yellowstrawberry.solveana.solve.memory.InMemoryFileManager;
import net.yellowstrawberry.solveana.solve.object.StringJava;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Tester {

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));
    private long time = 0;
    private int tests = 0;

    private final String id;
    public Tester(String id) {
        this.id = id;
    }

    public boolean compile(String code) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        List<JavaFileObject> sourceFiles = Collections.singletonList(new StringJava(id, code));
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        return task.call();
    }

    public String run(int timeout) {
        try {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(id);

            try {
                time += TimeLimitedCodeBlock.runWithTimeout(
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
                tests++;
            } catch (Exception e) {
                if(e instanceof TimeoutException) {
                    return "시간 초과";
                }else {
                    return e.getClass().getName() + ": " + e.getLocalizedMessage();
                }
            }
        }catch (Exception ignored){
            return "서버 오류";
        }
        return null;
    }

    public long getAverageTime() {
        return time/tests;
    }
}
