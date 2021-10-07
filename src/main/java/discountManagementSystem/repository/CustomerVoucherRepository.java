package discountManagementSystem.repository;

import discountManagementSystem.entity.CustomerVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher,Long> {}