package net.yellowstrawberry.solveana.solve;

import net.yellowstrawberry.solveana.db.problem.ProblemRepository;
import net.yellowstrawberry.solveana.db.submit.Submit;
import net.yellowstrawberry.solveana.db.submit.SubmitRepository;
import net.yellowstrawberry.solveana.solve.io.IOManager;
import net.yellowstrawberry.solveana.solve.io.SolveAnAInputStream;
import net.yellowstrawberry.solveana.solve.io.SolveAnAOutputStream;
import net.yellowstrawberry.solveana.solve.test.TestCase;
import net.yellowstrawberry.solveana.solve.test.TestCases;
import net.yellowstrawberry.solveana.solve.test.Tester;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestManager {

    private static ProblemRepository problemRepository;

    private static SubmitRepository submitRepository;

    public TestManager(ProblemRepository problemRepository, SubmitRepository submitRepository) {
        TestManager.problemRepository = problemRepository;
        TestManager.submitRepository = submitRepository;

        System.setOut(SolveAnAOutputStream.o);
        System.setIn(SolveAnAInputStream.i);
    }

    public static void schedule(Long problemId, String user, String testCases, String sourceCode) {
        Submit submit = submitRepository.saveAndFlush(new Submit(problemId, user, "준비중", sourceCode, new Timestamp(System.currentTimeMillis())));
        String id = "SA_"+submit.getId();
        Tester tester = new Tester(id);
        if(!tester.compile(sourceCode.replaceFirst("(public)*( *)+class( *)+Main", "public class "+id))) { submit.setStatus("컴파일 에러"); submitRepository.save(submit); problemRepository.findById(problemId).ifPresent(problem -> {
            problem.push(false);
            problemRepository.save(problem);
        }); return; }

        submit.setStatus("채점중");
        submitRepository.save(submit);
        IOManager.registerTestCase(id, new TestCases(getTestCases(testCases)));
        String code = null;
        while (IOManager.passTestCase(id)&&(code = tester.run(1)) == null) {
            if(!IOManager.checkTestCase(id)) {
                submit.setStatus("틀렸습니다!");
                submitRepository.save(submit);
                IOManager.unregisterTestCase(id);
                problemRepository.findById(problemId).ifPresent(problem -> {
                    problem.push(false);
                    problemRepository.save(problem);
                });
                return;
            }
        }
        IOManager.unregisterTestCase(id);
        submit.setTime(tester.getAverageTime());
        submit.setStatus(code==null?"맞았습니다!":code);
        submitRepository.save(submit);

        String finalCode = code;
        problemRepository.findById(problemId).ifPresent(problem -> {
            problem.push(finalCode == null);
            problemRepository.save(problem);
        });
    }

    private static TestCase[] getTestCases(String testCases) {
        List<TestCase> testCaseList = new ArrayList<>();
        JSONObject object = new JSONObject(testCases);
        for (Object o : object.getJSONArray("testcase")) {
            JSONObject testCase = (JSONObject) o;
            testCaseList.add(new TestCase(testCase.getString("ipt"), testCase.getString("opt")));
        }

        return testCaseList.toArray(new TestCase[]{});
    }
}
