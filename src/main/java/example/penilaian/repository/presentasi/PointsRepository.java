package example.penilaian.repository.presentasi;

import example.penilaian.entity.presentasi.Items;
import example.penilaian.entity.presentasi.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRepository extends JpaRepository<Points , Integer> {

    List<Points> findByItems(Items item);
}
