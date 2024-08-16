package cn.watchdog;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsApplication {
	@PostConstruct
	public void init() {
		log.info("UtilsApplication init.");
	}
}
