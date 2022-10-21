package it.prova.gestioneimpiegatojdbc.dao.impiegato;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.AbstractMySQLDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from impiegato")) {

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione"));
				impiegatoTemp.setId(rs.getLong("id"));
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Impiegato();
					result.setNome(rs.getString("nome"));
					result.setCognome(rs.getString("cognome"));
					result.setCodiceFiscale(rs.getString("codicefiscale"));
					result.setDataNascita(rs.getDate("datadinascita"));
					result.setDataAssunzione(rs.getDate("dataassunzione"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Impiegato impiegatoInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null || impiegatoInput.getId() == null || impiegatoInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE impiegato SET nome=?, cognome=?, codicefiscale=?, datanascita=?, dataassunzione=? where id=?;")) {
			ps.setString(1, impiegatoInput.getNome());
			ps.setString(2, impiegatoInput.getCognome());
			ps.setString(3, impiegatoInput.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(impiegatoInput.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(impiegatoInput.getDataAssunzione().getTime()));
			ps.setLong(6, impiegatoInput.getId());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato impiegatoInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (nome, cognome, codicefiscale, datadinascita, dataassunzione, compagnia_id) VALUES (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, impiegatoInput.getNome());
			ps.setString(2, impiegatoInput.getCognome());
			ps.setString(3, impiegatoInput.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(impiegatoInput.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(impiegatoInput.getDataAssunzione().getTime()));
			ps.setLong(6, impiegatoInput.getCompagnia().getId());

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato impiegatoInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null || impiegatoInput.getId() == null || impiegatoInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM impiegato WHERE ID=?")) {
			ps.setLong(1, impiegatoInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on i.compagnia_id = c.id where compagnia_id = ?")) {
			ps.setLong(1, compagniaInput.getId());

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("nome"));
					impiegatoTemp.setCognome(rs.getString("cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTemp.setDataNascita(rs.getDate("datanascita"));
					impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione"));
					impiegatoTemp.setId(rs.getLong("i.id"));

					compagniaTemp = new Compagnia();
					compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
					compagniaTemp.setId(rs.getLong("c.id"));

					impiegatoTemp.setCompagnia(compagniaTemp);
					result.add(impiegatoTemp);
				}
			}
		}
		return result;
	}

	@Override
	public int countByDataFondazioneCompagniaGreaterThan(Date dataFondazoneInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dataFondazoneInput == null)
			throw new Exception("Valore di input non ammesso.");

		int contatore = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from impiegato i inner join compagnia c on c.id = i.compagnia_id where c.datafondazione > ?;")) {

			ps.setDate(1, new java.sql.Date(dataFondazoneInput.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					contatore = rs.getInt("count(*)");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return contatore;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzione() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;
		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on c.id = i.compagnia_id where i.dataassunzione < c.datafondazione")) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("nome"));
					impiegatoTemp.setCognome(rs.getString("cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTemp.setDataNascita(rs.getDate("datanascita"));
					impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione"));

					result.add(impiegatoTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
