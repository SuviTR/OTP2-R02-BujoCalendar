/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetropoliaAMKgroup02.Backend.controller;

import MetropoliaAMKgroup02.Backend.Database;
//import MetropoliaAMKgroup02.Backend.model.Merkinta.Prior;
import MetropoliaAMKgroup02.Backend.model.CalendarModel;
import MetropoliaAMKgroup02.Common.model.Merkinta;
import MetropoliaAMKgroup02.Common.model.TestModel;

import java.util.Calendar;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URI;

/**
 *
 * @author vladimir
 */
public class CalendarController extends AbstractController {
	public CalendarController(Database data) {
		super(data);
	}
	
	/**
	 *
	 * @param body
	 * @return
	 */
	@Override
	protected Object sendResponse(URI uri, String body) {
		CalendarModel merkintoja = new CalendarModel(this.data);
		Object object = merkintoja.getAll();

		return object;
	}

	@Override
	protected Object handleGet(int id, URI uri) {
		CalendarModel calendar = new CalendarModel(data);
		return calendar.getMerkinta(id);
		
	}

	@Override
	protected Object handlePost(String body, URI uri) {
		CalendarModel calendar = new CalendarModel(data);
		Merkinta merkinta = new Merkinta();	
		try {
			merkinta = this.mapper.readValue(body, Merkinta.class);
		} catch (IOException ex) {
			Logger.getLogger(HenkiloController.class.getName()).log(Level.SEVERE, null, ex);
		}
		calendar.addMerkinta(merkinta);
		return merkinta;
	}
	
	/**
     * Handles put operation. Gets a changed note, note's id and uri.
     * @param id includes the id of the changed note.
     * @param body includes the content of the note.
     * @param uri includes the path.
     * @return merkinta is the new note.
     */
	@Override
	protected Object handlePut(int id, String body, URI uri) {
		
		CalendarModel calendar = new CalendarModel(data);
		Merkinta merkinta = new Merkinta();
		

		try {
			merkinta = this.mapper.readValue(body, Merkinta.class);
		} catch (IOException ex) {
			Logger.getLogger(HenkiloController.class.getName()).log(Level.SEVERE, null, ex);
		}
		merkinta.setId(id);
		calendar.editMerkinta(merkinta);
		return merkinta;
	}
	
	/**
     * Handles put operation. Gets a changed note, note's id and uri.
     * @param body includes the content of the note.
     * @param uri includes the path.
     * @return merkinta is the new note.
     */
	@Override
	protected Object handlePut(String string, URI uri) {
            //TODO:implement HTTPstatus codes
            return new Object();
	}

	/**
     * Handles delete operation. Gets an id of the note that a user wants to delete and uri.
     * @param id includes the id of the note that a user wants to delete.
     * @param uri includes the path.
     * @return calendar is the updated calendar.
     */
	@Override
	protected Object handleDelete(int id, URI uri) {
		CalendarModel calendar = new CalendarModel(data);
		calendar.deleteMerkinta(id);	
		return calendar;
	}

	@Override
	protected Object handleGet(URI uri) {
		CalendarModel calendar = new CalendarModel(data);
		return calendar.getAll();
	}

	@Override
	protected Object handleDelete(URI uri) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
