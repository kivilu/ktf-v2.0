package com.kivi.dashboard.sys.entity;

import lombok.Data;

@Data
public class SysLogEx {

	private long	startTime;

	private long	totalMillis;

	private SysLog	sysLog;

	public SysLogEx() {
		this.startTime	= System.currentTimeMillis();
		this.sysLog		= new SysLog();
	}

}
