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
@Table(name = "subscriteria_yelyel")
public class SubscriteriaYelyel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriteriaId;

    private String subscriteriaName;
    private Double maxPoint;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private CriteriaYelyel criteriaYelyel;

}
