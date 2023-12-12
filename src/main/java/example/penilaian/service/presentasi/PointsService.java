package example.penilaian.service.presentasi;

import example.penilaian.entity.presentasi.Points;
import example.penilaian.repository.presentasi.PointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsService {

    @Autowired
    private PointsRepository pointsRepository;

    public List<Points> getAll(){
        return pointsRepository.findAll();
    }
}
