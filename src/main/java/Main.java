public class Main {
	public static void main(String[] args) throws InterruptedException {
		TestManager testManager = new TestManager();
		testManager.getApiObject(true)
				.takeUntil(apiObjectRxObject -> apiObjectRxObject.getStatus() == RxObject.Status.SUCCESS)//like Single
				.subscribe(apiObjectRxObject -> {
					switch (apiObjectRxObject.getStatus()) {
						case ERROR:
							if (apiObjectRxObject.getData() != null) {
								System.out.println(apiObjectRxObject.getData().i);
							}
							if (apiObjectRxObject.getError() != null) {
								apiObjectRxObject.getError().printStackTrace();
							}
							break;
						case LOADING:
							if (apiObjectRxObject.getData() != null) {
								System.out.println(apiObjectRxObject.getData().i);
							}
							System.out.println("loading...");
							break;
						case SUCCESS:
							if (apiObjectRxObject.getData() != null) {
								System.out.println(apiObjectRxObject.getData().i);
							}
					}
				}, throwable -> {
					System.out.println(throwable);
				}, () -> {
					System.out.println("Complete!");
				});
		Thread.sleep(50000);
	}
}
