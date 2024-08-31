package cn.watchdog.auth;

import cn.watchdog.core.CaffeineFactory;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthApplication {
	@Getter
	private static Logger log;

	static {
		try {
			var ePayBotApplication = Class.forName("javax.security.auth.login.LoginContext");
			log = LoggerFactory.getLogger(ePayBotApplication);
		} catch (ClassNotFoundException e) {
			log = LoggerFactory.getLogger(CaffeineFactory.class);
		}
	}

	@PostConstruct
	public void init() {
		log.info("AuthApplication init.");
	}
}
