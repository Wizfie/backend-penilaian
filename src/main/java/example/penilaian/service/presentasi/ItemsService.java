package example.penilaian.service.presentasi;

import example.penilaian.entity.presentasi.Items;
import example.penilaian.entity.presentasi.Points;
import example.penilaian.repository.presentasi.ItemsRepository;
import example.penilaian.repository.presentasi.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private PointsRepository pointsRepository;

    public List<Items> getAllItemsWithPoints() {
        List<Items> itemsList = itemsRepository.findAll();

        for (Items item : itemsList) {
            List<Points> pointsList = pointsRepository.findByItems(item);
            item.setPoints(pointsList);
        }

        return itemsList;
    }
}

