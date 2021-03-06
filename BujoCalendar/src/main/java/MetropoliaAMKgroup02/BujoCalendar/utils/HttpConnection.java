/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetropoliaAMKgroup02.BujoCalendar.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import MetropoliaAMKgroup02.Common.JSONHandler;
import java.net.ConnectException;

/**
 *
 * @author heikki
 */
public class HttpConnection extends JSONHandler {
	
	private String baseUrl;
	private String endpoint;
	private HttpURLConnection con;

	public HttpConnection() {
		super();
		this.baseUrl = "http://localhost:8000";
	}

	public Object get(String endpoint, Class objectType) {
		this.connect(endpoint, "GET");

		String response = this.readResponse();
		Object responseObj = this.JSONToObj(response, objectType);
		this.cleanup();

		return responseObj;
	}

	public Object put(String endpoint, Object body, Class objectType) {
            this.connect(endpoint, "PUT");
            return this.sendData(endpoint, body, objectType);

	}

	public Object delete(String endpoint) {
		
            this.connect(endpoint, "DELETE");
            return this.getResponse(Object.class);
	}

	public Object post(String endpoint, Object body, Class objectType) {
		this.connect(endpoint, "POST");
        return this.sendData(endpoint, body, objectType);
	}

    private Object sendData(String endpoint, Object body, Class objectType) {

		this.con.setRequestProperty("Content-Type", "application/json; utf-8");
		this.con.setDoOutput(true);

		String bodystr = this.ObjToJSON(body);
		this.sendRequestBody(bodystr);

        return this.getResponse(objectType);
    }

    private Object getResponse(Class objectType) {
            
		String response = this.readResponse();

		Object responseObj = this.JSONToObj(response, objectType);
		this.cleanup();

		return responseObj;
    }

	private HttpURLConnection connect(String endpoint, String method) {
		URL url;
		try {
			url = new URL(this.baseUrl + endpoint);
			this.con = (HttpURLConnection) url.openConnection();
			this.con.setRequestMethod(method);
			this.con.setRequestProperty("Accept", "application/json");

		} catch (MalformedURLException ex) {
			System.out.println("Malformed url");
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} catch (ConnectException e) {
			System.out.println("Connection exception:Cant connect to " + this.baseUrl + endpoint);
		} catch (IOException ex) {
			System.out.println("IOException: Connection to " + this.baseUrl + endpoint + " not successfull");
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	private String readResponse() {
		try(BufferedReader br = new BufferedReader(
			new InputStreamReader(this.con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			return response.toString();

		} catch (IOException ex) {
			System.out.println("Reading response didn't succeed...");
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;
	}

	private void sendRequestBody(String body) {

		try(OutputStream os = this.con.getOutputStream()) {
			byte[] input = body.getBytes("utf-8");
			os.write(input, 0, input.length);           
		} catch (IOException ex) {
			System.out.println("Writin JSON request body didn't work... Body: " + body);
			Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void cleanup() {
		this.con = null;
	}
}
