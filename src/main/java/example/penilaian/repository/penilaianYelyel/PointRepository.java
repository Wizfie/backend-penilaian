package example.penilaian.repository.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PointRepository extends JpaRepository <PointsYelyel , Integer> {

    List<PointsYelyel> findByUsernameAndTeamNameAndCreateAt(String username, String teamName, Date createAt);

//    @Query("SELECT p.teamName, AVG(p.point) FROM PointsYelyel p WHERE p.createAt BETWEEN :startDate AND :endDate GROUP BY p.teamName")
//    List<Object[]> getAverageScoresByTeamNameAndCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT p.teamName, AVG(p.point), SUM(p.point) FROM PointsYelyel p WHERE p.createAt = :startDate  GROUP BY p.teamName")
    List<Object[]> getAverageScoresAndTotalScoresByTeamNameAndCreateAt(@Param("startDate") Date startDate);

    List<PointsYelyel> findByUsername(String username);

}

