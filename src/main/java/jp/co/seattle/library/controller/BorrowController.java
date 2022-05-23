package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.BorrowService;

/**
 * 貸出コントローラー
 */
@Controller
public class BorrowController {
	final static Logger logger = LoggerFactory.getLogger(BorrowController.class);

	@Autowired
	private BooksService booksService;

	@Autowired
	private BorrowService borrowService;

	/**
	 * 詳細画面に遷移する
	 * 
	 * @param locale
	 * @param bookId
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/borrow", method = RequestMethod.POST)
	public String borrowBook(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		// デバッグ用ログ
		logger.info("Welcome borrowController.java! The client locale is {}.", locale);

		int getHistory = borrowService.getHistory(bookId);

		if (getHistory == 0) {
			borrowService.registBorrow(bookId);
		} else {

			int borrow = borrowService.getBorrowHistory(bookId);

			if (borrow != bookId) {

				model.addAttribute("borrowError", "貸出し済です。");

			} else {

				borrowService.editHistory(bookId);

			}
		}

		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));

		return "details";
	}

}
