import java.util.concurrent.TimeUnit;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class TestManager {

	private BehaviorRelay<RxObject<Api.ApiObject>> apiObjectBehaviorRelay = BehaviorRelay.create();
	private PublishRelay<Long> checkNowTrigger = PublishRelay.create();
	private Api api = new Api();

	public TestManager() {
		Observable.interval(15, 5, TimeUnit.SECONDS, Schedulers.io())
				.mergeWith(checkNowTrigger)
				.doOnNext(aLong -> apiObjectBehaviorRelay.accept(RxObject.loading(null)))
				.switchMap(intervalValue -> request().toObservable())
				.subscribe(apiObjectBehaviorRelay, System.out::println);
	}

	public Flowable<RxObject<Api.ApiObject>> getApiObject(boolean checkNow) {
		return apiObjectBehaviorRelay.doOnSubscribe(dis -> {
			if (checkNow) {
				checkNowTrigger.accept(0L);
			}
		}).toFlowable(BackpressureStrategy.DROP);
	}

	private Single<RxObject<Api.ApiObject>> request() {
		return api.getObject()
				.map(RxObject::success)
				.onErrorReturn(throwable -> RxObject.error(throwable, null));
	}
}
