package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.HistoryInfo;
import jp.co.seattle.library.rowMapper.HistoryInfoRowmapper;

/**
 * 書籍サービス
 * 
 * borrowテーブルに関する処理を実装する
 */
@Service
public class BorrowService {
	final static Logger logger = LoggerFactory.getLogger(BorrowService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍を新規に借りる
	 * 
	 * @param bookId 書籍Id
	 */
	public void registBorrow(int bookId) {

		String sql = "insert into borrow (book_id,reg_date,upd_date) select " + bookId
				+ ",now() ,null where NOT EXISTS (select book_id from borrow where book_id=" + bookId + ")";

		jdbcTemplate.update(sql);
	}

	/**
	 * 書籍を返却状態から借りる
	 *
	 * @param bookId 書籍ID
	 */
	public void editHistory(int bookId) {
		String sql = "UPDATE borrow SET reg_date=now(), upd_date = null  Where book_id = " + bookId;

		jdbcTemplate.update(sql);
	}

	/**
	 * 書籍を返却する
	 *
	 * @param bookId 書籍ID
	 */
	public void returnHistory(int bookId) {
		String sql = "UPDATE borrow SET reg_date=null,upd_date = now() Where book_id = " + bookId;

		jdbcTemplate.update(sql);
	}

	/**
	 * 貸出履歴を取得
	 *
	 */
	public List<HistoryInfo> getHistoryInfo() {

		// JSPに渡すデータを設定する
		String sql = "SELECT borrow.id,books.title,borrow.reg_date,borrow.upd_date,borrow.book_id FROM borrow left outer join books on books.id = borrow.book_id";

		List<HistoryInfo> history = jdbcTemplate.query(sql, new HistoryInfoRowmapper());

		return history;
	}

	/**
	 * 返却中書籍のみ取得
	 *
	 * @param bookId 書籍ID
	 */
	public int getBorrowHistory(int bookId) {

		try {

			String sql = "SELECT book_id FROM borrow where reg_date is null and book_id=" + bookId;
			return jdbcTemplate.queryForObject(sql, int.class);

		} catch (Exception e) {

			return 0;
		}

	}

	/**
	 * 貸出中書籍のみ取得
	 *
	 * @param bookId 書籍ID
	 */
	public int getReturnHistory(int bookId) {

		try {

			String sql = "SELECT book_id FROM borrow where upd_date is null and book_id=" + bookId;
			return jdbcTemplate.queryForObject(sql, int.class);

		} catch (Exception e) {

			return 0;
		}

	}

	/**
	 * 書籍を貸出テーブルから消去する
	 *
	 * @param bookId 書籍Id
	 */
	public void returnBook(int bookId) {

		String sql = "delete from borrow where book_id =" + bookId;

		jdbcTemplate.update(sql);
	}

	/**
	 * 選択書籍が貸出テーブルにある場合取得
	 *
	 * @param bookId 書籍ID
	 */
	public int getHistory(int bookId) {

		try {

			String sql = "SELECT book_id FROM borrow where book_id=" + bookId;
			return jdbcTemplate.queryForObject(sql, int.class);

		} catch (Exception e) {

			return 0;
		}

	}

}
