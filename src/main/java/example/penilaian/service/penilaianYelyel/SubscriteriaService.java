package example.penilaian.service.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.SubscriteriaYelyel;
import example.penilaian.repository.penilaianYelyel.SubscriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriteriaService {

    @Autowired
    private SubscriteriaRepository subscriteriaRepository;

    public List<SubscriteriaYelyel> getAllSubs(){
        return subscriteriaRepository.findAll();
    }
}
