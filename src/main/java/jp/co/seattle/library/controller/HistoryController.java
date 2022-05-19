package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.BorrowService;

/**
 * 貸出履歴表示コントローラー
 */
@Controller
public class HistoryController {

	@Autowired
	private BorrowService borrowService;

	final static Logger logger = LoggerFactory.getLogger(HistoryController.class);

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String login(Model model) {

		model.addAttribute("bookHistory", borrowService.getHistoryInfo());

		return "history";
	}

}
