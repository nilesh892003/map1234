package com.nilesh.webapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class RegisterTest{
	
	private Register registerService;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter pageSource;
	private RequestDispatcher dispatcher;
	private HttpClient httpClientMock;
	private HttpPost httpPostMock;
	private HttpResponse httpResponseMock;
	private HttpClientContext contextMock;
	private StatusLine statusLineMock;
	private HttpClientBuilder builderMock;

	@Before
	public void setUp() throws ServletException, IOException {
		registerService = new Register();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		pageSource = new StringWriter();
		dispatcher = mock(RequestDispatcher.class); 
		
		httpResponseMock = mock(HttpResponse.class);
		contextMock = mock(HttpClientContext.class);
		httpPostMock = mock(HttpPost.class);
		statusLineMock = mock(StatusLine.class);
		builderMock = mock(HttpClientBuilder.class, RETURNS_DEEP_STUBS);
		when(response.getWriter()).thenReturn(new PrintWriter(pageSource));
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		httpClientMock = mock(HttpClient.class);
	}
	
	@Test
	public void shouldValidateFirstName() throws  ServletException, IOException {
		when(request.getParameter("fname")).thenReturn("b");
		registerService.doPost(request, response);
		verify(request).setAttribute("fname", "First Name should be at least 3 character long");
		verify(request).getRequestDispatcher("./register.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void shouldValidateLastName() throws ServletException, IOException {
		when(request.getParameter("lname")).thenReturn("N");
		registerService.doPost(request, response);
		verify(request).setAttribute("lname", "Last Name should be at least 3 character long");
		verify(request).getRequestDispatcher("./register.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void shouldValidateEmail() throws ServletException, IOException{
		when(request.getParameter("email")).thenReturn("example");
		registerService.doPost(request, response);
		verify(request).setAttribute("email", "Not a valid email");
		verify(request).getRequestDispatcher("./register.jsp");
		verify(dispatcher).forward(request, response);
	}
	
	@Test
	public void shouldValidatePassword() throws ServletException, IOException{
		when(request.getParameter("password")).thenReturn("test");
		when(request.getParameter("repassword")).thenReturn("test123");
		registerService.doPost(request, response);
		verify(request).setAttribute("password", "Not a valid password");
		verify(request).getRequestDispatcher("./register.jsp");
		verify(dispatcher).forward(request, response);
	}

	/*
	@Test
	public void shouldValidateFormData() throws ServletException, IOException{
		when(request.getParameter("fname")).thenReturn("John");
		when(request.getParameter("lname")).thenReturn("Doe");
		when(request.getParameter("email")).thenReturn("example@domain.com");
		when(request.getParameter("password")).thenReturn("test123");
		when(request.getParameter("repassword")).thenReturn("test123");
		when(builderMock.build()).thenReturn( httpClientMock);
		when(httpClientMock.execute(Mockito.isA(HttpPost.class), Mockito.isA(HttpClientContext.class))).thenReturn(httpResponseMock);
		when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
		when(statusLineMock.getStatusCode()).thenReturn(HttpStatus.FOUND_302);
		registerService.doPost(request, response);
		verify(request).getRequestDispatcher("./error.jsp");
		verify(dispatcher).forward(request, response);
	}
	*/
}
