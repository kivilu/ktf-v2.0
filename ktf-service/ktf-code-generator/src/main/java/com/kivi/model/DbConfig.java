package com.kivi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author jackcooper
 */
@Entity
@Data
@Table(name = "db_config")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class DbConfig implements Serializable {
	private static final long	serialVersionUID	= 3148176768559230877L;

	/** 数据库 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long				id;
	private String				dbName;
	private String				url					= "";
	private String				driver				= "";					// com.mysql.jdbc.Driver
	private String				username			= "";
	private String				password			= "";
	private String				schema;
	private String				catalog;
	private String				dbType;

}
