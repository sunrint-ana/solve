package net.yellowstrawberry.solveana.db.problem;

public class Example {
    private int num;
    private String ipt;
    private String opt;

    public Example(int num, String ipt, String opt) {
        this.num = num;
        this.ipt = ipt;
        this.opt = opt;
    }

    public int getNum() {
        return num;
    }

    public String getIpt() {
        return ipt;
    }

    public String getOpt() {
        return opt;
    }
}
