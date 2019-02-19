import java.util.concurrent.TimeUnit;

import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Main {
	public static void main(String[] args) {
		BehaviorRelay<Long> test = BehaviorRelay.create();
		test.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));

		Observable.interval(10, 200, TimeUnit.MILLISECONDS, Schedulers.io())
				.flatMap(intervalValue -> {
					Observable<Object> objectObservable = Observable.create(emitter -> {
						if (intervalValue % 10 == 3) {
							throw new Exception();
						}
						emitter.onNext(intervalValue);
					});

					objectObservable.onErrorResumeNext(objectObservable)
							.subscribe(value -> {
							}, System.out::println);
					return Observable.just(intervalValue);
				})
				.subscribe(test, System.out::println);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}