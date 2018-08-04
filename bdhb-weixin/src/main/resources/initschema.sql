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
					  );\
		     CREATE TABLE IF NOT EXISTS ${tenantid}_mod_login.T_WE_XIN_OKAPI (\
			  id          uuid NOT NULL,\
			  tanentid    varchar(100) NOT NULL,\
			  app_id      varchar(100) NOT NULL,\
			  app_secret  varchar(100) NOT NULL,\
			  user_name   varchar(100) NOT NULL,\
			  "password"  varchar(100) NOT NULL,\
			  note        varchar(500),\
			  "jsonb"     jsonb,\
			  CONSTRAINT t_we_xin_okapi_pkey \
			    PRIMARY KEY (id)\
			) WITH (\
			    OIDS = FALSE\
			  );\
			  CREATE TABLE IF NOT EXISTS  ${tenantid}_mod_login.T_WE_XIN_USERINFO (\
			  id          uuid NOT NULL,\
			  open_id     varchar(100) NOT NULL,\
			  nick_name   varchar(100),\
			  gender      integer,\
			  "language"  varchar(100),\
			  city        varchar(100),\
			  province    varchar(100),\
			  country     varchar(100),\
			  avatar_url  varchar(500),\
			  watermark   jsonb,\
			  CONSTRAINT t_we_xin_userinfo_pkey \
			    PRIMARY KEY (id)\
			) WITH (\
			    OIDS = FALSE\
			  );
