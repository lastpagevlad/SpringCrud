package org.crudSpringApp.controller;

import org.crudSpringApp.dao.PersonDAO;
import org.crudSpringApp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;


    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        //Получим всех людей из ДАО и передадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        //Получим одного человека по id из ДАО и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        //Создает пустой объект с пустым конструктором и помещает в модель
        //можно сделать и через моделаттрибут, указывая объект персон и класс персон, как в методе креейт
        //метод креейт никуда не девается от этого
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person){
        personDAO.save(person);
        return "redirect:/people";
        }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }
}
