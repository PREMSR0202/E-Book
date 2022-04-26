package com.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model.Books;
import com.service.BookService;
import com.validator.BooksValidation;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BooksValidation booksValidation;

	@RequestMapping(value = "/newbook", method = RequestMethod.GET)
	public String createBooks(Model model, Books books, HttpSession session) {
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("books", new Books());
		model.addAttribute("edit", "newbook");
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "newbook";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateBooks(@RequestParam(value = "id") int id, Model model, Books books, HttpSession session) {
		Books b = bookService.findByID(id);
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("books", b);
		model.addAttribute("edit", "update");
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "newbook";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteBooks(@RequestParam(value = "id") int id, Books books) throws Exception {
		Books b = bookService.findByID(id);
		String projectDir = Paths.get("").toAbsolutePath().toString();
		Files.delete(Paths.get(projectDir + "\\src\\main\\resources\\static\\" + b.getBook_image()));
		Files.delete(Paths.get(projectDir + "\\src\\main\\resources\\static\\" + b.getBook_path()));
		bookService.deleteBooks(id);
		return "redirect:/listbooks";
	}

	@RequestMapping(value = "/listbooks", method = RequestMethod.GET)
	public String listBooks(Model model, Books books, HttpSession session) {
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("listbooks", bookService.findAll());
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "listBooks";
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public String filter(@RequestParam(value = "genre") String genre, Model model, Books books, HttpSession session) {
		List<Books> result = bookService.findByGenre(genre);
		if (result.isEmpty()) {
			model.addAttribute("result", "Sorry No Books Found !");
		} else {
			model.addAttribute("listbooks", bookService.findByGenre(genre));
		}
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "listBooks";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, Books books, HttpSession session) {
		List<Books> result = bookService.findByName(books.getBook_name());
		if (result.isEmpty()) {
			model.addAttribute("result", "Sorry No Books Found !");
		} else {
			model.addAttribute("listbooks", bookService.findByName(books.getBook_name()));
		}
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "listBooks";
	}

	@RequestMapping(value = "/pdfshow", method = RequestMethod.GET)
	public String showPdf(@RequestParam(value = "path") String path, Model model) {
		model.addAttribute("path", path + "#toolbar=0&");
		boolean account = true;
		String redirect;
		if (account) {
			redirect = "showbooks";
		} else {
			redirect = "adminpage";
		}
		return redirect;
	}

	@GetMapping(value = "/successpayment")
	public String successPayment(Books books, HttpSession session, Model model) {
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "Success";
	}

	@GetMapping(value = "/mybooks")
	public String myBooks(Books books, HttpSession session, Model model) {
		model.addAttribute("listbooks",
				bookService.findByAuthor((int) session.getAttribute("user")));
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "mybooks";
	}

	@RequestMapping(value = "/upload/book", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute(name = "books") Books books, @RequestParam("fileImage") MultipartFile file,
			@RequestParam("filePDF") MultipartFile filePDF, BindingResult bindingResult, HttpSession session,
			Model model) throws Exception {

		booksValidation.validate(books, bindingResult);

		System.out.println(bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("edit", "newbook");
			model.addAttribute("roll", session.getAttribute("role"));
			model.addAttribute("account", session.getAttribute("account"));
			model.addAttribute("flag", session.getAttribute("flag"));
			return "newbook";
		}

		String projectDir = Paths.get("").toAbsolutePath().toString();

		String fileImgname = StringUtils.cleanPath(file.getOriginalFilename());
		String filePDFname = StringUtils.cleanPath(filePDF.getOriginalFilename());

		String uploadImgDir = projectDir + "\\src\\main\\resources\\static\\images";
		String uploadPDFDir = projectDir + "\\src\\main\\resources\\static\\pdf";

		Path uploadImgpath = Paths.get(uploadImgDir);
		Path uploadPDFpath = Paths.get(uploadPDFDir);
		if (!Files.exists(uploadImgpath)) {
			Files.createDirectories(uploadImgpath);
		}
		if (!Files.exists(uploadPDFpath)) {
			Files.createDirectories(uploadPDFpath);
		}

		try {
			InputStream is = file.getInputStream();
			InputStream ip = filePDF.getInputStream();
			Path fileImgpath = uploadImgpath.resolve(fileImgname);
			Path filePDFpath = uploadPDFpath.resolve(filePDFname);
			Files.copy(is, fileImgpath, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(ip, filePDFpath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}

		books.setSlag(StringUtils.cleanPath(filePDF.getOriginalFilename()).replaceAll(" ", "-").toLowerCase());
		books.setBook_image("\\images\\" + fileImgname);
		books.setBook_path("\\pdf\\" + filePDFname);
		books.setUser_id((int) session.getAttribute("user"));

		bookService.createBooks(books);

		return "redirect:/listbooks";

	}

}
