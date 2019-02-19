import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Observable<Integer> a = Observable.merge(Observable.fromArray(1, 2, 3, 4), Observable.fromArray(10, 11, 12, 13)).map(h -> {
//            if (h == 3) {
//                throw new NullPointerException();
//            } else return h;
//        }).onErrorReturn(th -> 3);
//
//
//        BehaviorRelay<Integer> behaviorRelay = BehaviorRelay.create();
//
//        behaviorRelay.subscribe(System.out::println);
//        a.subscribe(behaviorRelay, throwable -> {
//
//        });

        BehaviorRelay<Long> test = BehaviorRelay.create();
        test.subscribe(System.out::println, System.out::println, () -> System.out.println("cin"));

        Observable.interval(10, 10, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(intervalValue -> {
                    return Observable.defer(() -> {
                        Random random = new Random();
                        int i = random.nextInt(200);
                        if (i > 150) {
                            throw new NullPointerException();
                        }
                        return Observable.just(intervalValue);
                    });
                }).subscribe(test, System.out::println);
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
