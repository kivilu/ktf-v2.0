package com.kivi.db.model;

import com.kivi.db.model.convert.Convert;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 主键父类
 * </p>
 *
 * @author Caratacus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseEntity extends Convert {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	protected Long				id;

}
