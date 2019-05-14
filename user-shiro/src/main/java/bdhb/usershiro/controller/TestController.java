package bdhb.usershiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	/**
	 * 没有加shiro权限注解
	 * 
	 * @return
	 */
	@RequestMapping(value = "test1", method = RequestMethod.GET)
	public String test1() {
		return "test1";
	}

	/**
	 * 添加了shiro权限注解，需要用户有"systemUserAdd"权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "test2", method = RequestMethod.GET)
	@RequiresPermissions("systemUserAdd")
	public String test2() {
		return "test2";
	}

	@RequestMapping(value = "test3", method = RequestMethod.GET)
	@RequiresPermissions("systemUserAdd1")
	public String test3() {
		return "test3";
	}
}
