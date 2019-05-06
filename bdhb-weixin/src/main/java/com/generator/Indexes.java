/*
 * This file is generated by jOOQ.
*/
package com.generator;


import com.generator.tables.AuthCredentials;
import com.generator.tables.Friend;
import com.generator.tables.FriendMessage;
import com.generator.tables.TMyData;
import com.generator.tables.TMyLogin;
import com.generator.tables.TWeXinOkapi;
import com.generator.tables.TWeXinUserinfo;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>tat0004_mod_login</code> 
 * schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index AUTH_CREDENTIALS_PKEY = Indexes0.AUTH_CREDENTIALS_PKEY;
    public static final Index FRIEND_PKEY = Indexes0.FRIEND_PKEY;
    public static final Index FRIEND_MESSAGE_PKEY = Indexes0.FRIEND_MESSAGE_PKEY;
    public static final Index T_MY_DATA_PKEY = Indexes0.T_MY_DATA_PKEY;
    public static final Index MY_LOGIN_PKEY = Indexes0.MY_LOGIN_PKEY;
    public static final Index T_WE_XIN_OKAPI_PKEY = Indexes0.T_WE_XIN_OKAPI_PKEY;
    public static final Index T_WE_XIN_USERINFO_PKEY = Indexes0.T_WE_XIN_USERINFO_PKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index AUTH_CREDENTIALS_PKEY = Internal.createIndex("auth_credentials_pkey", AuthCredentials.AUTH_CREDENTIALS, new OrderField[] { AuthCredentials.AUTH_CREDENTIALS._ID }, true);
        public static Index FRIEND_PKEY = Internal.createIndex("friend_pkey", Friend.FRIEND, new OrderField[] { Friend.FRIEND.ID }, true);
        public static Index FRIEND_MESSAGE_PKEY = Internal.createIndex("friend_message_pkey", FriendMessage.FRIEND_MESSAGE, new OrderField[] { FriendMessage.FRIEND_MESSAGE.ID }, true);
        public static Index T_MY_DATA_PKEY = Internal.createIndex("t_my_data_pkey", TMyData.T_MY_DATA, new OrderField[] { TMyData.T_MY_DATA.ID }, true);
        public static Index MY_LOGIN_PKEY = Internal.createIndex("my_login_pkey", TMyLogin.T_MY_LOGIN, new OrderField[] { TMyLogin.T_MY_LOGIN.ID }, true);
        public static Index T_WE_XIN_OKAPI_PKEY = Internal.createIndex("t_we_xin_okapi_pkey", TWeXinOkapi.T_WE_XIN_OKAPI, new OrderField[] { TWeXinOkapi.T_WE_XIN_OKAPI.ID }, true);
        public static Index T_WE_XIN_USERINFO_PKEY = Internal.createIndex("t_we_xin_userinfo_pkey", TWeXinUserinfo.T_WE_XIN_USERINFO, new OrderField[] { TWeXinUserinfo.T_WE_XIN_USERINFO.ID }, true);
    }
}
