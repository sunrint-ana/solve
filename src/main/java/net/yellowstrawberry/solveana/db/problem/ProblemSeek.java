package net.yellowstrawberry.solveana.db.problem;

import java.util.Locale;

public interface ProblemSeek {
    Long getId();
    String getTitle();
    String getInfo();
    Long getAccepted();
    Long getPushed();
    String getAuthor();
    default String getPercentage() {
        return String.format(Locale.KOREA, "%.3f%%", getAccepted().longValue()==getPushed().longValue() && getPushed()==0?0:(getAccepted().doubleValue()/getPushed().doubleValue())*100);
    }
}
