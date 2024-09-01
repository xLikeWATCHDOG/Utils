package cn.watchdog.auth;

import cn.watchdog.core.CaffeineFactory;
import cn.watchdog.core.service.VariableReplacementService;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class AuthApplication implements SmartLifecycle {
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

	private final OkHttpClient client = new OkHttpClient();
	private boolean running = false;
	@Autowired
	private VariableReplacementService variableReplacementService;
	@Autowired
	private ApplicationContext applicationContext;

	public boolean checkLicense() {
		String url = "https://raw.githubusercontent.com/xLikeWATCHDOG/resources/main/licenses/LICENSE-COMMON";
		// 根目录下是否存在 LICENSE 文件
		Path license = Paths.get("LICENSE");
		log.info("License file: {}", license.toAbsolutePath());
		// 获取远程 LICENSE 文件内容
		Request request = new Request.Builder().url(url).build();
		try {
			String remoteLicense = client.newCall(request).execute().body().string();
			remoteLicense = variableReplacementService.replaceVariables(remoteLicense);
			// 如果本地不存在 LICENSE 文件，将远程 LICENSE 文件内容写入本地 LICENSE 文件
			if (!Files.exists(license)) {
				Files.writeString(license, remoteLicense);
				return false;
			}
			// 获取远程 LICENSE 的版本号 version=0.0.1
			String remoteVersion = remoteLicense.substring(remoteLicense.indexOf("version=") + 8, remoteLicense.indexOf("\n"));
			// 本地 LICENSE 文件内容
			String localLicense = Files.readString(license);
			// 本地 LICENSE 的版本号
			String localVersion = localLicense.substring(localLicense.indexOf("version=") + 8, localLicense.indexOf("\n"));
			boolean eula = localLicense.contains("license=true");
			// 比较两个 LICENSE 文件的版本号是否一致
			if (!remoteVersion.equals(localVersion)) {
				if (eula) {
					remoteLicense = remoteLicense.replace("license=false", "license=true");
				}
				// 如果不一致，将远程 LICENSE 文件内容写入本地 LICENSE 文件
				Files.writeString(license, remoteLicense);
				log.error("Your LICENSE file is outdated, now it has been updated.");
				return eula;
			}
			// 如果一致，返回 判断本地 license=true 是否存在
			return eula;
		} catch (Exception e) {
			log.error("Failed to check license: {}", e.getMessage());
		}
		return false;
	}

	@Override
	public void start() {
		boolean license = checkLicense();
		if (!license) {
			log.error("Please read the LICENSE file and set the license=true in the LICENSE file.");
			((ConfigurableApplicationContext) applicationContext).close();
		}
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}
}
