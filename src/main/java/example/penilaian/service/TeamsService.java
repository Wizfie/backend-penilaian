package example.penilaian.service;


import example.penilaian.entity.Teams;
import example.penilaian.repository.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamsService {


    @Autowired
    private TeamsRepository teamsRepository;

    public List<Teams> getAllTeam(){
        return teamsRepository.findAll();
    }

}
