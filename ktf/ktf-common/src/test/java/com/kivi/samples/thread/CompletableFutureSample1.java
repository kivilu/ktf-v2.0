package com.kivi.samples.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureSample1 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		TestTask				t			= new TestTask();

		long					s			= System.currentTimeMillis();
		CompletableFuture<Void>	voidFuture	= CompletableFuture.supplyAsync(() -> {
												return t.zero1();
											}).thenApply(i -> {
												return t.a();
											}).thenApply(a -> {
												return t.b(a);
											}).thenAccept(b -> {
												System.out.println(b);
												System.out.println("耗时：" + (System.currentTimeMillis() - s) + "ms");
											}).exceptionally(e -> {
												e.printStackTrace();
												return null;
											});

		voidFuture.get();

		CompletableFuture<String>	future0		= CompletableFuture.supplyAsync(t::zero1).exceptionally(e -> {
													System.out.println("Zero出错！");
													return null;
												});
		CompletableFuture<String>	futureAB	= CompletableFuture.supplyAsync(() -> {
													return t.a();
												}).thenCompose(a -> CompletableFuture.supplyAsync(() -> t.b(a)))
				.exceptionally(e -> {
					System.out.println("方法B出错！");
					return null;
				});
		long						s1			= System.currentTimeMillis();
		CompletableFuture<Void>		allOfFuture	= CompletableFuture.allOf(future0, futureAB);

		System.out.println(allOfFuture.get());
		System.out.println("耗时：" + (System.currentTimeMillis() - s1) + "ms");
	}

}
