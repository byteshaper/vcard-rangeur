package com.byteshaper.vcardrangeur.rest;

import com.byteshaper.vcardrangeur.domain.AddressBook;
import com.byteshaper.vcardrangeur.service.VcardService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VcardRangeurRestController {

  @Autowired
    private VcardService vcardService;
    
    
    @RequestMapping(
        value = "/addressbooks",
        method = RequestMethod.POST,
        consumes = "text/plain",
        produces = "application/json")
    public AddressBook getAddressbookOriginal(@RequestBody @NotBlank String content) {
      
        return vcardService.getOriginal(content);
    }
}
