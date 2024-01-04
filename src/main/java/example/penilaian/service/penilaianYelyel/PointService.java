package example.penilaian.service.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianYelyel.PointDataSummary;
import example.penilaian.repository.penilaianYelyel.PointRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    @Transactional
    public void SavePoint(List<PointsYelyel> PointData) {
        for (PointsYelyel pointsYelyel : PointData) {
            // Check if an entry exists for the same username, teamName, and createdAt
            List<PointsYelyel> existingPoints = pointRepository.findByUsernameAndTeamNameAndCreatedAt(
                    pointsYelyel.getUsername(),
                    pointsYelyel.getTeamName(),
                    pointsYelyel.getCreatedAt()
            );

            if (existingPoints.isEmpty()) {
                // If no entry exists for the same team and date, save a new entry
                pointRepository.save(pointsYelyel); // Save new entry
            } else {
                // Entry exists for the same team and date
                boolean entryFound = false;
                for (PointsYelyel existingPoint : existingPoints) {
                    if (existingPoint.getSubscriteriaName().equals(pointsYelyel.getSubscriteriaName())) {
                        entryFound = true;
                        if (!existingPoint.getPoint().equals(pointsYelyel.getPoint())) {
                            existingPoint.setPoint(pointsYelyel.getPoint());
                            pointRepository.save(existingPoint); // Update existing entry with new point
                        }
                        break;
                    }
                }

                if (!entryFound) {
                    // If subcriteria is not found for the same team and date, save a new entry
                    pointRepository.save(pointsYelyel); // Save new entry
                }
            }
        }
    }


    public List<PointsYelyel> getALlPoint(){
        return pointRepository.findAll();
    }

    public List<PointsYelyel> getByUsername(String username){
        return pointRepository.findByUsername(username);
    }

    public List<PointsYelyel> getByNip(String nip){
        return pointRepository.findByNip(nip);

    }

    public List<PointsYelyel> getByNipAndTeamNameAndCreateAt (String nip, String teamName , Date createdAt){

        List<PointsYelyel> points = pointRepository.findByNipAndTeamNameAndCreatedAt(nip,teamName,createdAt);

        if (points.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Data  tidak di temukan");
        }
        return points;

    }

    public void exportYelyel(String nip, LocalDate createdAt, String penilai, HttpServletResponse response) throws IOException {
        List<PointsYelyel> data = pointRepository.findByNipAndCreatedAt(nip, createdAt);

        if (data.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("Data not Found");
            return;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Set<String> teamNames = extractUniqueTeamNames(data);

        Row infoRow = sheet.createRow(0);
        infoRow.createCell(0).setCellValue("Name : " + penilai);

        Row infoRow2 = sheet.createRow(1);
        infoRow2.createCell(0).setCellValue("tanggal Data : " + createdAt.toString());

        Row infoRow3 = sheet.createRow(2);
        infoRow3.createCell(0).setCellValue("tanggal Cetak : " + LocalDate.now().toString());

        Row headerRow = sheet.createRow(3); // Mulai baris 4 untuk header
        headerRow.createCell(0).setCellValue("No");
        headerRow.createCell(1).setCellValue("Pertanyaan");

        int colNum = 2;
        Map<String, Integer> teamColumnMap = new HashMap<>();

        for (String team : teamNames) {
            headerRow.createCell(colNum).setCellValue(team);
            teamColumnMap.put(team, colNum++);
        }

        // Buat Map untuk menampung nilai-nilai PointsYelyel berdasarkan subscriteriaName
        Map<String, Map<String, Double>> subcriteriaPointsMap = new HashMap<>();

        for (PointsYelyel pointsYelyel : data) {
            String subcriteria = pointsYelyel.getSubscriteriaName();
            String teamName = pointsYelyel.getTeamName();
            Double points = pointsYelyel.getPoint();

            // Cek apakah subcriteria sudah ada di dalam Map
            if (!subcriteriaPointsMap.containsKey(subcriteria)) {
                subcriteriaPointsMap.put(subcriteria, new HashMap<>());
            }

            // Tambahkan poin ke tim yang sesuai di dalam subcriteria
            subcriteriaPointsMap.get(subcriteria).put(teamName, points);
        }

        int rowNum = 4; // Mulai baris 5 untuk data

        for (Map.Entry<String, Map<String, Double>> entry : subcriteriaPointsMap.entrySet()) {
            String subcriteria = entry.getKey();
            Map<String, Double> teamPointsMap = entry.getValue();

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 4);
            row.createCell(1).setCellValue(subcriteria);

            for (String team : teamNames) {
                int colIndex = teamColumnMap.get(team);
                if (teamPointsMap.containsKey(team)) {
                    Double points = teamPointsMap.get(team);
                    row.createCell(colIndex).setCellValue(points);
                }
            }
        }
            Row totalRow = sheet.createRow(rowNum++);
            totalRow.createCell(1).setCellValue("Total");

            for (String team : teamNames) {
                int colIndex = teamColumnMap.get(team);

                double totalPoints = 0.0;
                for (Map<String, Double> teamPointsMap : subcriteriaPointsMap.values()) {
                    if (teamPointsMap.containsKey(team)) {
                        totalPoints += teamPointsMap.get(team);
                    }
                }

                totalRow.createCell(colIndex).setCellValue(totalPoints);
            }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }



    private Set<String> extractUniqueTeamNames(List<PointsYelyel> data) {
        return data.stream().map(PointsYelyel::getTeamName).collect(Collectors.toSet());
    }

    public Page<PointsYelyel> findAllScoresBySpecification(Specification<PointsYelyel> spec, PageRequest pageRequest) {
        return pointRepository.findAll(spec, pageRequest);
    }

    public Page<PointDataSummary> getTotalPointsByUsernameOrTeamNameAndCreatedAtBetween(String usernameOrTeamName, Date startDate, Date endDate, Pageable pageable) {
        return pointRepository.getTotalPointsGroupedByUserAndTeamAndDate(usernameOrTeamName, startDate, endDate, pageable);
    }

}

