package net.yellowstrawberry.solveana.solve.memory;

import net.yellowstrawberry.solveana.solve.object.ByteJava;

import javax.tools.*;
import java.util.Hashtable;
import java.util.Map;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private final ClassLoader loader;
    private final Map<String, ByteJava> compiledClasses;

    public InMemoryFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
        this.compiledClasses = new Hashtable<>();
        this.loader = new InMemoryClassLoader(this.getClass().getClassLoader(), this);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className, JavaFileObject.Kind kind, FileObject sibling) {

        ByteJava classAsBytes = new ByteJava(className, kind);
        compiledClasses.put(className, classAsBytes);

        return classAsBytes;
    }

    public Map<String, ByteJava> getBytesMap() {
        return compiledClasses;
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return loader;
    }
}