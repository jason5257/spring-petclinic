package org.springframework.samples.petclinic.system;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.util.Locale;

public class P6spySqlFormatConfiguration implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {
		return elapsed + "ms | " + url + "\n" + formatSql(category, sql);
	}

	private String formatSql(String category, String sql) {
		if (sql == null || sql.trim().equals(""))
			return sql;
		// Only format Statement, distinguish DDL And DML
		if (Category.STATEMENT.getName().equals(category)) {
			String tempSql = sql.trim().toLowerCase(Locale.ROOT);
			if (tempSql.startsWith("create") || tempSql.startsWith("alter") || tempSql.startsWith("comment")) {
				sql = FormatStyle.DDL.getFormatter().format(sql);
			}
			else {
				sql = isNone(sql) ? FormatStyle.NONE.getFormatter().format(sql)
						: FormatStyle.BASIC.getFormatter().format(sql);
			}
		}
		return sql;
	}

	// mybatis 주석으로 판단하여 구분
	// 간혹 mybatis 문법 파싱 오류로 인해 mybatis query일 경우에는 그대로 출력하기 위한 판단
	private boolean isNone(String sql) {
		return !StringUtil.isNull(sql) && sql.contains("/*") && sql.contains("*/");
	}

}
