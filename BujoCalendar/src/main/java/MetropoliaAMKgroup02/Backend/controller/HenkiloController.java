/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MetropoliaAMKgroup02.Backend.controller;

import MetropoliaAMKgroup02.Backend.Database;
import MetropoliaAMKgroup02.Backend.model.HenkiloModel;

import java.net.URI;

/**
 *
 * @author heikki
 */
public class HenkiloController extends AbstractController {

	HenkiloModel henkilot = new HenkiloModel(this.data);

	public HenkiloController(Database data) {
		super(data);
	}

	@Override
	protected Object sendResponse(URI uri, String body) {
		henkilot.addHenkilo();
		Object object = henkilot.getHenkilo((long) 1);
		
		return object;
	}

	@Override
	protected Object handleGet(int id, URI uri) {
		Object object = henkilot.getAll();
		
		return object;
	}

	@Override
	protected Object handlePost(String body, URI uri) {
		henkilot.addHenkilo();
		Object object = henkilot.getAll();
		return object;
	}

	@Override
	protected Object handlePut() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected Object handleDelete() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}