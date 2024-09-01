package cn.watchdog.core.service.impl;

import cn.watchdog.core.enums.UserAgreementVariable;
import cn.watchdog.core.service.VariableReplacementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class VariableReplacementServiceImpl implements VariableReplacementService {
	// 使用 EnumMap 来存储默认变量值
	private static final Map<UserAgreementVariable, String> VARIABLES = new HashMap<>();

	static {
		for (UserAgreementVariable value : UserAgreementVariable.values()) {
			VARIABLES.put(value, value.getValue());
		}
	}

	@Override
	public void setVariable(UserAgreementVariable userAgreementVariable, String value) {
		VARIABLES.put(userAgreementVariable, value);
	}

	// 通过变量名获取变量值
	@Override
	public String getVariable(UserAgreementVariable userAgreementVariable) {
		return VARIABLES.get(userAgreementVariable);
	}

	@Override
	public String replaceVariables(String content) {
		for (var entry : VARIABLES.entrySet()) {
			// 替换文本中符合变量的所有内容
			content = content.replace(entry.getKey().getPlaceholder(), entry.getValue());
		}
		return content;
	}
}
