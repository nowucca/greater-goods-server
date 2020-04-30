package business.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginForm {

	private String email;
	private String passwordFingerprint;

	@JsonCreator
	public LoginForm(@JsonProperty("email") String email,
					 @JsonProperty("passwordHash") String passwordHash) {
		this.email = email;
		this.passwordFingerprint = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordFingerprint() {
		return passwordFingerprint;
	}

}
