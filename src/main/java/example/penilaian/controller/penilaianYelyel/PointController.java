package example.penilaian.controller.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianYelyel.TeamScoreDTO;
import example.penilaian.service.penilaianYelyel.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("/savePoint")
    public ResponseEntity<String> savePoint(@RequestBody List<PointsYelyel> pointData){
        try {
            pointService.SavePoint(pointData);
            return ResponseEntity.ok("Data Saved");

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed save data" + e);
        }
    }

    @GetMapping("/average-score")
    public ResponseEntity<List<TeamScoreDTO>> getAverageScoresByTeamAndDate(
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate)
    {
        Date sqlStartDate = Date.valueOf(startDate);

        List<TeamScoreDTO> teamScores = pointService.getAverageAndTotalScoresByTeamAndDate(sqlStartDate);

        if (teamScores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(teamScores, HttpStatus.OK);
    }

    @GetMapping("/point")
    public List<PointsYelyel> getByUsername(@RequestParam String username) {
        return pointService.getByUsername(username);
    }


    @GetMapping("/point/all")
    public List<PointsYelyel> getALlPoint(){
        return pointService.getALlPoint();
    }
}
