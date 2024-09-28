package net.yellowstrawberry.solveana.db.problem;

import java.util.Locale;

public interface ProblemInfo {
    Long getId();
    String getTitle();
    Long getAccepted();
    Long getPushed();
    Integer getTime();
    Integer getMemory();
    String getProblem();
    default String getPercentage() {
        return String.format(Locale.KOREA, "%.3f%%", getAccepted().longValue()==getPushed().longValue() && getPushed()==0?0:getAccepted().doubleValue()/getPushed().doubleValue());
    }
}
