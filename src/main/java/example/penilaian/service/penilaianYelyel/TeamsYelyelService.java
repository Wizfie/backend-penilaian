package example.penilaian.service.penilaianYelyel;

import example.penilaian.repository.penilaianYelyel.TeamsYelyelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamsYelyelService
{
    @Autowired
    private TeamsYelyelRepository teamsYelyel;

    public List<example.penilaian.entity.penilaianYelyel.TeamsYelyel> getAll(){
        return teamsYelyel.findAll();
    }
}
