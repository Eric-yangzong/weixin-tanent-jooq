package com.bdhanbang.weixin.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdhanbang.weixin.configuration.SchemaConf;

/**
 * @ClassName: SchemaService
 * @Description: 数据库用户操作
 * @author yangxz
 * @date 2018年7月10日 上午8:27:37
 * 
 */
@Service
public class SchemaService {

	private static Logger log = LoggerFactory.getLogger(SchemaService.class);

	@Autowired
	DataSource ds;

	@Autowired
	SchemaConf schemaConf;

	String regexTenantId = "\\$\\{tenantid\\}";

	/**
	 * @Title: initSchema
	 * @Description: 根据schema名称初始化数据库
	 * @param @param
	 *            schema 设定文件
	 * @return void 返回类型
	 * @throws:
	 */
	public void initSchema(String tenantId) {

		Connection conn = null;
		Statement statement = null;

		try {
			conn = ds.getConnection();

			// 启用事务
			conn.setAutoCommit(false);

			statement = conn.createStatement();

			// 建schema
			String createSql = schemaConf.getCreate();// 得到sql

			if (!(createSql == null || "".equals(createSql))) {
				createSql = createSql.replaceAll(regexTenantId, tenantId);// 用传入的字符串替占位符
				log.info(String.format("create-->%s", createSql));
				statement.execute(createSql);// 执行
			}

			// 建表
			String tableSql = schemaConf.getTable();// 得到sql
			if (!(tableSql == null || "".equals(tableSql))) {
				tableSql = tableSql.replaceAll(regexTenantId, tenantId);// 用传入的字符串替占位符
				log.info(String.format("table-->%s", tableSql));
				statement.execute(tableSql);// 执行
			}

			// 建表
			String insertSql = schemaConf.getInsert();// 得到sql
			if (!(insertSql == null || "".equals(insertSql))) {
				insertSql = insertSql.replaceAll(regexTenantId, tenantId);// 用传入的字符串替占位符
				log.info(String.format("insert-->%s", insertSql));
				statement.execute(insertSql);// 执行
			}

			// 建索引
			String indexSql = schemaConf.getIndex();
			if (!(indexSql == null || "".equals(indexSql))) {
				indexSql = indexSql.replaceAll(regexTenantId, tenantId);
				log.info(String.format("index-->%s", indexSql));
				statement.execute(indexSql);// 执行

			}

			// 提交事务并设置事务为自动提交
			conn.commit();
			conn.setAutoCommit(true);

			statement.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {

				// 关掉执行用的东西
				if (!Objects.isNull(statement)) {
					statement.close();
					statement = null;
				}

				if (!Objects.isNull(conn)) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		}
	}

	public void dropSchema(String schema) {
		Connection conn = null;
		Statement statement = null;

		try {
			conn = ds.getConnection();

			statement = conn.createStatement();

			// 建schema
			String dropSql = schemaConf.getDrop();// 得到sql

			if (!(dropSql == null || "".equals(dropSql))) {
				dropSql = dropSql.replaceAll(regexTenantId, schema);// 用传入的字符串替占位符
				log.info(String.format("drop-->%s", dropSql));
				statement.execute(dropSql);// 执行
			}

			statement.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {

				// 关掉执行用的东西
				if (!Objects.isNull(statement)) {
					statement.close();
					statement = null;
				}

				if (!Objects.isNull(conn)) {
					conn.close();
					conn = null;
				}

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

		}
	}
}
