package it.prova.gestioneimpiegatojdbc.dao.compagnia;

import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.IBaseDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;

import java.util.Date;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {

	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataAssunzioneInput) throws Exception;

	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeContieneInput) throws Exception;

}