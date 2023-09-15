package com.kivi.sys.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kivi.framework.util.kit.CollectionKit;

import lombok.Getter;

/**
 * 用户类型，0-超级管理员、1- 系统管理员、2-业务管理员、3-密钥操作员、4-密钥审核员、5-审计管理员、6-审计员
 *
 * @author xueqi
 *
 */
@Getter
public enum KmsUserType {
	SYSTEM(0, "超级管理员"),
		ADMIN_SYS(1, "系统管理员"),
		ADMIN_BIZ(2, "业务管理员"),
		OPERATOR_KEY(3, "密钥操作员"),
		REVIEWER_KEY(4, "密钥审核员"),
		ADMIN_AUDIT(5, "密钥审核员"),
		AUDITOR(6, "密钥审核员"),
		API_USER(99, "API用户");

	private final static KmsUserType	ADMIN_SYS_CHILDREN[]	= { ADMIN_BIZ, OPERATOR_KEY };
	private final static KmsUserType	ADMIN_BIZ_CHILDREN[]	= { OPERATOR_KEY, REVIEWER_KEY };
	private final static KmsUserType	ADMIN_AUDIT_CHILDREN[]	= { AUDITOR };

	private final int					code;
	private final String				desc;

	private KmsUserType(int code, String desc) {
		this.code	= code;
		this.desc	= desc;
	}

	public static KmsUserType valueOf(int code) {
		Optional<KmsUserType> op = Arrays.asList(KmsUserType.values()).stream().filter(s -> s.code == code).findAny();

		return op.isPresent() ? op.get() : null;
	}

	public List<Integer> children() {
		List<Integer> result = null;

		if (KmsUserType.SYSTEM.code == this.code) {
			result = Arrays.asList(KmsUserType.values()).stream().map(KmsUserType::getCode)
					.collect(Collectors.toList());
		} else if (KmsUserType.ADMIN_SYS.code == this.code) {
			result = Arrays.asList(ADMIN_SYS_CHILDREN).stream().map(KmsUserType::getCode).collect(Collectors.toList());
		} else if (KmsUserType.ADMIN_BIZ.code == this.code) {
			result = Arrays.asList(ADMIN_BIZ_CHILDREN).stream().map(KmsUserType::getCode).collect(Collectors.toList());
		} else if (KmsUserType.ADMIN_AUDIT.code == this.code) {
			result = Arrays.asList(ADMIN_AUDIT_CHILDREN).stream().map(KmsUserType::getCode)
					.collect(Collectors.toList());
		} else {
			result = CollectionKit.newArrayList(-1);
		}

		return result;
	}

}
