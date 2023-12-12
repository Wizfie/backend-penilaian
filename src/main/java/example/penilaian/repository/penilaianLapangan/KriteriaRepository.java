package example.penilaian.repository.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.Kriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KriteriaRepository extends JpaRepository<Kriteria , Long> {

}
