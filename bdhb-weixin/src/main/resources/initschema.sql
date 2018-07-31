#建立schema 【_mod_login】各个业务模块自定义
schema.create=CREATE SCHEMA IF NOT EXISTS ${tenantid}_mod_login;
#建立数据库表		
schema.table=CREATE TABLE IF NOT EXISTS ${tenantid}_mod_login.T_MY_LOGIN (\
					  id          uuid NOT NULL,\
					  user_code   varchar(100) NOT NULL,\
					  user_name   varchar(300) NOT NULL,\
					  login_type  VARCHAR(50) NOT NULL,\
					  login_time  timestamp without time zone NOT NULL,\
					  login_ip    varchar(200),\
					  "jsonb"     jsonb,\
					  CONSTRAINT my_login_pkey \
					    PRIMARY KEY (id)\
					) WITH (\
					    OIDS = FALSE\
					  );
#向数据库中写入数据
#schema.insert=insert into ${tenantid}_mod_log.t_log_login(id,\
			  user_code,user_name,login_type,login_time,login_ip,jsonb) \
			  values('65291cfd-6dd6-400c-9cbd-2210cb2bc9e2','admin_code',\
			  'admin',1,now(),'192.168.1.1','{"aa":"bbb"}');
#建立视图		
#schema.index=CREATE INDEX IF NOT EXISTS IDX_APP_CODE ON \
		${tenantid}_mod_log.T_LOG_OPERATION (APP_CODE);\
		CREATE INDEX IF NOT EXISTS IDX_APP_NAME ON \
		${tenantid}_mod_log.T_LOG_OPERATION (APP_NAME);\
		CREATE INDEX IF NOT EXISTS IDX_MODULE_ID ON \
		${tenantid}_mod_log.T_LOG_OPERATION (MODULE_ID);\
		CREATE INDEX IF NOT EXISTS IDX_USER_CODE ON \
		${tenantid}_mod_log.T_LOG_OPERATION (USER_CODE);\
		CREATE INDEX IF NOT EXISTS IDX_USER_NAME ON \
		${tenantid}_mod_log.T_LOG_OPERATION (USER_NAME);\
		CREATE INDEX IF NOT EXISTS IDX_OPT_TIME ON \
		${tenantid}_mod_log.T_LOG_OPERATION (OPT_TIME);\
		CREATE INDEX IF NOT EXISTS IDX_QUOTE_ADDR ON \
		${tenantid}_mod_log.T_LOG_OPERATION (QUOTE_ADDR);\
		CREATE INDEX IF NOT EXISTS IDX_OPT_MARK ON \
		${tenantid}_mod_log.T_LOG_OPERATION (OPT_MARK);

#删除schema	
schema.drop=DROP TABLE IF EXISTS ${tenantid}_mod_login.T_MY_LOGIN;