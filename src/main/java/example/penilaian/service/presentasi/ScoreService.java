package example.penilaian.service.presentasi;

import example.penilaian.entity.presentasi.Score;
import example.penilaian.repository.presentasi.ScoreRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<Score> getAll(){
        try {
            return scoreRepository.findAll();
        } catch (Exception e){
            logger.info("Fail get All score Presentasi");
            throw new RuntimeException("Fail getAll Presentasi" + e.getMessage());
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

    public List<Score> getScoreByNipAndCreatedAt(String nip , String createdAt){
        try {
            return getScoreByNipAndCreatedAt(nip , createdAt);
        } catch (Exception e ){
            logger.info("Fail get by Nip and Created At" + e.getMessage());
            throw new RuntimeException("Fail get by Nip And CreatedAt " +  e.getMessage());
        }

    }

    public void exportPresentasi(String nip, LocalDate createdAt, String penilai, HttpServletResponse response) throws IOException {
        List<Score> data = scoreRepository.findByNipAndCreatedAt(nip, createdAt);

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

        for (Score score : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 4);
            row.createCell(1).setCellValue(score.getTitle());

            String teamName = score.getTeamName();
            Double scores = score.getScore();
            int colIndex = teamColumnMap.get(teamName);
            row.createCell(colIndex).setCellValue(scores);
        }

        Row totalRow = sheet.createRow(rowNum);

        totalRow.createCell(1).setCellValue("Total");

// Loop melalui setiap tim untuk menghitung total nilai
        for (String team : teamNames) {
            int colIndex = teamColumnMap.get(team);
            double totalScore = data.stream()
                    .filter(score -> team.equals(score.getTeamName()))
                    .mapToDouble(Score::getScore)
                    .sum();
            totalRow.createCell(colIndex).setCellValue(totalScore);
        }



        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }



    private Set<String> extractUniqueTeamNames(List<Score> data) {
        return data.stream().map(Score::getTeamName).collect(Collectors.toSet());
    }

    public Page<Score> findAllScoresBySpecification(Specification<Score> spec, Pageable pageable) {
        return scoreRepository.findAll(spec, pageable);


    }
}