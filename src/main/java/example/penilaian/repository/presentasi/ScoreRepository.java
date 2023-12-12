package example.penilaian.repository.presentasi;

import example.penilaian.entity.presentasi.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score , Integer> {



    Optional<Score> findByTitleAndUsernameAndTeamNameAndCreatedAt(String title, String username, String teamName, Date createdAt);

    List<Score> findAllByNip(String nip);



}
