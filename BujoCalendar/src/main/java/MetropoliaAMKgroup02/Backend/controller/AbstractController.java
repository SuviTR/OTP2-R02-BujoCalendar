/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetropoliaAMKgroup02.Backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import MetropoliaAMKgroup02.Backend.Database;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.URI;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heikki
 */
public abstract class AbstractController implements HttpHandler {
	protected Database data;
	public AbstractController(Database data) {
		this.data = data;
	}
	public void handle(HttpExchange HttpObject) throws IOException {

		System.out.println(new Date().toString() + " Got request from: " + HttpObject.getRequestURI().toString());
		InputStream is = HttpObject.getRequestBody();
		byte requestBody[] = new byte[1024];
		is.read(requestBody);
		
		String response = this.json(this.sendResponse(HttpObject.getRequestURI(),
			new String(requestBody)
		));
		
		byte[] bytes = response.getBytes();

		HttpObject.getResponseHeaders().add("Content-type", "text/json");
		HttpObject.sendResponseHeaders(200, bytes.length);
		OutputStream os = HttpObject.getResponseBody();
		try {
			os.write(bytes);
			os.close();
		} catch(Exception e) {
			System.out.println("Outputstream kirjoitus epäonnistui:");
			e.printStackTrace();
		}
	}
	
	protected String json(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException ex) {
			Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
		}

		return json;
	}

	protected abstract Object sendResponse(URI uri, String body);
	
}
