

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class Api {
	public Single<ApiObject> getObject() {
		return Single.just(new ApiObject()).flatMap(apiObject -> {
			Random random = new Random();
			int i = random.nextInt(200);
			if (i > 150) {
				throw new NullPointerException();
			}
			return Single.just(apiObject);
		}).delay(2, TimeUnit.SECONDS);
	}


	class ApiObject {
		int i = 0;
	}
}
