package com.kivi.dao;

import com.kivi.model.DbConfig;
import com.kivi.model.TableInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeDao {
	
	public void saveComment(TableInfo tableInfo, DbConfig dbConfig);
	
	public List<TableInfo> getAllTables(DbConfig dbConfig);
	
	public TableInfo getAllColumns(String tableName, DbConfig dbConfig);

	public String testConnection(DbConfig dbConfig);

}
