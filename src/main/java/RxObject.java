

public class RxObject<T> {
	enum Status {
		SUCCESS,
		ERROR,
		LOADING
	}

	private Status status;
	private T data;
	private Throwable error;

	public RxObject(Status status, T data, Throwable error) {
		this.status = status;
		this.data = data;
		this.error = error;
	}


	public static <T> RxObject<T> success(T data) {
		return new RxObject<>(Status.SUCCESS, data, null);
	}

	public static <T> RxObject<T> error(Throwable error, T data) {
		return new RxObject<>(Status.ERROR, data, error);
	}

	public static <T> RxObject<T> loading(T data) {
		return new RxObject<>(Status.LOADING, data, null);
	}

	public Status getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}

	public Throwable getError() {
		return error;
	}
}
