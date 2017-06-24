package com.byteshaper.vcardrangeur.rest;

import com.byteshaper.vcardrangeur.domain.AddressBook;
import com.byteshaper.vcardrangeur.exception.NotFoundException;
import com.byteshaper.vcardrangeur.repository.VcardRepository;
import com.byteshaper.vcardrangeur.service.VcardService;
import io.swagger.annotations.ApiParam;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VcardRangeurRestController {

    @Autowired
    private VcardRepository vcardRepository;
    
    @Autowired
    private VcardService vcardService;
    
    @RequestMapping(            
            value = "/addressbooks",
            method = RequestMethod.POST,
            consumes = "text/plain",
            produces = "text/plain")
    public Serializable createAddressBookEntry(
            @RequestBody String content) {
        return vcardRepository.create(content);
    }
    
    @RequestMapping(
            value = "/addressbooks/keys",
            method = RequestMethod.GET,
            produces = "application/json")
    public Collection<String> listAddressBookKeys() {
        return vcardRepository.findAllKeys();
    }
    
    @RequestMapping(
            value = "/addressbooks/{key}/raw",
            method = RequestMethod.GET,
            produces = "text/plain")
    public String getAddressbookRawContent(@ApiParam @PathVariable("key") String key) {
        return vcardRepository.find(key).orElseThrow(() -> new NotFoundException("No addressbook found for key: " + key));
    }
    
    @RequestMapping(
        value = "/addressbooks/{key}/json",
        method = RequestMethod.GET,
        produces = "application/json")
    public AddressBook getAddressbookOriginal(@ApiParam @PathVariable("key") String key) {
        return vcardService.getOriginal(key).orElseThrow(() -> new NotFoundException("No addressbook found for key: " + key));
    }
}
