package example.penilaian.controller.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.Kriteria;
import example.penilaian.service.penilaianLapangan.KriteriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kriteria")
public class KriteriaController {

    private static final Logger logger = LoggerFactory.getLogger(Kriteria.class);

    @Autowired
    private KriteriaService kriteriaService;


    @GetMapping("/all")
    public List<Kriteria> getAllKriteria() {
        return kriteriaService.getAllKriteria();
    }
}
