package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
}
