package com.kivi.dashboard.sys.entity;

import lombok.Data;

@Data
public class SysLogEx {

	private final long		startTime;
	private final SysLog	sysLog;

	private long			totalMillis;

	public SysLogEx() {
		this.startTime	= System.currentTimeMillis();
		this.sysLog		= new SysLog();
	}

}
