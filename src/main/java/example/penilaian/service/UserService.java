package example.penilaian.service;

import example.penilaian.entity.Users;
import example.penilaian.model.penilaianLapangan.ValidatorService;
import jakarta.transaction.Transactional;
import example.penilaian.model.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import example.penilaian.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidatorService validatorService;
    @Transactional
    public void register(RegisterUserRequest request){


        validatorService.validate(request);

        if (userRepository.existsById(request.getNip())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "NIP already exist");
        }
        Users users =new Users();

        users.setNip(request.getNip());
        users.setUsername(request.getUsername());
        users.setPassword(request.getPassword());
        users.setRole(request.getRole());

        userRepository.save(users);
    }

}
