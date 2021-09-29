package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.domain.Address;
@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

}
