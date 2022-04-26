package com.validator;

import com.model.Books;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BooksValidation implements Validator {


	@Override
	public boolean supports(Class<?> clazz) {
		return Books.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Books books = (Books) obj;
		
		if(books.getBook_name().length() < 3 || books.getBook_name().length() > 30) {
			errors.rejectValue("book_name", "Size.bookName");
		}
		
		if(books.getBook_description().length() > 224) {
			errors.rejectValue("book_description", "Size.bookDescription");
		}
		
		
	}

}
