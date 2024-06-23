package br.com.ace.controller;

import br.com.ace.dto.UserRequestDTO;
import br.com.ace.model.User;
import br.com.ace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/ace/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/cadastro")
    public ModelAndView preRegistration(ModelMap model) {
        model.addAttribute("users", new User());
        return new ModelAndView("/user/add", model);
    }

    @PostMapping("/salvar")
    public String toSave(@Valid @ModelAttribute("users") UserRequestDTO userDTO, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "user/add";
        }

        userService.toInsert(userDTO);
        attr.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso.");
        return "redirect:/ace/user/dashboard";
    }

    @GetMapping("/{id}/atualizar")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("users", user);
        return new ModelAndView("/user/add", model);
    }

    @PostMapping("/{id}/atualizar")
    public String toUpdate(@PathVariable("id") int id, @Valid @ModelAttribute("users") UserRequestDTO userDTO, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "user/add";
        }

        userService.updateUser(id, userDTO);
        attr.addFlashAttribute("mensagem", "Usuário atualizado com sucesso.");
        return "redirect:/ace/user/dashboard";
    }

    @GetMapping("/{id}")
    public ModelAndView getUserDetails(@PathVariable("id") int id, ModelMap model) {
        User user = userService.getUserDetails(id);
        model.addAttribute("users", user);
        return new ModelAndView("user/details", model);
    }

    @PostMapping("/{id}/salario")
    public String updateGrossSalary(@PathVariable("id") int id, @RequestParam double grossSalary, RedirectAttributes attr) {
        userService.updateGrossSalary(id, grossSalary);
        attr.addFlashAttribute("mensagem", "Salário bruto atualizado com sucesso.");
        return "redirect:/ace/user/dashboard";
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(ModelMap model) {
        return new ModelAndView("user/dashboard", model);
    }

}
