package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class BookDetailsInfo {

    private int bookId;

    private String title;

    private String author;

    private String publisher;

    private String thumbnailUrl;

    private String thumbnailName;
    
    private String publishDate;
    
    private String thumbnail;
    
    private String isbn;
    
    private String explanation;
    
    private String status;
    
    private java.sql.Date reg;
    
    private java.sql.Date upd;

    public BookDetailsInfo() {

    }

    public BookDetailsInfo(int bookId, String title, String author, String publisher,
            String thumbnailUrl, String thumbnailName, String publishDate, String thumbnail, String isbn, String explanation, java.sql.Date reg, java.sql.Date upd) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailName = thumbnailName;
        this.publishDate = publishDate;
        this.thumbnail = thumbnail;
        this.isbn = isbn;
        this.explanation = explanation;
        this.reg = reg;
        this.upd = upd;
        
        
    }

}