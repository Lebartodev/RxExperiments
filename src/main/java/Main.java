package main.java;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		BehaviorRelay<Long> test = BehaviorRelay.create();
		test.subscribe(System.out::println, System.out::println, () -> System.out.println("cin"));

		Observable.interval(10, 10, TimeUnit.MILLISECONDS, Schedulers.io())
				.flatMap(intervalValue -> Observable.defer(() -> {
					Random random = new Random();
					int i = random.nextInt(200);
					if (i > 150) {
						throw new NullPointerException();
					}
					return Observable.just(intervalValue);
				})/*.onErrorResumeNext(Observable.empty())*/)
				.subscribe(test, System.out::println);
		Thread.sleep(5000);
		test.accept(1L);
		test.accept(1L);
		test.accept(2L);
		test.accept(4L);
		test.accept(3L);
		test.accept(1L);
		test.accept(2L);
		Thread.sleep(5000);
	}


}
