package cn.watchdog.core.service;

import cn.watchdog.core.enums.UserAgreementVariable;

public interface VariableReplacementService {
	void setVariable(UserAgreementVariable userAgreementVariable, String value);

	// 通过变量名获取变量值
	String getVariable(UserAgreementVariable userAgreementVariable);

	String replaceVariables(String content);
}
