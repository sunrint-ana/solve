package net.yellowstrawberry.solveana.db.submit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long problemId;
    private String user;
    private String status;
    private Integer memory;
    private Long time;
    private String source;
    private String lang;
    private Timestamp timestamp;

    public Submit() {}

    public Submit(Long problemId, String user, String status, String source, Timestamp timestamp) {
        this.problemId = problemId;
        this.user = user;
        this.status = status;
        this.memory = -1;
        this.time = -1L;
        this.source = source;
        this.lang = "Java 17";
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long problemId() {
        return problemId;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public Long getProblemId() {
        return problemId;
    }

    public Integer getMemory() {
        return memory;
    }

    public Long getTime() {
        return time;
    }

    public String getLang() {
        return lang;
    }

    public int getSourceLength() {
        return source.length();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
