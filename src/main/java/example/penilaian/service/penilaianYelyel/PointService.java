package example.penilaian.service.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianYelyel.TeamScoreDTO;
import example.penilaian.repository.penilaianYelyel.PointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Date testDate = Date.valueOf("2023-11-23");

        for (PointsYelyel pointsYelyel : PointData) {
            if (pointsYelyel.getCreateAt() == null){
                pointsYelyel.setCreateAt(currentDate);
            }
            List<PointsYelyel> existingPoints = pointRepository.findByUsernameAndTeamNameAndCreateAt(
                    pointsYelyel.getUsername(),
                    pointsYelyel.getTeamName(),
                    pointsYelyel.getCreateAt()
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





    public List<TeamScoreDTO> getAverageAndTotalScoresByTeamAndDate(Date startDate) {
        List<Object[]> resultList = pointRepository.getAverageScoresAndTotalScoresByTeamNameAndCreateAt(startDate);

        List<TeamScoreDTO> teamScores = new ArrayList<>();

        for (Object[] row : resultList) {
            String teamName = (String) row[0]; // Assuming team_name is in index 0
            Double average = ((Number) row[1]).doubleValue(); // Assuming average is in index 1
            Integer total = ((Number) row[2]).intValue(); // Assuming total is in index 2

            TeamScoreDTO teamScore = new TeamScoreDTO();
            teamScore.setTeamName(teamName);
            teamScore.setAverageScore(average);
            teamScore.setTotalScore(total);
            teamScore.setDate(startDate); // Set the start date from parameter

            teamScores.add(teamScore);
        }

        return teamScores;
    }











    public List<PointsYelyel> getALlPoint(){
        return pointRepository.findAll();
    }

    public List<PointsYelyel> getByUsername(String username){
        return pointRepository.findByUsername(username);
    }
}

