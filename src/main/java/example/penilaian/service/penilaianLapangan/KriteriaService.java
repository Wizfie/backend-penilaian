package example.penilaian.service.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.Kriteria;
import example.penilaian.repository.penilaianLapangan.KriteriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KriteriaService {

    private static final Logger logger = LoggerFactory.getLogger(Kriteria.class);
    @Autowired
    private KriteriaRepository kriteriaRepository;


    public List<Kriteria> getAllKriteria() {

        try {
            return kriteriaRepository.findAll();
        } catch (Exception e) {
            logger.error("Failed to retrieve criteria: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve criteria: " + e.getMessage());
        }
    }


}
