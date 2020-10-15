package controller;


import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import repository.ProvinceRepository;
import service.CustomerService;
import service.ProvinceService;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String listProvinces(Model model){
        Iterable<Province> provinces = provinceService.findAll();
        model.addAttribute("provinces", provinces);
        return "province/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("province", new Province());
        return "province/create";
    }

    @PostMapping("/create")
    public String saveProvince(@ModelAttribute("province") Province province, Model model){
        provinceService.save(province);
        model.addAttribute("province", new Province());
        model.addAttribute("message", "New province created successfully");
        return "province/create";
    }

    @GetMapping("/provinces/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model){
        Province province = provinceService.findById(id);
        if(province != null) {
            model.addAttribute("province", province);
            return "province/edit";

        }else {
            return "error.404";
        }
    }

    @PostMapping("/provinces/edit")
    public String updateProvince(@ModelAttribute("province") Province province, Model model){
        provinceService.save(province);

        model.addAttribute("province", province);
        model.addAttribute("message", "Province updated successfully");
        return "province/edit";
    }

    @GetMapping("/provinces/delete/{id}")
    public String showDeleteForm(@PathVariable Integer id,Model model){
        Province province = provinceService.findById(id);
        if(province != null) {
            model.addAttribute("province", province);
            return "province/delete";

        }else {
            return "error.404";
        }
    }

    @PostMapping("/provinces/delete")
    public String deleteProvince(@ModelAttribute("province") Province province){
        provinceService.remove(province.getId());
        return "redirect:provinces";
    }

    @GetMapping("/provinces/view/{id}")
    public String viewProvince(@PathVariable("id") Integer id,Model model){
        Province province = provinceService.findById(id);
        if(province == null){
            return "/province/error.404";
        }

        Iterable<Customer> customers = customerService.findAllByProvince(province);

        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("province", province);
        model.addAttribute("customers", customers);
        return "province/view";
    }

}
