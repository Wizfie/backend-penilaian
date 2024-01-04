package example.penilaian.repository.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianYelyel.PointDataSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PointRepository extends JpaRepository <PointsYelyel , Integer> {

    List<PointsYelyel> findByUsernameAndTeamNameAndCreatedAt(String username, String teamName, Date createdAt);


    List<PointsYelyel> findByUsername(String username);

    List<PointsYelyel> findByNip(String nip);

    List<PointsYelyel> findByNipAndTeamNameAndCreatedAt(String nip, String teamName, Date createdAt);

    List<PointsYelyel> findByNipAndCreatedAt(String nip, LocalDate createdAt);

    Page<PointsYelyel> findAll(Specification<PointsYelyel> spec, Pageable pageRequest);



    @Query("SELECT new example.penilaian.model.penilaianYelyel.PointDataSummary(pd.username, pd.nip, pd.createdAt, CAST(SUM(pd.point) AS double), pd.teamName) " +
            "FROM PointsYelyel pd " +
            "WHERE (:usernameOrTeamName is null OR pd.username LIKE %:usernameOrTeamName% OR pd.teamName LIKE %:usernameOrTeamName%) " +
            "AND (:startDate is null OR pd.createdAt >= :startDate) " +
            "AND (:endDate is null OR pd.createdAt <= :endDate) " +
            "GROUP BY pd.username, pd.teamName, pd.createdAt, pd.nip")
    Page<PointDataSummary> getTotalPointsGroupedByUserAndTeamAndDate(
            @Param("usernameOrTeamName") String usernameOrTeamName,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);
}




