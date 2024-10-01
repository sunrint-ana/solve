package net.yellowstrawberry.solveana.solve.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import static net.yellowstrawberry.solveana.solve.io.IOManager.findUnique;
import static net.yellowstrawberry.solveana.solve.io.IOManager.testCases;

public class SolveAnAOutputStream extends PrintStream {

    public static final SolveAnAOutputStream o = new SolveAnAOutputStream();
    private final PrintStream printStream = System.out;

    public SolveAnAOutputStream() {
        super(System.out);
    }

    @Override
    public void write(int b) {
        String s = findUnique();
        if(s==null) super.write(b);
    }

    public void printToIOMan(String id, String x) {
        if(testCases.containsKey(id)) testCases.get(id).getCurrent().print(x);
    }

    @Override
    public void print(boolean b) {
        String s = findUnique();
        if(s==null) super.print(b);
        else printToIOMan(s, String.valueOf(b));
    }

    @Override
    public void print(char c) {
        String s = findUnique();
        if(s==null) super.print(c);
        else printToIOMan(s, String.valueOf(c));
    }

    @Override
    public void print(int i) {
        String s = findUnique();
        if(s==null) super.print(i);
        else printToIOMan(s, String.valueOf(i));
    }

    @Override
    public void print(long l) {
        String s = findUnique();
        if(s==null) super.print(l);
        else printToIOMan(s, String.valueOf(l));
    }

    @Override
    public void print(float f) {
        String s = findUnique();
        if(s==null) super.print(f);
        else printToIOMan(s, String.valueOf(f));
    }

    @Override
    public void print(double d) {
        String s = findUnique();
        if(s==null) super.print(d);
        else printToIOMan(s, String.valueOf(d));
    }

    @Override
    public void print(char[] s) {
        String i = findUnique();
        if(i==null) super.print(s);
        else printToIOMan(i, new String(s));
    }

    @Override
    public void print(String s) {
        String i = findUnique();
        if(i==null) super.print(s);
        else printToIOMan(i, s);
    }

    @Override
    public void print(Object obj) {
        String s = findUnique();
        if(s==null) super.print(obj);
        else printToIOMan(s, String.valueOf(obj));
    }

    @Override
    public void println() {
        String s = findUnique();
        if(s==null) super.println();
        else printToIOMan(s, "\n");
    }

    @Override
    public void println(boolean x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(char x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(int x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(long x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(float x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(double x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(char[] x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, new String(x)+"\n");
    }

    @Override
    public void println(String x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public void println(Object x) {
        String s = findUnique();
        if(s==null) super.println(x);
        else printToIOMan(s, x+"\n");
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        String s = findUnique();
        if(s==null) return super.printf(format, args);
        printToIOMan(s, String.format(format, args));
        return this;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        String s = findUnique();
        if(s==null) return super.printf(l, format, args);
        printToIOMan(s, String.format(l, format, args));
        return this;
    }


}
