package bdhb.usershiro.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bdhanbang.base.exception.AuthenticationException;
import com.bdhanbang.base.exception.ForbiddenException;

public class JwtUtils {

	// 系统密钥
	private static final String SECRET = "EvW47A58";

	// 系统名称
	private static final String ISSUER = "sys.name";

	/**
	 * 生成token
	 *
	 * @param claims
	 * @return
	 */
	public static String createToken(Map<String, String> claims) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER)
					// 设置过期时间为2小时
					.withExpiresAt(DateUtils.addHours(new Date(), 2));
			claims.forEach(builder::withClaim);
			return builder.sign(algorithm);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			throw new AuthenticationException("生成token失败", e);
		}
	}

	/**
	 * 验证jwt，并返回数据
	 */
	public static Map<String, String> verifyToken(String token) {
		Algorithm algorithm;
		Map<String, Claim> map;

		try {
			algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
			DecodedJWT jwt = verifier.verify(token);
			map = jwt.getClaims();
		} catch (Exception e) {
			throw new ForbiddenException("鉴权失败", e);
		}

		Map<String, String> resultMap = new HashMap<>(map.size());
		map.forEach((k, v) -> resultMap.put(k, v.asString()));
		return resultMap;
	}
}
