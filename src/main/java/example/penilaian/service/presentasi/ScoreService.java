package example.penilaian.service.presentasi;

import example.penilaian.entity.presentasi.Score;
import example.penilaian.repository.presentasi.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);


    @Transactional
    public void saveScore(List<Score> evaluations) {
        try {
            java.util.Date currentDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String testDate = "2023-12-05";

            for (Score eval : evaluations) {

                // Cek dan set nilai createdAt jika null
                if (eval.getCreatedAt() == null) {
                    String formattedDate = sdf.format(currentDate);
                    eval.setCreatedAt(Date.valueOf(formattedDate));
                }

                if (eval.getScore() == null ) {
                    throw new IllegalArgumentException("Score cannot be empty or null.");
                }

                // Validasi tanggal createdAt
                if (eval.getCreatedAt().after(new Date(currentDate.getTime()))) {
                    throw new IllegalArgumentException("Invalid createdAt date for " + eval.getTitle() +
                            ". Please enter a valid createdAt date.");
                }

                Optional<Score> existingScore = scoreRepository.findByTitleAndUsernameAndTeamNameAndCreatedAt(
                        eval.getTitle(), eval.getUsername(), eval.getTeamName(), eval.getCreatedAt());

                if (existingScore.isPresent()) {
                    Score existing = existingScore.get();
                    existing.setScore(eval.getScore());
                    scoreRepository.save(existing);
                } else {
                    scoreRepository.save(eval);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving scores");
        }
    }


    public List<Score> getScoreByNip(String nip) {
        try {
            return scoreRepository.findAllByNip(nip);

        } catch (Exception e) {
            logger.info("FAIL get by NIP " + e.getMessage());
            throw  new RuntimeException("Fail get data by nip " + e.getMessage());
        }
    }



}