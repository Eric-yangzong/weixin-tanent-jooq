package bdhb.usershiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	/**
	 * 没有加shiro权限注解
	 * 
	 * @return
	 */
	@RequestMapping("/test1")
	public String test1() {
		return "test1";
	}

	/**
	 * 添加了shiro权限注解，需要用户有"systemUserAdd"权限
	 * 
	 * @return
	 */
	@RequestMapping("/test2")
	@RequiresPermissions("systemUserAdd")
	public String test2() {
		return "test2";
	}
}
