package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

}
