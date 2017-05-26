package com.byteshaper.vcardrangeur.service;

import com.byteshaper.vcardrangeur.repository.VcardRepository;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VcardService {
    
    @Autowired
    private VcardRepository vcardRepository;
   
    
    public Optional<List<VCard>> getOriginal(String key) {
        
        Optional<String> content = vcardRepository.find(key);
        
        if(content.isPresent()) {
            return Optional.of(Ezvcard.parse(content.get()).all());
        } 
        
        return Optional.empty();
        
    }
}
