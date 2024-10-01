package net.yellowstrawberry.solveana.db.submit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubmitRepository extends JpaRepository<Submit, Long> {
    @Query("select s from Submit s order by s.id DESC ")
    Page<Submit> findAllByUser(Pageable pageable, String user);
    @Query("select s from Submit s where s.status='맞았습니다!' order by s.id DESC ")
    Page<Submit> findAllSucceedByUser(Pageable pageable, String user);
}
