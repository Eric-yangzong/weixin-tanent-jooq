package com.bdhanbang.base.jooq;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.impl.DSL;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @ClassName: PostgresJsonBinding
 * @Description: 字符和实体转换
 * @author yangxz
 * @date 2018年7月14日 下午1:40:38
 * 
 */
public class PostgresJsonBinding implements Binding<Object, JsonNode> {

	/**
	 * @Fields serialVersionUID : 字符和实体转换
	 */

	private static final long serialVersionUID = 1L;

	@Override
	public Converter<Object, JsonNode> converter() {
		return new PostgresJsonNodeConverter();
	}

	@Override
	public void sql(BindingSQLContext<JsonNode> ctx) throws SQLException {

		// This ::json cast is explicitly needed by PostgreSQL:
		ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::json");
	}

	@Override
	public void register(BindingRegisterContext<JsonNode> ctx) throws SQLException {
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<JsonNode> ctx) throws SQLException {
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value()));
	}

	@Override
	public void get(BindingGetResultSetContext<JsonNode> ctx) throws SQLException {
		ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
	}

	@Override
	public void get(BindingGetStatementContext<JsonNode> ctx) throws SQLException {
		ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
	}

	// The below methods aren't needed in PostgreSQL:

	@Override
	public void set(BindingSetSQLOutputContext<JsonNode> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetSQLInputContext<JsonNode> ctx) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}
}