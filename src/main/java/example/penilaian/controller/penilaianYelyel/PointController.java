package example.penilaian.controller.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianYelyel.PointDataSummary;
import example.penilaian.service.penilaianYelyel.PointService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            @PathVariable String  createdAt
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedDate;
        try {
            parsedDate = formatter.parse(createdAt);
        } catch (ParseException e) {
            // Tangani kesalahan jika konversi gagal
            return ResponseEntity.badRequest().build();
        }

        // Konversi java.util.Date ke java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

        List<PointsYelyel> points = pointService.getByNipAndTeamNameAndCreateAt(nip, teamName, sqlDate);
        if (points.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(points);
    }
        @GetMapping("/pointsYelyel")
        public Page<PointDataSummary> getTotalPointsByUsernameOrTeamNameAndCreatedAtBetween(
                @RequestParam(required = false) String usernameOrTeamName,
                @RequestParam(required = false) Date startDate,
                @RequestParam(required = false) Date endDate,
                @RequestParam(name = "page", defaultValue = "0") int page,
                @RequestParam(name = "size", defaultValue = "10") int size,
                @PageableDefault(page = 0 ,size = 10) Pageable pageable) {
            Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

            return pointService.getTotalPointsByUsernameOrTeamNameAndCreatedAtBetween(usernameOrTeamName, startDate, endDate, pageable);
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
