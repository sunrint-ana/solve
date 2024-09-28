package net.yellowstrawberry.solveana.db.problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Page<ProblemSeek> findAllBy(Pageable pageable);
    Optional<ProblemInfo> findByIdIs(Long id);
    List<ProblemSeek> findByTitle(String title);
    @Query("select p.testCases from Problem p")
    String findByIdGetTestCases(Long id);
}
