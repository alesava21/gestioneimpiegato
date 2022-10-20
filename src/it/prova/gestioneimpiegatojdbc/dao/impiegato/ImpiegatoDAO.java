package it.prova.gestioneimpiegatojdbc.dao.impiegato;

import java.sql.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.IBaseDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception;
	public int countByDataFondazioneCompagniaGreaterThan(Date dataFondazoneInput) throws Exception;
	public List<Impiegato> findAllErroriAssunzione() throws Exception;

}
