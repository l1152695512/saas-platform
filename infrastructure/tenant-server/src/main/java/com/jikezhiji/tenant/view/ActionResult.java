package com.jikezhiji.tenant.view;

public class ActionResult<T> {
	private String message;
	private boolean success;
	private T value;

	public ActionResult() {
	}

	private ActionResult(String message, boolean success, T value) {
		this.message = message;
		this.success = success;
		this.value = value;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public static <T> ActionResult<T> success(String message) {
		return success(message, null);
	}
	public static <T> ActionResult<T> success(String message,T value) {
		return new ActionResult<T>(message, true,value);
	}

	public static <T> ActionResult<T> fail(String message) {
		return fail(message, null);
	}
	public static <T> ActionResult<T> fail(String message,T value) {
		return new ActionResult<T>(message, false,value);
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public T getValue(){
		return value;
	}

	@Override
	public String toString() {
		return "{message:" + message + ", success:" + success+ ", value:" +value+ "}";
	}
	
	
}