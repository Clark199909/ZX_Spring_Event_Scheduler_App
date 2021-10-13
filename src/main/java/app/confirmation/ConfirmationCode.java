package app.confirmation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConfirmationCode {
	private String code;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String input;
	
	public ConfirmationCode() {}

	public ConfirmationCode(String code, String input) {
		this.code = code;
		this.input = input;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	
}
