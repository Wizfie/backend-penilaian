package example.penilaian.service;

import example.penilaian.entity.Users;
import example.penilaian.model.LoginUserRequest;
import example.penilaian.model.UserToken;
import example.penilaian.model.penilaianLapangan.ValidatorService;
import example.penilaian.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorService validatorService;

    @Transactional
    public UserToken login(LoginUserRequest request) {
        validatorService.validate(request);

        Users user = userRepository.findByNip(request.getNip())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "NIP or Password Salah"));

        // Periksa apakah kata sandi yang dimasukkan oleh pengguna sama dengan yang ada di database.
        if (user.getPassword().equals(request.getPassword())) {
            // Password cocok, Anda dapat mengizinkan akses ke pengguna.

            return UserToken.builder()
                    .nip(user.getNip())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();
        } else {
            throw new ResponseStatusException(UNAUTHORIZED, "NIP or Password Salah");
        }
    }
}
