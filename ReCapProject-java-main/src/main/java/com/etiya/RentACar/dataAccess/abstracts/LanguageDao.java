package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.entites.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDao extends JpaRepository<Language,Integer> {
    boolean existsByLanguageName(String languageName);
    LanguageSearchListDto getById(int id);
    boolean existsById(int id);

}
