package example.penilaian.repository.presentasi;

import example.penilaian.entity.presentasi.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items , Integer> {
}
