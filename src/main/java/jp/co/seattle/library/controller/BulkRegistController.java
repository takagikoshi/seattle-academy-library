package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

@Controller //APIの入り口
public class BulkRegistController {
	final static Logger logger = LoggerFactory.getLogger(BulkRegistController.class);
	
	@Autowired
    private BooksService booksService;
	
	@RequestMapping(value = "/bulkRegist", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String login(Model model) {
        return "bulkRegist";
    }
	
	/**
     * 書籍情報を一括登録する
     * @param locale ロケール情報
     * @param file CSVファイル
     * @param model モデル
     * @return 遷移先画面
     */
	
    @Transactional
	@RequestMapping(value = "/bulkRegistBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String bulkRegistBook(Locale locale,Model model,
    		@RequestParam("file") MultipartFile uploadFile) {
    	logger.info("Welcome bulkRegistBooks.java! The client locale is {}.", locale);
    	
    	ArrayList<BookDetailsInfo> bookList = new ArrayList<BookDetailsInfo>();
    	ArrayList<String> countErrors = new ArrayList<String>();
    	
    	
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(uploadFile.getInputStream(), StandardCharsets.UTF_8))){
    		
    		int count = 0;
    		String line = ""; 
    		
    	      while ((line = br.readLine()) != null) {
    	    	  
    	    	  
    	        final String[] split = line.split(",",-1);
    	      
    	        //ループの数数える
    	        count++;
    	        
    	        BookDetailsInfo bookInfo = new BookDetailsInfo();
    	        bookInfo.setTitle(split[0]);
    	        bookInfo.setAuthor(split[1]);
    	        bookInfo.setPublisher(split[2]);
    	        bookInfo.setPublishDate(split[3]);
    	        bookInfo.setIsbn(split[4]);
    	        
    	        
    	        boolean validPd = split[3].matches("^(?!([02468][1235679]|[13579][01345789])000229)(([0-9]{4}(01|03|05|07|08|10|12)(0[1-9]|[12][0-9]|3[01]))|([0-9]{4}(04|06|09|11)(0[1-9]|[12][0-9]|30))|([0-9]{4}02(0[1-9]|1[0-9]|2[0-8]))|([0-9]{2}([02468][048]|[13579][26])0229))$");
    	        boolean validIsbnn = split[4].matches("^[0-9]{10}$");
    	        boolean validIsbn = split[4].matches("^[0-9]{13}$");
    	        boolean isEmptyBookInfo = split[0].isEmpty() ||  split[2].isEmpty() || split[1].isEmpty() || split[3].isEmpty();
    	       
    	        
    	        if (!validPd  || isEmptyBookInfo || (!validIsbnn && !validIsbn)) {
    	        	
        	        countErrors.add(count+ "行目の書籍登録でエラーが起きました。");
        	        }
    	        
    	        
    	        
    	        bookList.add(bookInfo);
    	    
    	        
    	    }
    	      
    	      if (bookList.isEmpty()) {
    	    	  model.addAttribute("emptyFail","CSVに書籍情報がありません。");
    	    		 return "bulkRegist";
    	    	 }
 
    	      
    	    } catch (IOException e) {
    	    	
    	    }
    	
    	    if(countErrors.size() > 0) {
	    		model.addAttribute("countError",countErrors);
	    		
	    		return "bulkRegist";
	    		
	    	}
    	
    	for (BookDetailsInfo bookInfo: bookList){
    		booksService.registBook(bookInfo);
    		}
    	
    	
    	 
    	model.addAttribute("bookList", booksService.getBookList());
    	return "home";
    	
    }

}
