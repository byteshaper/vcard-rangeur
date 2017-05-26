package com.byteshaper.vcardrangeur.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryVcardRepository implements VcardRepository {

    private final Map<String, String> vcards = new ConcurrentHashMap<>();
    
    @Override
    public String create(String content) {
        String uuid = UUID.randomUUID().toString();
        vcards.put(uuid, content);
        return uuid;
    }

    @Override
    public Optional<String> find (String key) {
        return Optional.ofNullable(vcards.get(key));
    }

    @Override
    public boolean delete(String key) {
        return vcards.remove(key) != null;
    }

    @Override
    public Collection<String> findAllKeys() {
        return vcards.keySet();
    }
}
