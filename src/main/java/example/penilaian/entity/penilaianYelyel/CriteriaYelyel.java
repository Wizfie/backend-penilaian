package example.penilaian.entity.penilaianYelyel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "criteria_yelyel")

public class CriteriaYelyel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  CriteriaId;

    private String criteriaName;

}
