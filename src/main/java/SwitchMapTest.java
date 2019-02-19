package main.java;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class SwitchMapTest {
	public static void main(String[] args) throws InterruptedException {
		final List<String> items = Arrays.asList("a", "b", "c", "d", "e", "f");

		Observable.fromArray(items)
				.flatMapIterable(list -> list)
				.switchMap(s -> {
					final int delay = new Random().nextInt(10);
					return Observable.just(s + "x").delay(delay, TimeUnit.MILLISECONDS);
				})
				.toList()
				.doOnSuccess(System.out::println)
				.subscribe();
		//return [fx]

		Observable.fromArray(items)
				.flatMapIterable(list -> list)
				.flatMap(s -> {
					final int delay = new Random().nextInt(10);
					return Observable.just(s + "x").delay(delay, TimeUnit.MILLISECONDS);
				})
				.toList()
				.doOnSuccess(System.out::println)
				.subscribe();
		//return [dx, ex, fx, ax, cx, bx]
		Thread.sleep(5000);

	}
}
