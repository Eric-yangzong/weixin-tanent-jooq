package com.bdhanbang.weixin.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SchemaConf
 * @Description: 取得配置文件中的sql语句
 * @author yangxz
 * @date 2018年7月12日 下午4:35:49
 * 
 */
@Component
@PropertySource("classpath:initschema.sql")
@ConfigurationProperties(prefix = "schema")
public class SchemaConf {

	public String create;

	public String table;

	public String insert;

	public String index;

	public String drop;

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getDrop() {
		return drop;
	}

	public void setDrop(String drop) {
		this.drop = drop;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
