package example.penilaian.controller.presentasi;

import example.penilaian.entity.presentasi.Score;
import example.penilaian.repository.presentasi.ScoreRepository;
import example.penilaian.service.presentasi.ScoreService;
import example.penilaian.specifications.PresentasiSpecification;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    @Autowired
    private ScoreRepository scoreRepository;


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

@GetMapping("/searchPresentasi")
    public ResponseEntity<Page<Score>> searchPresentasiSpecifications(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size);
        Specification<Score> spec = PresentasiSpecification.searchPresentasi(keyword ,startDate ,endDate);
        Page<Score> result = scoreService.findAllScoresBySpecification(spec,pageRequest);
        return ResponseEntity.ok(result);
    }




    @GetMapping("/export")
    public void exportExcelPresentasi(
            @RequestParam("nip") String nip,
            @RequestParam("createdAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,
            @RequestParam("penilai") String penilai,
            HttpServletResponse response) {
        try {
            scoreService.exportPresentasi(nip, createdAt, penilai, response);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().println("Failed to export data to Excel: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
