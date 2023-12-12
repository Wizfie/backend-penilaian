package example.penilaian.model.penilaianYelyel;

import lombok.Data;

import java.sql.Date;

@Data
public class TeamScoreDTO {
    private String teamName;
    private Double averageScore;
    private double totalScore;
    private Date date;

}
