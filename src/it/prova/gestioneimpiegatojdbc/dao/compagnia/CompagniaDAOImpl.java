package it.prova.gestioneimpiegatojdbc.dao.compagnia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.dao.AbstractMySQLDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<>();
		Compagnia comapagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				comapagniaTemp = new Compagnia();
				comapagniaTemp.setId(rs.getLong("ID"));
				comapagniaTemp.setRagioneSociale(rs.getString("ragioneSociale"));
				comapagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoAnnuo"));
				comapagniaTemp.setDataFondazione(rs.getDate("dataFondazione"));
				result.add(comapagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive()) {
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		}
		if (idInput == null || idInput < 1) {
			throw new Exception("Valore di input non ammesso.");
		}

		Compagnia result = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id =?;")) {
			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setId(rs.getLong("ID"));
					result.setRagioneSociale(rs.getString("ragioneSociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoAnnuo"));
					result.setDataFondazione(rs.getDate("dataFondazione"));
				} else {
					result = null;
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			return result;
		}

	}

	@Override
	public int update(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"update compagnia set ragionesociale =?, fatturatoannuo=?, datafondazione=? where id =?;")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (ragionesociale, fatturatoannuo, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(input.getDataFondazione().getTime()));

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from compagnia where id=?;")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String ragioneSocialeContieneInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (ragioneSocialeContieneInput == null)
			throw new Exception("Valore di input non ammesso.");

		List<Compagnia> listaCompagnie = new ArrayList<Compagnia>();
		Compagnia temp = null;

		try (PreparedStatement ps = connection
				.prepareStatement("select * from compagnia where ragionesociale like?;")) {
			ps.setString(1, '%' + ragioneSocialeContieneInput + '%');

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					temp = new Compagnia();
					temp.setRagioneSociale(rs.getString("ragioneSociale"));
					listaCompagnie.add(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listaCompagnie;
	}

	@Override
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(java.util.Date dataAssunzioneInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (dataAssunzioneInput == null)
			throw new Exception("Valore di input non ammesso.");
		
		List<Compagnia> listaCompegnia = new ArrayList<Compagnia>();

		Compagnia temp = null;
		
		try (PreparedStatement ps= connection.prepareStatement("select distinct * from compagnia c inner join impiegato i on c.id = i.compagnia_id where i.dataassunzione > ?;")){
			ps.setDate(1, new java.sql.Date(dataAssunzioneInput.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					temp = new Compagnia();
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(rs.getDate("datafondazione"));
					temp.setId(rs.getLong("ID"));
					listaCompegnia.add(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listaCompegnia;
	}

}
