package com.crud.demo.service;

import com.crud.demo.entity.FormPower;
import com.crud.demo.mapper.FormPowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
public class FormPowerService {
    @Autowired
    private FormPowerMapper formPowerMapper;

    public List<FormPower> getFormPowerList(){return formPowerMapper.getFormPowerList();};

    public FormPower getFormPowerByForm(String form){return formPowerMapper.getFormPowerByForm(form);}

    public void updateFormPowerByForm(FormPower formPower){formPowerMapper.updateFormPower(formPower);}

}
