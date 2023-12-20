package example.penilaian.repository.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Questions , Long> {

    @Override
    Optional<Questions> findById(Long id);


    @Query("SELECT q.questionText FROM Questions q WHERE q.id = :questionId")
    String findQuestionTextById(int questionId);

}

