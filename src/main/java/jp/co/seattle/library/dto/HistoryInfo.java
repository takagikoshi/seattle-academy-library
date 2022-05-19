package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍貸出履歴情報格納DTO
 *
 */
@Configuration
@Data

public class HistoryInfo {

	private java.sql.Date reg;

	private java.sql.Date upd;

	private String title;

	private int bookId;

}
