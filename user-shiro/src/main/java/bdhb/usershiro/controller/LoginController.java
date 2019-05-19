package bdhb.usershiro.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private SysUserService userService;
    
    public LoginController(SysUserService userService) {
    	this.userService = userService;
    }

    /**
     * 用户名密码登录
     * @param request
     * @return token
     */
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody SysUserEntity loginInfo, HttpServletRequest request, HttpServletResponse response){      
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUserName(), loginInfo.getPassword());
            subject.login(token);
            
            SysUserEntity user = (SysUserEntity) subject.getPrincipal();
            String newToken = userService.generateJwtToken(user.getUserName());
            response.setHeader("x-auth-token", newToken);
            
            return ResponseEntity.ok(newToken);
            
            //return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            logger.error("User {} login fail, Reason:{}", loginInfo.getUserName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
    	Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipals() != null) {
        	SysUserEntity user = (SysUserEntity)subject.getPrincipals().getPrimaryPrincipal();
            userService.deleteLoginInfo(user.getUserName());
        }
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok().build();
    }

}
