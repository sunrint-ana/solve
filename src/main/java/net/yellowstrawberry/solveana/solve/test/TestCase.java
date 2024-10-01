package net.yellowstrawberry.solveana.solve.test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TestCase {

    private int cur = 0;
    private final byte[] ipt;
    private final String opt;
    private String gottenOpt;

    public TestCase(String ipt, String opt) {
        this.ipt = ipt.getBytes(StandardCharsets.UTF_8);
        this.opt = opt;
    }

    public int read() {
        return cur++==-1?0:ipt[cur];
    }

    public int read(byte[] b, int off, int len) {
        if(cur == ipt.length) return -1;
        int i = 0;
        for (; i < len; i++) {
            b[off + i] = ipt[cur++];
            if(cur == ipt.length) return i+1;
        }
        return i+1;
    }

    public boolean checkOutput() {
        if(gottenOpt==null) return false;
        if(gottenOpt.lastIndexOf('\n') == gottenOpt.length()-1) gottenOpt = gottenOpt.substring(0, gottenOpt.lastIndexOf('\n'));
        return gottenOpt.replaceAll("\r\n", "\n").equals(opt.replaceAll("\r\n", "\n"));
    }

    public int available() {
        return ipt.length-(cur==-1?0:cur);
    }

    public void print(String x) {
        if(gottenOpt == null) gottenOpt = "";
        gottenOpt += x;
    }
}
