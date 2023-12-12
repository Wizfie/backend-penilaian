package example.penilaian.repository.penilaianYelyel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamsYelyelRepository extends JpaRepository <example.penilaian.entity.penilaianYelyel.TeamsYelyel, Integer> {
    Optional<example.penilaian.entity.penilaianYelyel.TeamsYelyel> findByTeamName(String teamName);
}
