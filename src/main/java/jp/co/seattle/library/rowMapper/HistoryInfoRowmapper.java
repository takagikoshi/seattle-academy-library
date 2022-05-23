package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.HistoryInfo;

@Configuration
public class HistoryInfoRowmapper implements RowMapper<HistoryInfo> {
	@Override
	public HistoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Query結果（ResultSet rs）を、オブジェクトに格納する実装
		HistoryInfo history = new HistoryInfo();

		// bookInfoの項目と、取得した結果(rs)のカラムをマッピングする
		history.setReg(rs.getDate("reg_date"));
		history.setUpd(rs.getDate("upd_date"));
		history.setTitle(rs.getString("title"));
		history.setBookId(rs.getInt("book_id"));

		return history;
	}

}
