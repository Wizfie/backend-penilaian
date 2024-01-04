package example.penilaian.model.penilaianLapangan;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NilaiResponseDTO {
    private int nilaiId;
    private double nilai;
    private String teamName;
    private String username;
    private int questionId;
    private Date timestamp;
    private String nip;
    private String questionText;
}
