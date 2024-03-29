package com.etiya.RentACar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.business.concretes.LanguageWordManager;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.entites.LanguageWord;
import com.etiya.RentACar.entites.MessageKey;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.etiya.RentACar.core.utilities.results.ErrorDataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@RestControllerAdvice
public class RentACarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentACarApplication.class, args);
	}


	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.etiya.RentACar"))                                     
          .build();                                           
    }
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper=new ModelMapper();
		return modelMapper;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
		Map<String, String> validationErrors=new HashMap<String, String>();
		for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorDataResult<Object> errors=new ErrorDataResult<Object>(validationErrors,Messages.VALIDATIONERROR);
		return errors;
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult handleNoSuchElementException(NoSuchElementException exception){
	
		ErrorResult error = new ErrorResult(Messages.RECORDNOTFOUND);
		return error;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResult HttpMessageNotReadableException(HttpMessageNotReadableException exception){

		ErrorResult error = new ErrorResult(Messages.LOCALDATEERROR);
		return error;
	}


	/*@Override
	@Bean
	public void afterPropertiesSet() throws Exception {//string value int keyId
		List<LanguageWord> languageWords = this.languageWordService.findAllByLanguageId(1).getData();

		for (LanguageWord languageWord: languageWords) {
			String messageKey =this.messageKeyService.getById(languageWord.getMessageKey().getId()).getData().getKey();
			Messages.keyAndValue.put(messageKey,languageWord.getTranslation());
		}
	}*/

}

