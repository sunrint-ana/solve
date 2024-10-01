package net.yellowstrawberry.solveana.solve.io;

import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStream;

import static net.yellowstrawberry.solveana.solve.io.IOManager.findUnique;
import static net.yellowstrawberry.solveana.solve.io.IOManager.testCases;

public class SolveAnAInputStream extends InputStream {

    public static final SolveAnAInputStream i = new SolveAnAInputStream();
    private final InputStream stream;

    public SolveAnAInputStream() {
        stream = System.in;
    }

    @Override
    public int available() throws IOException {
        String s = findUnique();
        if(s==null) return stream.read();
        if(!testCases.containsKey(s)) return -1;
        return testCases.get(s).getCurrent().available();
    }

    @Override
    public int read() throws IOException {
        String s = findUnique();
        if(s==null) return stream.read();
        if(!testCases.containsKey(s)) return -1;

        return testCases.get(s).getCurrent().read();
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        String s = findUnique();
        if(s!=null) {
            if(!testCases.containsKey(s)) return -1;
            return testCases.get(s).getCurrent().read(b, off, len);
        }else {
            return stream.read(b, off, len);
        }
    }
}
