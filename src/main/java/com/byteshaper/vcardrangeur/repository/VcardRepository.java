package com.byteshaper.vcardrangeur.repository;

import java.util.Collection;
import java.util.Optional;

public interface VcardRepository {

    String create(String content);

    Optional<String> find(String key);
    
    boolean delete(String key);
    
    Collection<String> findAllKeys();
}
