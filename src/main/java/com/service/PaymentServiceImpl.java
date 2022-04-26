package com.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.model.Payment;
import com.model.User;
import com.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserService userservice;

	@Autowired
	private HttpSession session;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromMail;

	@Override
	public void createPayment(Payment payment, int userid) {

		LocalDate start = LocalDate.now();
		LocalDate end = start.plusDays(30);

		payment.setStart_date(start);
		payment.setEnd_date(end);

		User user = userservice.findbyID(userid);
		user.setAccount(true);

		userservice.save(user);

		String transid = (String) session.getAttribute("transactionid");

		payment.setTrans_id(transid);

		createInvoice(user, transid, end, end, userid);

		paymentRepository.save(payment);

	}

	@Override
	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

	public void sendMailWithAttachment(String toEmail, String body, String subject, String attachment, int userid)
			throws Exception {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(fromMail);
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);
		FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
		javaMailSender.send(mimeMessage);

		String projectDir = Paths.get("").toAbsolutePath().toString();
		String uploadInvoiceDir = projectDir + "\\src\\main\\resources\\static\\invoices";
		Path uploadInvoicepath = Paths.get(uploadInvoiceDir);
		if (!Files.exists(uploadInvoicepath)) {
			Files.createDirectories(uploadInvoicepath);
		}
		Files.move(Paths.get(attachment), Paths.get(uploadInvoiceDir + "\\" + attachment),
				StandardCopyOption.REPLACE_EXISTING);
		User user = userservice.findbyID(userid);
		user.setInvoice("\\invoices\\" + attachment);
		userservice.save(user);
	}

	public void createInvoice(User user, String transid, LocalDate start, LocalDate end, int userid) {

		PdfWriter pdfWriter;

		try {

			pdfWriter = new PdfWriter(user.getFname() + ".pdf");
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);

			Document document = new Document(pdfDocument);

			pdfDocument.setDefaultPageSize(PageSize.A4);

			float col = 280f;

			float columnwidth[] = { col, col };

			Table table = new Table(columnwidth);

			table.setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE);
			table.addCell(new Cell().add("Invoice"));

			table.addCell(new Cell().add("E-Book Subscription"));

			float colWidth[] = { 80, 300, 100, 80 };

			Table customerInfoTable = new Table(colWidth);

			customerInfoTable.addCell(new Cell(0, 6).add("Subscriber Information").setBold());

			customerInfoTable.addCell(new Cell().add("Name"));
			customerInfoTable.addCell(new Cell().add(user.getFname() + user.getLname()));

			customerInfoTable.addCell(new Cell().add("Invoice ID"));
			customerInfoTable.addCell(new Cell().add(transid));

			customerInfoTable.addCell(new Cell().add("Email"));
			customerInfoTable.addCell(new Cell().add(user.getEmail()));

			customerInfoTable.addCell(new Cell().add("Validity"));
			customerInfoTable.addCell(new Cell().add(start + " To " + end));

			customerInfoTable.addCell(new Cell().add("Amount"));
			customerInfoTable.addCell(new Cell().add("2$"));

			customerInfoTable.addCell(new Cell().add("Payment Status"));
			customerInfoTable.addCell(new Cell().add("Paid"));

			document.add(table);
			document.add(customerInfoTable);
			document.close();

			sendMailWithAttachment(user.getEmail(), "E-Book Subscription", "Invoice", user.getFname() + ".pdf", userid);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
