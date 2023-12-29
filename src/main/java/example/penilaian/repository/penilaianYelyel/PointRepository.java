package example.penilaian.repository.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT p.teamName, p.username, p.createdAt, SUM(p.point) " +
            "FROM PointsYelyel p " +
            "WHERE (:keyword IS NULL OR lower(p.username) LIKE lower(concat('%', :keyword, '%'))) " +
            "AND (:startDate IS NULL OR p.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR p.createdAt <= :endDate) " +
            "GROUP BY p.teamName, p.username, p.createdAt")
    Page<Object[]> findTotalScoresGroupedWithSpec(
            String keyword,
            Date startDate,
            Date endDate,
            Pageable pageable);
}

