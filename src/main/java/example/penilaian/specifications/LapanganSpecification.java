package example.penilaian.specifications;

import example.penilaian.entity.penilaianLapangan.NilaiLapangan;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LapanganSpecification {

    public static Specification<NilaiLapangan> searchLapangan(String keyword, Date startDate , Date endDate ){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + keyword.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("nip")), "%" + keyword.toLowerCase() + "%")
                ));
            }

            if (startDate != null && endDate != null){
                predicates.add(criteriaBuilder.between(root.get("createdAt"), startDate, endDate));
            }

            return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        });
    }
}
