package net.yellowstrawberry.solveana.db.problem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface ProblemInfo {
    Long getId();
    String getTitle();
    Long getAccepted();
    Long getPushed();
    Integer getTime();
    Integer getMemory();
    String getProblem();
    default String getMProblem() {
        return new JSONObject(getProblem()).getString("pb");
    }
    default String getInputs() {
        return new JSONObject(getProblem()).getString("ipt");
    }
    default String getOutputs() {
        return new JSONObject(getProblem()).getString("opt");
    }
    default List<Example> getExamples() {
        JSONObject jo = new JSONObject(getProblem());
        if(!jo.has("examples")) return List.of();
        List<Example> e = new ArrayList<>();
        int i = 0;
        for(Object o : jo.getJSONArray("examples")) {
            JSONObject j = (JSONObject) o;
            e.add(new Example(++i, j.getString("ipt"), j.getString("opt")));
        }
        return e;
    }
    default String getPercentage() {
        return String.format(Locale.KOREA, "%.3f%%", getAccepted().longValue()==getPushed().longValue() && getPushed()==0?0:(getAccepted().doubleValue()/getPushed().doubleValue())*100);
    }
}
