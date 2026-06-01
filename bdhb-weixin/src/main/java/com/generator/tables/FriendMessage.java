package com.generator.tables;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

public class FriendMessage extends TableImpl<Record> {

	private static final long serialVersionUID = 1L;

	public final TableField<Record, UUID> ID = createField("id", SQLDataType.UUID, this, "");
	public final TableField<Record, String> FROM_USER_ID = createField("from_user_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> TO_USER_ID = createField("to_user_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> CONTENT = createField("content", SQLDataType.CLOB, this, "");
	public final TableField<Record, LocalDateTime> SEND_TIME = createField("send_time", SQLDataType.LOCALDATETIME, this,
			"");
	public final TableField<Record, Short> IS_SEND = createField("is_send", SQLDataType.SMALLINT, this, "");
	public final TableField<Record, Short> IS_DEL = createField("is_del", SQLDataType.SMALLINT, this, "");
	public final TableField<Record, Short> IS_BACK = createField("is_back", SQLDataType.SMALLINT, this, "");

	public FriendMessage() {
		super("friend_message");
	}
}
