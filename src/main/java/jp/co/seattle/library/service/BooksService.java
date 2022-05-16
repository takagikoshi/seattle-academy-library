package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"select id, title, author, publisher , publish_date, thumbnail_url from books ORDER BY title",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	/**
	 * 検索した書籍リストを取得する（完全一致）
	 *
	 *@param 検索文字
	 * @return 書籍リスト
	 */
	public List<BookInfo> searchAllBookList(String search) {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"select id, title, author, publisher , publish_date, thumbnail_url from books  where title ='"+search+"'ORDER BY title",
				new BookInfoRowMapper());

		return getedBookList;
	}
	
	/**
	 * 検索した書籍リストを取得する（部分一致）
	 *
	 *@param 検索文字
	 * @return 書籍リスト
	 */
	public List<BookInfo> searchPartBookList(String search) {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"select id, title, author, publisher , publish_date, thumbnail_url ,isbn, explanation from books where title like '%"+search+"%'ORDER BY title",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {

		// JSPに渡すデータを設定する
		String sql = "SELECT * ,case WHEN book_id is null THEN '貸し出し可' ELSE '貸し出し中' end as status FROM books left outer join borrow on books.id = borrow.book_id where books.id ="
				+ bookId;

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void registBook(BookDetailsInfo bookInfo) {

		String sql = "INSERT INTO books (title, author,publisher,publish_date,thumbnail_name,thumbnail_url,isbn, explanation,reg_date,upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" + bookInfo.getThumbnailName() + "','" + bookInfo.getThumbnailUrl()
				+ "','" + bookInfo.getIsbn() + "','" + bookInfo.getExplanation() + "',now()," + "now())";

		jdbcTemplate.update(sql);
	}

	/**
	 * 書籍を消去する
	 *
	 * @param bookId 書籍Id
	 */
	public void deleteBook(int bookId) {

		String sql = "delete from books where id =" + bookId;

		jdbcTemplate.update(sql);
	}

	/**
	 * 最新のID取得
	 *
	 */
	public Integer maxId() {
		String sql = "SELECT MAX(id) FROM books";

		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	/**
	 * 書籍を編集する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void editBook(BookDetailsInfo bookInfo) {
		String sql = "UPDATE books SET title ='" + bookInfo.getTitle() + "', author = '" + bookInfo.getAuthor()
				+ "', publisher = '" + bookInfo.getPublisher() + "', publish_date = '" + bookInfo.getPublishDate()
				+ "', thumbnail_url ='" + bookInfo.getThumbnailUrl() + "', isbn ='" + bookInfo.getIsbn()
				+ "', upd_date = now(), explanation ='" + bookInfo.getExplanation() + "' Where id = "
				+ bookInfo.getBookId();

		jdbcTemplate.update(sql);
	}
}
