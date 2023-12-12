package example.penilaian.controller.presentasi;

import example.penilaian.entity.presentasi.Score;
import example.penilaian.service.presentasi.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/save")
    public ResponseEntity<String> saveScore(@RequestBody List<Score> evaluations) {
        try {
            scoreService.saveScore(evaluations);
            return ResponseEntity.ok("Scores saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving scores");
        }
    }



    @GetMapping("/byNip/{nip}")
    public ResponseEntity<List<Score>> getAllScoresByNip(@PathVariable String nip) {
        List<Score> scores = scoreService.getScoreByNip(nip);
        if (scores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

}
