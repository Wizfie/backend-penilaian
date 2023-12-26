package example.penilaian.repository.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
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
}

