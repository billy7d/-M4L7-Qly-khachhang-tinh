package service;

import model.Customer;
import model.Province;

import java.util.Iterator;
import java.util.List;

public interface CustomerService {

    Iterable<Customer> findAll();

    Customer findById(Integer id);

    void save(Customer customer);

    void remove(Integer id);

    Iterable<Customer> findAllByProvince(Province province);
}
