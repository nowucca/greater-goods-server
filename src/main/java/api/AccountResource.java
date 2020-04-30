package api;

import business.account.LoginForm;
import business.account.LoginDetails;
import javax.ws.rs.*;

@ApplicationPath("/")
@Path("/account")
public class AccountResource {

	@POST
	@Path("login")
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public LoginDetails login(LoginForm loginForm) {

		//TODO - validate login form against a customer store

		try {
			LoginDetails loginDetails = new LoginDetails();
			loginDetails.setEmail("jane@doe.com");
			loginDetails.setFirstName("jane");
			loginDetails.setLastName("doe");
			return loginDetails;
		} catch (ApiException e) {
			throw e;
		} catch (Exception e) {
			throw new ApiException("login failed", e);
		}
	}
}

