package example.penilaian.service.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.entity.presentasi.Score;
import example.penilaian.model.penilaianYelyel.TeamScoreDTO;
import example.penilaian.repository.penilaianYelyel.PointRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Service
public class PointService {

    @Autowired
    private PointRepository pointRepository;

    @Transactional
    public void SavePoint(List<PointsYelyel> PointData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new java.util.Date());
        java.sql.Date currentDate = java.sql.Date.valueOf(formattedDate);
        Date testDate = Date.valueOf("2023-12-16");

        for (PointsYelyel pointsYelyel : PointData) {
            if (pointsYelyel.getCreatedAt() == null){
                pointsYelyel.setCreatedAt(currentDate);
            }
            List<PointsYelyel> existingPoints = pointRepository.findByUsernameAndTeamNameAndCreatedAt(
                    pointsYelyel.getUsername(),
                    pointsYelyel.getTeamName(),
                    pointsYelyel.getCreatedAt()
            );

            boolean found = false;
            for (PointsYelyel existingPoint : existingPoints) {
                if (existingPoint.getSubscriteriaName().equals(pointsYelyel.getSubscriteriaName())) {
                    if (!existingPoint.getPoint().equals(pointsYelyel.getPoint())) {
                        existingPoint.setPoint(pointsYelyel.getPoint());
                        pointRepository.save(existingPoint); // Menyimpan perubahan pada data yang sudah ada
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                pointRepository.save(pointsYelyel); // Jika tidak ada entri yang ditemukan, maka menyimpan entri baru
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

        int rowNum = 4; // Mulai baris 5 untuk data

        for (PointsYelyel pointsYelyel : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 4);
            row.createCell(1).setCellValue(pointsYelyel.getSubscriteriaName());

            String teamName = pointsYelyel.getTeamName();
            Double pointsYelyels = pointsYelyel.getPoint();
            int colIndex = teamColumnMap.get(teamName);
            row.createCell(colIndex).setCellValue(pointsYelyels);
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
}

