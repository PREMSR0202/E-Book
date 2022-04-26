package com.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Books")
public class Books implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int book_id;

	@Column(name = "Name")
	private String book_name;

	@Column(name = "Author")
	private String author_name;

	@Column(name = "Edition")
	private String book_edition;

	@Column(name = "Publication")
	private String book_publication;

	@Column(name = "Description")
	@Size(max = 500)
	private String book_description;

	@Column(name = "ImagePath")
	private String book_image;

	@Column(name = "BookPath")
	private String book_path;

	@Column(name = "Genre")
	private String book_genre;

	@Column(name = "Status")
	private int book_status;

	@Column(name = "Slag")
	private String slag;

	@Column(name = "User_id")
	private int user_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Books() {

	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getBook_edition() {
		return book_edition;
	}

	public void setBook_edition(String book_edition) {
		this.book_edition = book_edition;
	}

	public String getBook_publication() {
		return book_publication;
	}

	public void setBook_publication(String book_publication) {
		this.book_publication = book_publication;
	}

	public String getBook_description() {
		return book_description;
	}

	public void setBook_description(String book_description) {
		this.book_description = book_description;
	}

	public String getBook_image() {
		return book_image;
	}

	public void setBook_image(String book_image) {
		this.book_image = book_image;
	}

	public String getBook_path() {
		return book_path;
	}

	public void setBook_path(String book_path) {
		this.book_path = book_path;
	}

	public String getBook_genre() {
		return book_genre;
	}

	public void setBook_genre(String book_genre) {
		this.book_genre = book_genre;
	}

	public int getBook_status() {
		return book_status;
	}

	public void setBook_status(int book_status) {
		this.book_status = book_status;
	}

	public String getSlag() {
		return slag;
	}

	public void setSlag(String slag) {
		this.slag = slag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author_name, book_description, book_edition, book_genre, book_id, book_image, book_name,
				book_path, book_publication, book_status, slag, user_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Books other = (Books) obj;
		return Objects.equals(author_name, other.author_name)
				&& Objects.equals(book_description, other.book_description)
				&& Objects.equals(book_edition, other.book_edition) && Objects.equals(book_genre, other.book_genre)
				&& book_id == other.book_id && Objects.equals(book_image, other.book_image)
				&& Objects.equals(book_name, other.book_name) && Objects.equals(book_path, other.book_path)
				&& Objects.equals(book_publication, other.book_publication) && book_status == other.book_status
				&& Objects.equals(slag, other.slag) && user_id == other.user_id;
	}

	@Override
	public String toString() {
		return "Books [book_id=" + book_id + ", book_name=" + book_name + ", author_name=" + author_name
				+ ", book_edition=" + book_edition + ", book_publication=" + book_publication + ", book_description="
				+ book_description + ", book_image=" + book_image + ", book_path=" + book_path + ", book_genre="
				+ book_genre + ", book_status=" + book_status + ", slag=" + slag + ", user_id=" + user_id + "]";
	}

	public Books(int book_id, String book_name, String author_name, String book_edition, String book_publication,
			@Size(max = 500) String book_description, String book_image, String book_path, String book_genre,
			int book_status, String slag, int user_id) {
		super();
		this.book_id = book_id;
		this.book_name = book_name;
		this.author_name = author_name;
		this.book_edition = book_edition;
		this.book_publication = book_publication;
		this.book_description = book_description;
		this.book_image = book_image;
		this.book_path = book_path;
		this.book_genre = book_genre;
		this.book_status = book_status;
		this.slag = slag;
		this.user_id = user_id;
	}

	

}
