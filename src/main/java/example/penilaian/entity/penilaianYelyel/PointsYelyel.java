package example.penilaian.entity.penilaianYelyel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point_yelyel")
public class PointsYelyel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointId;

    private String subscriteriaName;
    private Double point;

    private String teamName;
    private String username;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private String nip;

    private Double maxPoint;

}
