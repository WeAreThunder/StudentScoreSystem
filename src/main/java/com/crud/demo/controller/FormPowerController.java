package com.crud.demo.controller;

import com.crud.demo.entity.FormPower;
import com.crud.demo.entity.Student;
import com.crud.demo.service.FormPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FormPowerController {
    @Autowired
    private FormPowerService formPowerService;

    @GetMapping("/formPowerList")
    public String formPowerList(Model model){
        List<FormPower> formPowerList = formPowerService.getFormPowerList();
        model.addAttribute("formPowers",formPowerList);
        return "formPowerList";
    }

    //更新界面权限信息
    @GetMapping("/formPowerUpdate/formPower/{form}")
    public String getFormPowerUpdate(@PathVariable("form") String form,
                                   Model model){
        FormPower formPowerByForm = formPowerService.getFormPowerByForm(form);
        model.addAttribute("formPower",formPowerByForm);
        return "formPowerUpdate";
    }
    //更新后返回
    @PostMapping("/formPowerUpdate")
    public String formPowerUpdate(@ModelAttribute FormPower formPower){
        formPowerService.updateFormPowerByForm(formPower);
        return "redirect:/formPowerList";
    }

}
