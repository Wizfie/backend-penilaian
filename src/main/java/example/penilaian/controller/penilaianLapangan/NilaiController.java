package example.penilaian.controller.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.NilaiLapangan;
import example.penilaian.model.penilaianLapangan.NilaiByUser;
import example.penilaian.service.penilaianLapangan.NilaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/nilai-getAll")
    public List<NilaiLapangan> getAllNilai() {
        return nilaiService.getAllNilai();
    }

//    @DeleteMapping("/{username}/{teamName}/{timestamp}")
//    public ResponseEntity<String> deleteNilai(
//            @PathVariable String username,
//            @PathVariable String teamName,
//            @PathVariable String timestamp) {
//
////        System.out.println("Username yang diterima: " + username);
////        System.out.println("Teams Name yang diterima: " + teamName);
////        System.out.println("Timestamp yang diterima: " + timestamp);
//
//        try {
////            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            java.sql.Date date = java.sql.Date.valueOf(timestamp);
//            nilaiService.deleteNilai(username, teamName, date);
//            return ResponseEntity.ok("Data dengan " + username +  " - " +  teamName +" - "+ timestamp + " ini Berhasil dihapus");
//        } catch (ServiceException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }


//    }
}
