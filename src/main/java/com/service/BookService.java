package com.service;

import java.util.List;

import com.model.Books;

public interface BookService {
	public void createBooks(Books books);

	public List<Books> findAll();

	public Books findByID(int id);

	public void deleteBooks(int id);

	public List<Books> findByGenre(String genre);

	public List<Books> findByName(String name);

	public List<Books> findByAuthor(int author);

}
