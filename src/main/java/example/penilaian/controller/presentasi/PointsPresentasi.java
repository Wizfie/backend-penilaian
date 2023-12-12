package example.penilaian.controller.presentasi;

import example.penilaian.entity.presentasi.Points;
import example.penilaian.service.presentasi.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PointsPresentasi {

    @Autowired
    private PointsService pointsService;


    @GetMapping("/points-presentasi")
    public List<Points> getAll(){
        return pointsService.getAll();
    }
}
