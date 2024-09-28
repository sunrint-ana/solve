package net.yellowstrawberry.solveana;

import net.yellowstrawberry.solveana.db.problem.ProblemInfo;
import net.yellowstrawberry.solveana.db.problem.ProblemRepository;
import net.yellowstrawberry.solveana.db.problem.ProblemSeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProblemController {
    @Autowired
    private ProblemRepository problemRepository;

    @GetMapping("/problemset")
    public String allProblems(@RequestParam(name = "p",defaultValue = "0") Integer page, Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) model.addAttribute("given_name", principal.getAttribute("given_name"));

        PageRequest pageable = PageRequest.of(page, 30);
        Page<ProblemSeek> pageProblems = problemRepository.findAllBy(pageable);

        model.addAttribute("problems", pageProblems.getContent());

        return "problemset";
    }

    @GetMapping("/problem/{id}")
    public String problem(@PathVariable long id, Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) model.addAttribute("given_name", principal.getAttribute("given_name"));
        ProblemInfo info = problemRepository.findByIdIs(id).orElse(null);
        if(info!=null) {
            model.addAttribute("problem", info);
        }
        return "problem";
    }
}
