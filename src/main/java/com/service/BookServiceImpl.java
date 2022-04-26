package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Books;
import com.repository.BooksRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BooksRepository booksRepository;

	@Override
	public List<Books> findAll() {
		return booksRepository.findAll();
	}

	@Override
	public void createBooks(Books books) {
		booksRepository.save(books);
	}

	@Override
	public Books findByID(int id) {
		return booksRepository.findById(id).orElse(new Books());
	}

	@Override
	public void deleteBooks(int id) {
		booksRepository.deleteById(id);
	}

	@Override
	public List<Books> findByGenre(String genre) {
		return booksRepository.findByGenre(genre);
	}

	@Override
	public List<Books> findByName(String name) {
		return booksRepository.findByName(name);
	}

	@Override
	public List<Books> findByAuthor(int author) {
		return booksRepository.finByAuthor(author);
	}

}
