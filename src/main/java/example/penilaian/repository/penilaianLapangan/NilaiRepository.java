package example.penilaian.repository.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.NilaiLapangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface NilaiRepository extends JpaRepository<NilaiLapangan, Integer> {

   List<NilaiLapangan> findByUsername(String username);
   List<NilaiLapangan> findByUsernameAndTimestampAndQuestionIdAndTeamName(
           String username,
           Date timestamp,
           int questionId,
           String teamName
   );

   void deleteByTimestampAndUsernameAndTeamName(Date timestamp, String username, String teamName);



}
