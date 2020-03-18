/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.kivi.db.fill;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.dto.JwtUserDTO;
import com.kivi.framework.service.IJwtUserServie;

/**
 * 通用填充类 适用于mybatis plus
 *
 * @author Caratacus
 */
public class CommonMetaObjectHandler implements MetaObjectHandler {

	/**
	 * 创建时间
	 */
	private final String	gmtCreate	= "gmtCreate";
	/**
	 * 修改时间
	 */
	private final String	gmtUpdate	= "gmtUpdate";
	/**
	 * 创建者ID
	 */
	private final String	createUid	= "createUid";

	/**
	 * 创建者用户名
	 */
	private final String	createUser	= "createUser";

	/**
	 * 修改者ID
	 */
	private final String	updateUid	= "updateUid";

	/**
	 * 修改者用户名
	 */
	private final String	updateUser	= "updateUser";

	private IJwtUserServie	iJwtUserServie;

	@Override
	public void insertFill(MetaObject metaObject) {
		setInsertFieldValByName(gmtCreate, LocalDateTime.now(), metaObject);
		setInsertFieldValByName(createUid, currentUid(), metaObject);
		setInsertFieldValByName(gmtUpdate, LocalDateTime.now(), metaObject);
		setInsertFieldValByName(updateUid, currentUid(), metaObject);
		setInsertFieldValByName(createUser, currentUser(), metaObject);
		setInsertFieldValByName(updateUser, currentUser(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		setUpdateFieldValByName(gmtUpdate, LocalDateTime.now(), metaObject);
		setUpdateFieldValByName(updateUid, currentUid(), metaObject);
		setInsertFieldValByName(updateUser, currentUser(), metaObject);
	}

	/**
	 * 获取当前用户ID
	 */
	private Long currentUid() {
		Long uid = null;
		try {
			IJwtUserServie iJwtUserServie = iJwtUserServie();
			if (iJwtUserServie != null) {
				JwtUserDTO jwtUser = iJwtUserServie.getLoginUser();
				uid = jwtUser != null ? jwtUser.getId() : null;
			}
		} catch (Exception ignored) {
		}
		return uid;
	}

	private String currentUser() {
		String user = null;
		try {
			IJwtUserServie iJwtUserServie = iJwtUserServie();
			if (iJwtUserServie != null) {
				JwtUserDTO jwtUser = iJwtUserServie.getLoginUser();
				user = jwtUser != null ? jwtUser.getIdentifier() : null;
			}
		} catch (Exception ignored) {
		}

		return user;
	}

	private IJwtUserServie iJwtUserServie() {
		if (this.iJwtUserServie == null)
			this.iJwtUserServie = SpringContextHolder.getBean(IJwtUserServie.class);

		return this.iJwtUserServie;
	}

}