package repository;

import model.Customer;
import model.Province;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Integer> {
    Iterable<Customer> findAllByProvince(Province province);
}
