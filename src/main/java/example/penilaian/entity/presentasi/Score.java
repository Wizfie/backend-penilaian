    package example.penilaian.entity.presentasi;

    import example.penilaian.entity.Teams;
    import example.penilaian.entity.Users;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.data.annotation.CreatedDate;

    import java.sql.Date;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "score_presentasi")
    public class Score {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String title;

        private Double score;

        private String username;

        private String maxScore;

        private  String  nip;

        private String teamName;



        @Temporal(TemporalType.DATE)
        private Date createdAt;

    }
