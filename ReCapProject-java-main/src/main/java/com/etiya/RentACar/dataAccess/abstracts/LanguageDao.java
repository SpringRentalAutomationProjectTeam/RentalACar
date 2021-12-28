package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.entites.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDao extends JpaRepository<Language,Integer> {

}
