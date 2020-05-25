package com.kivi.samples.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestTask {

//各个方法，sleep当成是执行时间

	public void zero() {
		sleep(100L);
		System.out.println("zero方法触发！\n-----------------------------");
	}

	public String a() {
		sleep(500L);
		return "a";
	}

	public String b(String a) {
		sleep(1000L);
		return a + "b";
	}

	public String c() {
		sleep(500L);
		return "c";
	}

	public String ab(String a, String b) {
		sleep(100L);
		return a + "|" + b;
	}

	public void d(String a) {
		sleep(1000L);
		System.out.println("d方法触发，拿到的a = " + a);
	}

	public String e(String a) {
		sleep(100L);
		return a + "e";
	}

	public String f() {
		sleep(500L);
		return "f";
	}

	private void sleep(long t) {
		LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(t));
	}

}
