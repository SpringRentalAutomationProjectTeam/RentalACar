package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.entites.MessageKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageKeyDao extends JpaRepository<MessageKey,Integer> {
    MessageKey findByKey(String key);
    boolean existsByKey(String key);

}
