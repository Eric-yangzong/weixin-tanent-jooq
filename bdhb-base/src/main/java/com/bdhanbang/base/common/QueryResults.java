package com.bdhanbang.base.common;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: QueryResults
 * @Description:
 * @author yangxz
 * @date 2018年7月14日 下午4:57:33
 * 
 * @param <T>
 */
public final class QueryResults<T> implements Serializable {

	private static final long serialVersionUID = -4591506147471300909L;

	private final Long limit, offset, total;

	private final List<T> results;

	public QueryResults() {
		this.limit = 0L;
		this.offset = 0L;
		this.total = 0L;
		this.results = null;
	}

	/**
	 * Create a new {@link QueryResults} instance
	 *
	 * @param results
	 *            paged results
	 * @param limit
	 *            used limit
	 * @param offset
	 *            used offset
	 * @param total
	 *            total result rows count
	 */
	public QueryResults(List<T> results, Long limit, Long offset, Long total) {
		this.limit = limit != null ? limit : Long.MAX_VALUE;
		this.offset = offset != null ? offset : 0L;
		this.total = total;
		this.results = results;
	}

	/**
	 * Get the results in List form
	 *
	 * An empty list is returned for no results.
	 *
	 * @return results
	 */
	public List<T> getResults() {
		return results;
	}

	/**
	 * Get the total number of results
	 *
	 * @return total rows
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Return whether there are results in the current query window
	 *
	 * @return true, if no results where found
	 */
	public boolean isEmpty() {
		return results.isEmpty();
	}

	/**
	 * Get the limit value used for the query
	 *
	 * @return applied limit
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * Get the offset value used for the query
	 *
	 * @return applied offset
	 */
	public long getOffset() {
		return offset;
	}

}
