package example.penilaian.service.penilaianLapangan;

import example.penilaian.entity.penilaianLapangan.NilaiLapangan;
import example.penilaian.entity.penilaianLapangan.Questions;
import example.penilaian.entity.penilaianYelyel.PointsYelyel;
import example.penilaian.model.penilaianLapangan.NilaiByUser;
import example.penilaian.model.penilaianLapangan.NilaiResponseDTO;
import example.penilaian.repository.penilaianLapangan.NilaiRepository;
import example.penilaian.repository.penilaianLapangan.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NilaiService {


    @Autowired
    private NilaiRepository nilaiRepository;

    @Autowired
    private QuestionRepository questionRepository;



    @Transactional
    public void saveNilai(List<NilaiLapangan> nilaiLapanganData) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = sdf.format(new java.util.Date());
        Date currentDate = Date.valueOf(formattedDate);
//        Date testDate = Date.valueOf("2023-12-20");

        for (NilaiLapangan nilaiLapangan : nilaiLapanganData) {
            // Perbarui nilaiLapangan timestamp hanya jika belum diisi
            if (nilaiLapangan.getTimestamp() == null) {
                nilaiLapangan.setTimestamp(currentDate);
//                nilaiLapangan.setTimestamp(testDate);

            double nilaiAsDouble;
            try {
                nilaiAsDouble = Double.parseDouble(String.valueOf(nilaiLapangan.getNilai()));
                nilaiLapangan.setNilai(nilaiAsDouble);
            } catch (NumberFormatException e) {
                // Tangani kesalahan jika nilai tidak dapat diubah menjadi Double
                e.printStackTrace();
                // Atau lakukan penanganan kesalahan lainnya sesuai kebutuhan Anda
            }
            }


            // Cek apakah sudah ada data dengan username, timestamp, dan questionId yang sama
            List<NilaiLapangan> existingNilaiListLapangan = nilaiRepository
                    .findByUsernameAndTimestampAndQuestionIdAndTeamName(
                            nilaiLapangan.getUsername(),
                            nilaiLapangan.getTimestamp(),
                            nilaiLapangan.getQuestionId(),
                            nilaiLapangan.getTeamName()
                    );

            // Jika data sudah ada, update nilaiLapangan sesuai kebutuhan
            if (!existingNilaiListLapangan.isEmpty()) {
                for (NilaiLapangan existingNilaiLapangan : existingNilaiListLapangan) {
                    // Hanya update jika teamName sama
                    if (existingNilaiLapangan.getTeamName().equals(nilaiLapangan.getTeamName())) {
                        existingNilaiLapangan.setNilai(nilaiLapangan.getNilai());
                        // Update nilaiLapangan-nilaiLapangan lainnya sesuai kebutuhan
                    }
                }
                // Simpan kembali ke repository
                nilaiRepository.saveAll(existingNilaiListLapangan);
            } else {
                // Jika tidak ada data dengan kombinasi yang sama, langsung simpan data baru
                nilaiRepository.save(nilaiLapangan);
            }
        }
    }




    public List<NilaiByUser> getNilaiByUser(String username) {
        List<NilaiLapangan> nilaiLapanganList = nilaiRepository.findByUsername(username);
        List<NilaiByUser> nilaiByUserList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (NilaiLapangan nilaiLapangan : nilaiLapanganList) {
            Date timestamp = nilaiLapangan.getTimestamp();

            // Lepaskan error jika timestamp null
            if (timestamp == null) {
                throw new IllegalStateException("Timestamp cannot be null for nilaiId: " + nilaiLapangan.getNilaiId());
            }

            String formattedTimestamp = sdf.format(timestamp);

            Questions question = questionRepository.findById((long) nilaiLapangan.getQuestionId()).orElse(null);

            if (question != null) {
                double maxValue = 0.0; // Inisialisasi nilai maksimum

                // Ambil nilai maksimum dari array options pada entitas Question
                for (String option : question.getOptions()) {
                    double optionValue = Double.parseDouble(option.replace(',', '.')); // Ubah tanda koma menjadi titik
                    if (optionValue > maxValue) {
                        maxValue = optionValue;
                    }
                }


                NilaiByUser nilaiByUser = NilaiByUser.builder()
                        .nilaiId(nilaiLapangan.getNilaiId())
                        .username(username)
                        .teamName(nilaiLapangan.getTeamName())
                        .nilai(nilaiLapangan.getNilai())
                        .questionId(nilaiLapangan.getQuestionId())
                        .questionText(question.getQuestionText())
                        .formattedTimestamp(formattedTimestamp)
                        .maxValue(maxValue)
                        .build();
                nilaiByUserList.add(nilaiByUser);
            }

        }
        return nilaiByUserList;
    }

    public List<NilaiLapangan> updateNilai(List<NilaiLapangan> updatedNilaiListLapangan) {
        List<NilaiLapangan> updatedNilaiListResultLapangan = new ArrayList<>();

        for (NilaiLapangan updatedNilaiLapangan : updatedNilaiListLapangan) {
            Optional<NilaiLapangan> existingNilai = nilaiRepository.findById(updatedNilaiLapangan.getNilaiId());
            if (existingNilai.isPresent()) {
                NilaiLapangan existingNilaiLapangan = existingNilai.get();
                double maxValue = 0.0;

                // Dapatkan nilai maksimum dari pertanyaan terkait
                Questions question = questionRepository.findById((long) existingNilaiLapangan.getQuestionId()).orElse(null);
                if (question != null) {
                    for (String option : question.getOptions()) {
                        double optionValue = Double.parseDouble(option.replace(',', '.'));
                        if (optionValue > maxValue) {
                            maxValue = optionValue;
                        }
                    }
                }

                // Perbarui nilai dengan penanganan kesalahan jika nilai melebihi maxValue
                if (updatedNilaiLapangan.getNilai() > maxValue) {
                    throw new IllegalArgumentException("Nilai tidak dapat melebihi maksimum!");
                    // Anda dapat menambahkan penanganan lain yang sesuai dengan kebutuhan aplikasi Anda
                } else {
                    existingNilaiLapangan.setNilai(updatedNilaiLapangan.getNilai());
                    updatedNilaiListResultLapangan.add(nilaiRepository.save(existingNilaiLapangan));
                }
            }
        }

        return updatedNilaiListResultLapangan;
    }

    public List<NilaiResponseDTO> getAllNilai () {
        List<NilaiResponseDTO> responseDTOList;
        try {
            List<NilaiLapangan> nilaiLapanganList = nilaiRepository.findAll();
            responseDTOList = new ArrayList<>();

            for (NilaiLapangan nilaiLapangan : nilaiLapanganList) {
                String questionText = questionRepository.findQuestionTextById(nilaiLapangan.getQuestionId());

                NilaiResponseDTO responseDTO = new NilaiResponseDTO(
                        nilaiLapangan.getNilaiId(),
                        nilaiLapangan.getNilai(),
                        nilaiLapangan.getTeamName(),
                        nilaiLapangan.getUsername(),
                        nilaiLapangan.getQuestionId(),
                        nilaiLapangan.getTimestamp(),
                        nilaiLapangan.getNip(),
                        questionText
                );

                 responseDTOList.add(responseDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("Fail fetch AllNilai");
        }
        return responseDTOList;
    }

    public Page<NilaiLapangan> findAllScoresBySpecification(Specification<PointsYelyel> spec, PageRequest pageRequest) {
        return nilaiRepository.findAll(spec, pageRequest);
    }





}



