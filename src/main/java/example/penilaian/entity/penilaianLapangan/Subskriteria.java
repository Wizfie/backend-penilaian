package example.penilaian.entity.penilaianLapangan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subskriteria")
public class Subskriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kriteria_id")
    @JsonIgnore
    private Kriteria kriteria;

    @OneToMany(mappedBy = "subskriteria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Questions> questions;

    @JsonProperty("name")
    public String getSubskriteriaName() {
        return name;
    }

    @JsonProperty("questions")
    public List<Questions> getQuestions() {
        return questions;
    }
    // Getter dan setter lainnya
}
