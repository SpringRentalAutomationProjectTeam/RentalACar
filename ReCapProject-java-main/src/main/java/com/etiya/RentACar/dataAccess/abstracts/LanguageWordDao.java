package com.etiya.RentACar.dataAccess.abstracts;

import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.entites.LanguageWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageWordDao extends JpaRepository<LanguageWord,Integer> {
    LanguageWord getByLanguageIdAndMessageKeyId(int languageId,int keyId);
    boolean existsByTranslation(String languageWord);
    boolean existsByLanguageIdAndMessageKeyId(int languageId, int messageKeyId);
}
