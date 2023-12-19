package example.penilaian.service.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianYelyel.TeamScoreDTO;
import example.penilaian.repository.penilaianYelyel.PointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

}

