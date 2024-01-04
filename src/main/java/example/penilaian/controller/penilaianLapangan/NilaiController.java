package example.penilaian.controller.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.NilaiLapangan;
import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianLapangan.NilaiByUser;
import example.penilaian.model.penilaianLapangan.NilaiResponseDTO;
import example.penilaian.service.penilaianLapangan.NilaiService;
import example.penilaian.specifications.YelyelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NilaiController {

    @Autowired
    private NilaiService nilaiService;

    @PostMapping("/save-nilai")
    public ResponseEntity<String> saveNilai(@RequestBody List<NilaiLapangan> nilaiLapanganData) {
        System.out.println("Received request with data: " + nilaiLapanganData);
        try {
            nilaiService.saveNilai(nilaiLapanganData);
            return ResponseEntity.ok("Data berhasil masuk");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal menyimpan data: " + e.getMessage());
        }
    }


    @PutMapping("/update-nilai")
    public ResponseEntity<List<NilaiLapangan>> updateNilai(@RequestBody List<NilaiLapangan> updatedNilaiListLapangan) {
        List<NilaiLapangan> updatedNilaiListResultLapangan = nilaiService.updateNilai(updatedNilaiListLapangan);

        if (!updatedNilaiListResultLapangan.isEmpty()) {
            return new ResponseEntity<>(updatedNilaiListResultLapangan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/nilai-list")
    public List<NilaiByUser> getCustomDataByUser(@RequestParam String username) {
        return nilaiService.getNilaiByUser(username);
    }

    @GetMapping("/nilai-lapangan")
    public ResponseEntity<List<NilaiResponseDTO>> getAllNilai() {
        try {
            List<NilaiResponseDTO> nilaiList = nilaiService.getAllNilai();
            return new ResponseEntity<>(nilaiList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/searchLapangan")
//    public ResponseEntity<Page<NilaiLapangan>> searchPresentasiSpecifications(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) Date startDate,
//            @RequestParam(required = false) Date endDate,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ){
//        PageRequest pageRequest = PageRequest.of(page,size);
//        Specification<NilaiLapangan> spec = nilaiService.s(keyword ,startDate ,endDate);
//        Page<NilaiLapangan> result = nilaiService.findAllScoresBySpecification(spec,pageRequest);
//        return ResponseEntity.ok(result);
//    }


}
