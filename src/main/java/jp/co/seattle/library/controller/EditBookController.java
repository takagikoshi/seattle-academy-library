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
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

@Controller
public class EditBookController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);
	
	@Autowired //APIの入り口
    private BooksService booksService;
	
	@Autowired
    private ThumbnailService thumbnailService;
	
	 @Transactional
	 @RequestMapping(value = "/editBook", method = RequestMethod.POST,produces = "text/plain;charset=utf-8") //value＝actionで指定したパラメータ
	    //RequestParamでname属性を取得
	    public String editBook(Model model, Locale locale, @RequestParam("bookId") int bookId) {
		 model.addAttribute("bookInfo",booksService.getBookInfo(bookId));
		 return "edit";
	    }
	 
	 /**
	  * 書籍情報を登録する
	  * @param locale ロケール情報
	  * @param title 書籍名
	  * @param author 著者名
	  * @param publisher 出版社
	  * @param file サムネイルファイル
	  * @param model モデル
	  * @return 遷移先画面
	  */
	 @Transactional
	 @RequestMapping(value = "/upDateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	    public String upDateBook(Locale locale,
	    		@RequestParam("bookId") int bookId,
	            @RequestParam("title") String title,
	            @RequestParam("author") String author,
	            @RequestParam("publisher") String publisher,
	            @RequestParam("thumbnail") MultipartFile file,
	            @RequestParam("publishDate") String publishDate,
	            @RequestParam("isbn") String isbn,
	            @RequestParam("explanation") String explanation,
	            Model model) {
	        logger.info("Welcome editBooks.java! The client locale is {}.", locale);
	        
	     // パラメータで受け取った書籍情報をDtoに格納する。
	        BookDetailsInfo bookInfo = new BookDetailsInfo();
	        bookInfo.setBookId(bookId);
            bookInfo.setTitle(title);
	        bookInfo.setAuthor(author);
	        bookInfo.setPublisher(publisher);
	        bookInfo.setPublishDate(publishDate);
	        bookInfo.setIsbn(isbn);
	        bookInfo.setExplanation(explanation);
	        
	        // クライアントのファイルシステムにある元のファイル名を設定する
	        String thumbnail = file.getOriginalFilename();

	        if (!file.isEmpty()) {
	            try {
	                // サムネイル画像をアップロード
	                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
	                // URLを取得
	                String thumbnailUrl = thumbnailService.getURL(fileName);

	                bookInfo.setThumbnailName(fileName);
	                bookInfo.setThumbnailUrl(thumbnailUrl);

	            } catch (Exception e) {

	                // 異常終了時の処理
	                logger.error("サムネイルアップロードでエラー発生", e);
	                model.addAttribute("bookDetailsInfo", bookInfo);
	                return "edit";
	            }
	        }
	 
	 
	 boolean validPd = publishDate.matches("^(?!([02468][1235679]|[13579][01345789])000229)(([0-9]{4}(01|03|05|07|08|10|12)(0[1-9]|[12][0-9]|3[01]))|([0-9]{4}(04|06|09|11)(0[1-9]|[12][0-9]|30))|([0-9]{4}02(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}([02468][048]|[13579][26])0229))$");
     boolean validIsbn = isbn.matches("^[0-9]{10}$|^[0-9]{13}$|^[0-9]{0}$" );
     boolean isEmptyBookInfo = title.isEmpty() ||  publisher.isEmpty() || author.isEmpty() || publishDate.isEmpty();
    
     
     
     //必須項目なし
     if (isEmptyBookInfo) {
    	 
    	 model.addAttribute("emptyErrorMessage", "必須項目です");
     } 
     
     //日付不正解
     if (!validPd) {
     	
    	 model.addAttribute("PdErrorMessage",  "出版日は半角数字の半角数字YYYYMMDD形式で入力してください");
     } 
     
   //isbn不正解	
     if(!validIsbn) {
     	
    	 model.addAttribute("IsbnErrorMessage","ISBNの桁数または半角数字が正しくありません");
     }
     


      if (!validPd  || isEmptyBookInfo || !validIsbn) {
     	 
     	 model.addAttribute("bookInfo",bookInfo);
     	 return "edit";
      }
   
     
     

     // 書籍情報を変更を登録する
     
      
     booksService.editBook(bookInfo);
     
     int editId = bookInfo.getBookId();  
     
     
     //  詳細画面に遷移する
     	model.addAttribute("bookDetailsInfo",booksService.getBookInfo(editId));
     	return "details";
     
 }
	
}
