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
@Table(name = "kriteria")
public class Kriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "kriteria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Subskriteria> subskriteriaList;

    @JsonProperty("name")
    public String getKriteriaName() {
        return name;
    }

    @JsonProperty("subCriteria")
    public List<Subskriteria> getSubskriteriaList() {
        return subskriteriaList;
    }
    // Getter dan setter lainnya
}
