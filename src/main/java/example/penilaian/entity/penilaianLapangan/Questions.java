package example.penilaian.entity.penilaianLapangan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String questionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subskriteria_id")
    @JsonIgnore
    private Subskriteria subskriteria;

    @ElementCollection
    private List<String> options;

    @JsonProperty("question")
    public String getQuestionText() {
        return questionText;
    }

    @JsonProperty("options")
    public List<String> getOptions() {
        return options;
    }
}
