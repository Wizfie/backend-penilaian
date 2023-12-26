package example.penilaian.repository.presentasi;

import example.penilaian.entity.presentasi.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score , Integer> {



    Optional<Score> findByTitleAndUsernameAndTeamNameAndCreatedAt(String title, String username, String teamName, Date createdAt);

    List<Score> findAllByNip(String nip);


    List<Score> findByNipAndCreatedAt(String nip, LocalDate createdAt);

    Page<Score> findAll(Specification<Score> spec, Pageable pageRequest);


}

