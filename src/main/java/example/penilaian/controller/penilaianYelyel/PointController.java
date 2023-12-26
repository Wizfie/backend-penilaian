package example.penilaian.controller.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.entity.presentasi.Score;
import example.penilaian.model.penilaianYelyel.TeamScoreDTO;
import example.penilaian.service.penilaianYelyel.PointService;
import example.penilaian.specifications.PresentasiSpecification;
import example.penilaian.specifications.YelyelSpecification;
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
            List<PointsYelyel> points = pointService.getByNipAndTeamNameAndCreateAt(nip, teamName, createdAt);
            if (points.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(points);

    }

    @GetMapping("/searchYelyel")
    public ResponseEntity<Page<PointsYelyel>> searchPresentasiSpecifications(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size);
        Specification<PointsYelyel> spec = YelyelSpecification.searchYelyel(keyword ,startDate ,endDate);
        Page<PointsYelyel> result = pointService.findAllScoresBySpecification(spec,pageRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/export-yelyel")
    public void exportExcelYelyel(
            @RequestParam("nip") String nip,
            @RequestParam("createdAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,
            @RequestParam("penilai") String penilai,
            HttpServletResponse response) {
        try {
            pointService.exportYelyel(nip, createdAt, penilai, response);
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
