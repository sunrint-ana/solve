package net.yellowstrawberry.solveana.db.problem;

import jakarta.persistence.*;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Locale;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String info;
    private Long accepted;
    private Long pushed;
    private String author;
    private Timestamp timestamp;

    private Integer time;
    private Integer memory;
    private String problem;
    private String testCases;


    @Transient
    private String pb;
    @Transient
    private String ipt;
    @Transient
    private String opt;

    public Problem() {}

    public Problem(Long id, String title, String info, Long accepted, Long pushed, String author, Timestamp timestamp, Integer time, Integer memory, String problem, String testCases) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.accepted = accepted;
        this.pushed = pushed;
        this.author = author;
        this.timestamp = timestamp;
        this.time = time;
        this.memory = memory;
        this.problem = problem;
        this.testCases = testCases;

        JSONObject o = new JSONObject(problem);
        pb = o.getString("pb");
        ipt = o.getString("ipt");
        opt = o.getString("opt");
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public Long getAccepted() {
        return accepted;
    }

    public Long getPushed() {
        return pushed;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getMemory() {
        return memory;
    }

    public String getProblem() {
        return problem;
    }

    public String getTestCases() {
        return testCases;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getPb() {
        return pb;
    }

    public String getIpt() {
        return ipt;
    }

    public String getOpt() {
        return opt;
    }

    public String getPercentage() {
        return String.format(Locale.KOREA, "%.3f%%", accepted.longValue()==pushed.longValue() && pushed==0?0:(accepted.doubleValue()/pushed.doubleValue())*100);
    }

    public void push(boolean succeed) {
        pushed++;
        accepted += succeed ? 1 : 0;
    }
}
