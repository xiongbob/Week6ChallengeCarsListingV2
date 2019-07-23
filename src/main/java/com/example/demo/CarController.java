package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
public class CarController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CarRepository carRepository;

    @RequestMapping("/")
    public String index(Model model)
    {

        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("people", categoryRepository.findAll());
        //return "index";
        return "list";
    }

    @GetMapping("/add")
    public String messageForm(Model model) {
        model.addAttribute("car", new Car());
        //return "carform";
        return "HowManageCars";
    }
    @PostMapping("/process")
    public String processForm(@Valid Car car, BindingResult result) {
        if (result.hasErrors()){
            //return "carform";
            return "HowManageCars";
        }
        carRepository.save(car);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showMessages(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateMessages(@PathVariable("id") long id,  Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        //return "carform";
        return "HowManageCars";
    }

    @RequestMapping("/delete/{id}")
    public String delMessages(@PathVariable("id") long id,  Model model) {
        carRepository.deleteById(id);
        return "redirect:/";
    }




    @RequestMapping("/addcar")
    public String addPet(Model model)
    {

        model.addAttribute("people", categoryRepository.findAll());

        model.addAttribute("car", new Car());
        return "car";
    }

    @RequestMapping("/processcar")
    public String savePet(@ModelAttribute("car") Car car,
                          Model model)
    {
        carRepository.save(car);
        return "redirect:/";
    }

    @PostConstruct
    public void fillTables()
    {
        Category p = new Category();
        p.setCategoryName("Ford");
        categoryRepository.save(p);

        p = new Category();
        p.setCategoryName("Camry");
        categoryRepository.save(p);

        p= new Category();
        p.setCategoryName("GMC");
        categoryRepository.save(p);
    }
}