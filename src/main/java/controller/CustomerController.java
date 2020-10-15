package controller;


import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;
import service.ProvinceService;

import javax.jws.WebParam;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @Autowired
    ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return  provinceService.findAll();
    }

    @GetMapping("/")
    public String findAll(@PageableDefault(size = 4) Pageable pageable, Model model ){
        Page<Customer> customers =customerService.findAll(pageable);
        model.addAttribute("customers",customers);
        return "customer/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("customer",new Customer());
        return "customer/create";
    }
    @PostMapping("/create/customer")
    public String createCustomer(Customer customer){
        customerService.save(customer);
        return  "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable int id,Model model){
        Customer customer = customerService.findById(id);
        model.addAttribute("customer",customer);
        return "customer/edit";
    }

    @PostMapping("/edit/customer")
    public String edit(Customer customer){
        customerService.save(customer);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        customerService.remove(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String listCustomers(@RequestParam("search") Optional<String> s, Pageable pageable, Model model){
        Page<Customer> customers;
        if(s.isPresent()){
            customers = customerService.findAllByFirstNameContaining(s.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        model.addAttribute("customers", customers);
        return "customer/index";
    }
}
