package com.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.BooksExcelExporter;
import com.model.Books;
import com.service.BookService;

@Controller
public class ExcelController {

	@Autowired
	private BookService bookService;

	@GetMapping("/excel")
	public void exportToExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=List_of_books_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Books> listBooks = bookService.findAll();

		BooksExcelExporter excelExporter = new BooksExcelExporter(listBooks);

		excelExporter.export(response);
	}

}
