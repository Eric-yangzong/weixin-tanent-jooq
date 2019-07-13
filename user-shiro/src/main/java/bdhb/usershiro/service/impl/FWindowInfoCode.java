package bdhb.usershiro.service.impl;

import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.Schema;
import org.jooq.impl.AbstractRoutine;

public class FWindowInfoCode extends AbstractRoutine<String> {

	private static final long serialVersionUID = 1910412330;

	/**
	 * The parameter <code>tat0004_mod_shiro.f_window_info_code.RETURN_VALUE</code>.
	 */
	public static final Parameter<String> RETURN_VALUE = createParameter("RETURN_VALUE",
			org.jooq.impl.SQLDataType.VARCHAR, false, false);

	/**
	 * The parameter <code>tat0004_mod_shiro.f_window_info_code.tenant</code>.
	 */
	public static final Parameter<String> TENANT = createParameter("tenant", org.jooq.impl.SQLDataType.VARCHAR, false,
			false);

	/**
	 * Create a new routine call instance
	 */
	public FWindowInfoCode(Schema schema) {
		super("f_window_info_code", schema, org.jooq.impl.SQLDataType.VARCHAR);

		setReturnParameter(RETURN_VALUE);
		addInParameter(TENANT);
	}

	/**
	 * Set the <code>tenant</code> parameter IN value to the routine
	 */
	public void setTenant(String value) {
		setValue(TENANT, value);
	}

	/**
	 * Set the <code>tenant</code> parameter to the function to be used with a
	 * {@link org.jooq.Select} statement
	 */
	public void setTenant(Field<String> field) {
		setField(TENANT, field);
	}
}
