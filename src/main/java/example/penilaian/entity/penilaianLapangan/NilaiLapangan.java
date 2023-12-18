    package example.penilaian.entity.penilaianLapangan;


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
    @Table(name = "nilai_lapangan" )
    public class NilaiLapangan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int nilaiId;

        private double nilai;
        private String teamName;
        private String username;
        private int questionId;

        @Column(name = "timestamp")
        private Date timestamp;

        private String nip;



    }

