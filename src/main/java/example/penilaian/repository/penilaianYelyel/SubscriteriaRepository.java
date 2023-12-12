package example.penilaian.repository.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.CriteriaYelyel;
import example.penilaian.entity.penilaianYelyel.SubscriteriaYelyel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriteriaRepository extends JpaRepository<SubscriteriaYelyel , Integer> {

    Optional<Object> findBysubscriteriaName(String subscriteriaName);
}
