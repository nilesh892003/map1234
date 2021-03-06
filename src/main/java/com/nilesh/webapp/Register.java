package com.nilesh.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

public class Register extends HttpServlet {
	/*
	 * Author: Nilesh Singh, George Mason University, MS Computer Science Date:
	 * June, 16th, 2014.
	 * 
	 * Topic:This is a webapp that allows any user to sign up for
	 * http://myopenissues.com/magento/index.php. after the process user will
	 * get success/error notifications.
	 * 
	 * Web service:
	 * http://myopenissues.com/magento/index.php/customer/account/create/
	 * 
	 * Tool: HTTPComponents: HttpClient is a HTTP/1.1 compliant HTTP agent
	 * implementation based on HttpCore. It also provides reusable components
	 * for client-side authentication, HTTP state management, and HTTP
	 * connection management.
	 * 
	 * Twitter Bootstrap: Sleek, intuitive, and powerful front-end framework for
	 * faster and easier web development.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String accountURL = "http://myopenissues.com/magento/index.php/customer/account/create/";
		// String successURL =
		// "http://myopenissues.com/magento/index.php/customer/account/index/";
		String successURL = "http://myopenissues.com/magento/index.php/";

		String address = null;
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmation = request.getParameter("repassword");
		String isSubscribed = request.getParameter("is_subscribed");
		String success_url = request.getParameter("success_url");
		String error_url = request.getParameter("error_url");

		boolean isValid = true;

		// Validation

		if (!isValidName(fname)) {
			request.setAttribute("fname",
					"First Name should be at least 3 character long");
			isValid = false;
		}
		if (!isValidName(lname)) {
			request.setAttribute("lname",
					"Last Name should be at least 3 character long");
			isValid = false;
		}
		if (!isValidEmail(email)) {
			request.setAttribute("email", "Not a valid email");
			isValid = false;
		}
		if (!isValidPassword(password, confirmation)) {
			request.setAttribute("password", "Not a valid password");
			isValid = false;
		}

		if (!isValid) {
			address = "./register.jsp";
		}

		else {

			BasicCookieStore cookieStore = new BasicCookieStore();
			BasicClientCookie cookie = new BasicClientCookie("SESSIONID",
					"map1234");
			cookie.setDomain(".myopenissues.com");
			cookie.setPath("/magento");
			cookieStore.addCookie(cookie);

			HttpClientBuilder builder = HttpClientBuilder.create();
			builder.setDefaultCookieStore(cookieStore);
			builder.setRedirectStrategy(new LaxRedirectStrategy());

			HttpClient client = builder.build();
			HttpPost post = new HttpPost(
					"http://myopenissues.com/magento/index.php/customer/account/createpost/");

			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				nameValuePairs.add(new BasicNameValuePair("success_url",
						success_url));
				nameValuePairs.add(new BasicNameValuePair("error_url",
						error_url));

				nameValuePairs.add(new BasicNameValuePair("firstname", fname));
				nameValuePairs.add(new BasicNameValuePair("lastname", lname));
				nameValuePairs.add(new BasicNameValuePair("email", email));
				nameValuePairs.add(new BasicNameValuePair("is_subscribed",
						isSubscribed));
				nameValuePairs
						.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("confirmation",
						confirmation));
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpClientContext context = HttpClientContext.create();
				HttpResponse res = client.execute(post, context);
				List<URI> redirectLocations = context.getRedirectLocations();

				if (res.getStatusLine().getStatusCode() == 200) {
					if (redirectLocations.get(0).toString().equals(successURL)) {
						out.println(redirectLocations.get(0).toString()
								.equals(successURL));
						address = "./success.jsp";
					}

					else if (redirectLocations.get(0).toString()
							.equals(accountURL)) {
						address = "./register.jsp";
						String message = "User Already Registered";
						request.setAttribute("message", message);
					} else {
						address = "./error.jsp";
					}
				} else {
					address = "./error.jsp";
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	// Method for first and last name validation
	private boolean isValidName(String name) {
		if (name == null || name.length() < 3
				|| !name.matches("[A-Z][a-zA-Z]*"))
			return false;
		else
			return true;
	}

	// Method for Email validation
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		if(email != null){
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
			}
		else
		{
			return false;
		}
	}

	// Method for Password
	private boolean isValidPassword(String password, String confirmPass) {
		if (password != null && confirmPass != null
				&& password.equals(confirmPass) && password.length() > 5)
			return true;
		else
			return false;
	}

}