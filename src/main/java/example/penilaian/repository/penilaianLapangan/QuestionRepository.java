package example.penilaian.repository.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Questions , Long> {

    @Override
    Optional<Questions> findById(Long id);}
