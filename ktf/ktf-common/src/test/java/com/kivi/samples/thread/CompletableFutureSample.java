package com.kivi.samples.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CompletableFutureSample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("CompletableFuture示例开始");
		long						s			= System.currentTimeMillis();
		TestTask					t			= new TestTask();

		// runAsync用于执行没有返回值的异步任务
		CompletableFuture<Void>		future0		= CompletableFuture.runAsync(t::zero).exceptionally(e -> {
													System.out.println("Zero出错！");
													return null;
												});																		// 这里是异常处理，指的是该异步任务执行中出错，应该做的处理

		// supplyAsync方法用于执行带有返回值的异步任务
		CompletableFuture<String>	futureA		= CompletableFuture.supplyAsync(t::a).exceptionally(e -> {
													System.out.println("方法A出错！");
													return null;
												});

		// thenCompose方法用于连接两个CompletableFuture任务，如下代表futureA结束后将执行结果交由另外一个CompletableFuture处理，然后将执行链路最终赋值给futureB
		CompletableFuture<String>	futureB		= futureA.thenCompose(a -> CompletableFuture.supplyAsync(() -> t.b(a)))
				.exceptionally(e -> {
															System.out.println("方法B出错！");
															return null;
														});

		// thenAccept方法用于将一个任务的结果，传给需要该结果的任务，如下表示futureD的执行需要futureA的结果，与thenApply不同的是，这个方法没有有返回值
		CompletableFuture<Void>		futureD		= futureA.thenAccept(t::d);

		// thenApply方法用于将一个任务的结果，传给需要该结果的任务，如下表示futureE的执行需要futureA的结果，与thenAccept不同的是，这个方法有返回值
		CompletableFuture<String>	futureE		= futureA.thenApply(t::e).exceptionally(e -> {
													System.out.println("方法E出错！");
													return null;
												});

		/**
		 * thenApply方法概念容易与thenCompose混淆，毕竟最终目的很相似
		 */

		// thenCombine方法用于连接多个异步任务的结果，如下ab方法需要futureA和futureB的执行结果，那么就可以使用thenCombine进行连接
		// 注意，执行到ab这里，说明futureA和futureB一定已经执行完了
		CompletableFuture<String>	futureAB	= futureA.thenCombine(futureB, t::ab).exceptionally(e -> {
													System.out.println("方法AB出错！");
													return null;
												});

		// 单纯的一个异步任务，不依赖任何其他任务
		CompletableFuture<String>	futureC		= CompletableFuture.supplyAsync(t::c).exceptionally(e -> {
													System.out.println("方法C出错！");
													return null;
												});

		// 指定线程池的一个异步任务，不依赖任何其他任务
		Executor					executor	= Executors.newFixedThreadPool(3, new ThreadFactory() {
													int count = 1;

													@Override
													public Thread newThread(Runnable runnable) {
														return new Thread(runnable, "custom-executor-" + count++);

													}

												});
		CompletableFuture<String>	futureF		= CompletableFuture.supplyAsync(t::f, executor).exceptionally(e -> {
													System.out.println("方法F出错！");
													return null;
												}).whenComplete((u, e) -> {
													System.out.println("u:" + u);
													System.out.println("e:" + e);
												});

		// allOf如果阻塞结束则表示所有任务都执行结束了
		CompletableFuture.allOf(future0, futureA, futureB, futureAB, futureC, futureD, futureE, futureF).get();

		System.out.println("方法Zero输出：" + future0.get());
		System.out.println("方法A输出：" + futureA.get());
		System.out.println("方法B输出：" + futureB.get());
		System.out.println("方法AB输出：" + futureAB.get());
		System.out.println("方法C输出：" + futureC.get());
		System.out.println("方法D输出：" + futureD.get());
		System.out.println("方法E输出：" + futureE.get());
		System.out.println("方法F输出：" + futureF.get());
		System.out.println("耗时：" + (System.currentTimeMillis() - s) + "ms");

	}

}
