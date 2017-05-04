package com.decipher.util;

public class JsonResponse {
	public static final String RESULT_SUCCESS = "success";
	public static final String RESULT_FAILED = "failed";
	public static final String RESULT_ALREADY_EXISTS = "Already exists";
	public static final String RESULT_NOT_EXISTS = "Not exists";
	public static final String RESULT_INVALID_USER_NOT_EXISTS = "invalid user";
	public static final String RESULT_HAS_CHILD_RECORD = "This has child record";
	private String status;
	private Object result;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "{\"status\":\"" + status + "\",\"result\":" + result.toString() + "}";
	}

}
