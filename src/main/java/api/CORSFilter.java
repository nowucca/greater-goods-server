package api;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * This CORS filter allows a server to accept requests from foreign origins.
 * It can be installed in any Jersey-based API server.
 *
 * This filter adds CORS response headers to a response if an Origin is presented
 * and that Origin matches allowed origins.  A request missing an Origin
 * header is assumed a simple request and allowed.
 *
 * A request arriving from a dis-allowed Origin will be rejected with a 403 response.
 *
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class CORSFilter implements ContainerResponseFilter {

	private Pattern[] MATCHING_ORIGIN_PATTERNS = new Pattern[] {
		Pattern.compile("^http(s?)://cs5244.cs.vt.edu:(8080|8443)(.*)$"),  // allow students' hosted sites
		Pattern.compile("^http(s?)://localhost:8081(.*)$"),  // allow local development
		Pattern.compile("^http(s?)://localhost:(8080|8443)(.*)$"),  // allow local development of combined client and server
		//Pattern.compile("^(.*)$") // allow anything
	};


	// Only update the pattern if we have to.
	public CORSFilter() {
	}

	@Override
	public void filter(ContainerRequestContext requestContext,
					   ContainerResponseContext responseContext) throws IOException {
		if (responseContext.getStatus() >= 300) {
			return;
		}

		try {
			String originFromRequest = requestContext.getHeaderString("Origin");
			if (originFromRequest == null) {
				handleMissingOriginHeader(requestContext, responseContext);
			}else if (originFromRequest.length() == 0) {
				handleEmptyOriginHeader(requestContext, responseContext);
			} else {
				System.out.printf("CORSFilter: received request with originFromRequest=%s\n", originFromRequest);
				String matchingOrigin = matchingOrigin(originFromRequest);
				if (matchingOrigin == null || matchingOrigin.length() == 0) {
					System.out.printf("CORSFilter: failed to match originFromRequest=%s\n", originFromRequest);
					handleNonMatchingOrigin(requestContext, responseContext, originFromRequest);
				} else {
					System.out.printf("CORSFilter: matched originFromRequest=%s matchingOrigin=%s\n", originFromRequest, matchingOrigin);
					handleMatchingOrigin(requestContext, responseContext, matchingOrigin);
				}
			}
		} catch(Exception e)  {
			System.out.printf("Unexpected problem evaluating the nature of a possible CORS request: %s\n", e);
			e.printStackTrace(System.out);
		}
	}


	protected void handleMissingOriginHeader(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
	}


	protected void handleMatchingOrigin(ContainerRequestContext requestContext, ContainerResponseContext responseContext, String matchingOrigin) {
		addCORSResponseHeaders(responseContext, matchingOrigin);
	}


	protected void addCORSResponseHeaders(ContainerResponseContext responseContext, String corsAllowOrigin) {
		MultivaluedMap<String, Object> headers =  responseContext.getHeaders();
		headers.putSingle("Access-Control-Allow-Origin", corsAllowOrigin);
		headers.putSingle("Access-Control-Allow-Credentials", "true");
		headers.putSingle("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
		headers.putSingle("Access-Control-Allow-Headers", "Access-Control-Allow-Headers,Access-Control-Allow-Origin,Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,X-ThreatMetrix-SessionId");
		headers.add("Vary", "Origin");
	}

	protected String matchingOrigin(String originFromRequest) {
		for (Pattern pattern: MATCHING_ORIGIN_PATTERNS) {
			System.out.printf("CORSFilter: testing %s against pattern %s\n", originFromRequest, pattern.pattern());
			Matcher matcher = pattern.matcher(originFromRequest);
			if (matcher.matches()) {
				return matcher.group();
			}
		}
		return null;
	}


	protected void handleEmptyOriginHeader(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		rejectCorsRequest(requestContext, responseContext);
	}

	protected void handleNonMatchingOrigin(ContainerRequestContext requestContext, ContainerResponseContext responseContext,
										   String nonMatchingOrigin) {
		rejectCorsRequest(requestContext, responseContext);
	}



	private void rejectCorsRequest(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
		responseContext.setStatusInfo(new Response.StatusType() {
			@Override
			public int getStatusCode() {
				return 403;
			}

			@Override
			public Response.Status.Family getFamily() {
				return Response.Status.Family.CLIENT_ERROR;
			}

			@Override
			public String getReasonPhrase() {
				return "Missing or invalid Origin header";
			}
		});
		responseContext.setEntity("Missing or invalid Origin header");
	}


}
