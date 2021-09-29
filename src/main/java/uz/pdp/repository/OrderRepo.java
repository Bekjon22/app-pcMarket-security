package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Order;
@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
}
