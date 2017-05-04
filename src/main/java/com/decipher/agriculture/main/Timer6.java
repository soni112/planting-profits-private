package com.decipher.agriculture.main;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Timer6 {

	static int a = 0;
	static Callable<Object> complicatedCalculation = new Callable<Object>() {
		@Override
		public Object call() throws Exception {
			Thread.sleep(1);
			return "42";
		}
	};

	public static void main(final String[] args) {
		final ExecutorService service = Executors.newCachedThreadPool();
		try {
			System.out.println("In try");
			final Future<Object> f = service.submit(complicatedCalculation);
			f.get(1, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			System.err.println("Calculation took to long");
		} catch (Exception e) {
			System.out.println("exception " + e);
			// throw new RuntimeException(e);
		}
		System.out.println("continue ");
	}
}