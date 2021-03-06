package br.com.compasso.uol.products.dto;

public class ErroValidationDTO {

	private Integer status_code;
	private String message;

	public ErroValidationDTO(Integer status_code, String message) {
		this.status_code = status_code;
		this.message = message;
	}

	public Integer getStatus_code() {
		return status_code;
	}

	public String getMessage() {
		return message;
	}

}
