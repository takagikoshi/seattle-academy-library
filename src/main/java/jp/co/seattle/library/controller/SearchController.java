package jp.co.seattle.library.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@Controller // APIの入り口
public class SearchController {
	final static Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * 詳細画面に遷移する
	 * 
	 * @param locale
	 * @param bookId
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String borrowBook(Locale locale, @RequestParam("search") String search, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// デバッグ用ログ
		logger.info("Welcome SearchController.java! The client locale is {}.", locale);

		String select = request.getParameter("radiobutton");
		String all = "1";
		String part = "2";

		if (select.equals(all)) {
			model.addAttribute("bookList", booksService.searchAllBookList(search));
			return "home";
		} else if (select.equals(part)) {

			model.addAttribute("bookList", booksService.searchPartBookList(search));

			return "home";
		}

		return "home";
	}
}
