package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 書籍サービス
 * 
 *  borrowテーブルに関する処理を実装する
 */
@Service
public class BorrowService {
	 final static Logger logger = LoggerFactory.getLogger(BorrowService.class);
	    @Autowired
	    private JdbcTemplate jdbcTemplate;
	
	/**
	 * 書籍貸し出しテーブルに追加
	 *
	 */
    public void registBorrow(int bookId) {
    	String sql = "insert into borrow (book_id) select " + bookId + " where NOT EXISTS (select book_id from borrow where book_id=" + bookId + ")";
    	
    	 jdbcTemplate.update(sql);
    }
    
    /**
	 * 貸出レコードカウント
	 *
	 */
    public int count() {
    	String sql = "SELECT COUNT(book_id) FROM borrow";
    	
    	return jdbcTemplate.queryForObject(sql,int.class);
    	
    }
    
    public void returnBook(int bookId) {
    	String sql = "delete from borrow where book_id =" + bookId;
    	
    	jdbcTemplate.update(sql);
    	
    }
    
    

}
