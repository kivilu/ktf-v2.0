package com.kivi.constant;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.po.TableFill;

public interface KtfTableFill {
	public String	prop_gmt_Create	= "gmtCreate";
	public String	prop_gmt_Update	= "gmtUpdate";
	public String	prop_createUid	= "createUid";
	public String	prop_updateUid	= "updateUid";
	public String	prop_createUser	= "createUser";
	public String	prop_updateUser	= "updateUser";

	public String	col_GMT_CREATE	= "GMT_CREATE";
	public String	col_GMT_UPDATE	= "GMT_UPDATE";
	public String	col_CREATE_UID	= "CREATE_UID";
	public String	col_UPDATE_UID	= "UPDATE_UID";
	public String	col_CREATE_USER	= "create_user";
	public String	col_UPDATE_USER	= "update_user";

	/**
	 * 获取TableFill策略
	 *
	 * @return
	 */
	public static List<TableFill> propTableFills() {
		// 自定义需要填充的字段
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill(prop_gmt_Create, FieldFill.INSERT));
		tableFillList.add(new TableFill(prop_gmt_Update, FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill(prop_createUid, FieldFill.INSERT));
		tableFillList.add(new TableFill(prop_updateUid, FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill(prop_createUser, FieldFill.INSERT));
		tableFillList.add(new TableFill(prop_updateUser, FieldFill.INSERT_UPDATE));
		return tableFillList;
	}

	public static List<TableFill> colTableFills() {
		// 自定义需要填充的字段
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill(col_GMT_CREATE, FieldFill.INSERT));
		tableFillList.add(new TableFill(col_GMT_UPDATE, FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill(col_CREATE_UID, FieldFill.INSERT));
		tableFillList.add(new TableFill(col_UPDATE_UID, FieldFill.INSERT_UPDATE));
		tableFillList.add(new TableFill(col_CREATE_USER, FieldFill.INSERT));
		tableFillList.add(new TableFill(col_UPDATE_USER, FieldFill.INSERT_UPDATE));
		return tableFillList;
	}
}
