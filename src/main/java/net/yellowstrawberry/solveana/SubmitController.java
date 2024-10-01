package net.yellowstrawberry.solveana;

import net.yellowstrawberry.solveana.db.problem.ProblemInfo;
import net.yellowstrawberry.solveana.db.problem.ProblemRepository;
import net.yellowstrawberry.solveana.db.problem.ProblemSeek;
import net.yellowstrawberry.solveana.db.submit.Submit;
import net.yellowstrawberry.solveana.db.submit.SubmitRepository;
import net.yellowstrawberry.solveana.solve.SourceCode;
import net.yellowstrawberry.solveana.solve.TestManager;
import net.yellowstrawberry.solveana.solve.test.Tester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/submit")
public class SubmitController {
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SubmitRepository submitRepository;

    private static final ExecutorService executor = Executors.newWorkStealingPool(2);


    @GetMapping(value = "/{id}")
    public String submitM(@PathVariable long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) model.addAttribute("given_name", principal.getAttribute("given_name"));
        ProblemInfo info = problemRepository.findByIdIs(id).orElse(null);
        if(info!=null) {
            model.addAttribute("problem", info);
            model.addAttribute("sourceCode", new SourceCode());
        }
        return "submit";
    }

    @PostMapping(value = "/{id}")
    public String submit(@PathVariable long id, Model model, @AuthenticationPrincipal OAuth2User principal, @ModelAttribute SourceCode code) throws InterruptedException {
        if (principal == null) return "redirect:/";
        model.addAttribute("given_name", principal.getAttribute("given_name"));
        problemRepository.findByIdGetTestCases(id).ifPresent(problem -> executor.submit(() -> TestManager.schedule(id, principal.getAttribute("sub"), problem, code.getSourceCode())));
        Thread.sleep(100);
        return "redirect:/submit/results";
    }

    @GetMapping(value = "/results")
    public String submit(Model model, @AuthenticationPrincipal OAuth2User principal, @RequestParam(name = "p",defaultValue = "0") Integer page) {
        if (principal == null) return "redirect:/";
        model.addAttribute("given_name", principal.getAttribute("given_name"));
        PageRequest pageable = PageRequest.of(page, 30);
        Page<Submit> submitPage = submitRepository.findAllByUser(pageable, principal.getAttribute("sub"));
        model.addAttribute("submits", submitPage.getContent());
        return "results";
    }
}
