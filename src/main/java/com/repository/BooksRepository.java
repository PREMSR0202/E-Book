package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

	@Query("from Books where book_genre=?1")
	public List<Books> findByGenre(String genre);
	
	@Query("from Books where book_name=?1")
	public List<Books> findByName(String name);

	@Query("from Books where user_id=?1")
	public List<Books> finByAuthor(int author);

}
