package cn.watchdog.core.enums;

import lombok.Getter;

@Getter
public enum UserAgreementVariable {
	PROJECT("{PROJECT}", "项目名称"),
	INTRODUCTION("{INTRODUCTION}", "项目介绍"),
	SOFTWARE_PURPOSE("{SOFTWARE_PURPOSE}", "娱乐性质"),
	FEATURES("{FEATURES}", "软件提供的功能"),
	LEGAL_SCOPE("{LEGAL_SCOPE}", "法律允许的范围"),
	DEVELOPER("{DEVELOPER}", "开发者"),
	POTENTIAL_ISSUES("{POTENTIAL_ISSUES}", "可能出现的问题"),
	ENVIRONMENT("{ENVIRONMENT}", "生产环境"),
	ISSUE_SEVERITY("{ISSUE_SEVERITY}", "严重BUG"),
	CONTACT_METHOD("{CONTACT_METHOD}", "联系开发者"),
	STABILITY_AND_SECURITY("{STABILITY_AND_SECURITY}", "软件的稳定性和安全性"),
	RISKS("{RISKS}", "安全风险"),
	REVERSE_ENGINEERING_POLICY("{REVERSE_ENGINEERING_POLICY}", "严令禁止"),
	OFFICIAL_CHANNELS("{OFFICIAL_CHANNELS}", "官方渠道"),
	TARGET_AUDIENCE("{TARGET_AUDIENCE}", "非中国大陆地区用户"),
	EXCLUDED_AUDIENCE("{EXCLUDED_AUDIENCE}", "中国大陆地区用户");

	private final String placeholder;
	private final String value;

	UserAgreementVariable(String placeholder, String value) {
		this.placeholder = placeholder;
		this.value = value;
	}

}
