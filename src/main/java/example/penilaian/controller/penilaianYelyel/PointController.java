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


    @GetMapping("/point")
    public List<PointsYelyel> getByNip(@RequestParam String nip) {
        return pointService.getByNip(nip);
    }


    @GetMapping("/point/all")
    public List<PointsYelyel> getALlPoint(){
        return pointService.getALlPoint();
    }


    @GetMapping("/details-yelyel/{nip}/{teamName}/{createdAt}")
    public ResponseEntity<List<PointsYelyel>> getDetail(
            @PathVariable String nip,
            @PathVariable String teamName,
            @PathVariable Date createdAt
    ) {
            List<PointsYelyel> points = pointService.getByNipAndTeamNameAndCreateAt(nip, teamName, createdAt    );
            if (points.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(points);

    }
}
