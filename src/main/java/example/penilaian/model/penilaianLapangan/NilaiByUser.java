package example.penilaian.model.penilaianLapangan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NilaiByUser {

    private int nilaiId;
    private String username;
    private String teamName;
    private double nilai;
    private String questionText;
    private int questionId;
    private double maxValue;
    private String formattedTimestamp;

}
