package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import it.prova.gestioneimpiegatojdbc.connection.MyConnection;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAO;
import it.prova.gestioneimpiegatojdbc.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAO;
import it.prova.gestioneimpiegatojdbc.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class TestGestioneImpiegato {
	public static void main(String[] args) throws SQLException, Exception {

		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(
				it.prova.gestioneimpiegatojdbc.dao.Constants.DRIVER_NAME,
				it.prova.gestioneimpiegatojdbc.dao.Constants.CONNECTION_URL)) {
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);

			testInsertCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");

			testInsertImpiegato(impiegatoDAOInstance);
			System.out.println("In tabella compagnia ci sono " + impiegatoDAOInstance.list().size() + " elementi.");

			testGetCompagnia(compagniaDAOInstance);

			testFindAllByRagioneSocialeContiene(compagniaDAOInstance);

			testFindAllByDataAssunzioneMaggioreDi(compagniaDAOInstance);

			testGetImpiegato(impiegatoDAOInstance);
			System.out.println("In tabella compagnia ci sono " + impiegatoDAOInstance.list().size() + " elementi.");

			//testUpdateImpiegato(impiegatoDAOInstance);
			System.out.println("In tabella compagnia ci sono " + impiegatoDAOInstance.list().size() + " elementi.");

			testDeleteCompagnia(compagniaDAOInstance);
			System.out.println("In tabella compagnia ci sono " + compagniaDAOInstance.list().size() + " elementi.");
			
			testDeleteImpiegato(impiegatoDAOInstance);
			System.out.println("In tabella compagnia ci sono " + impiegatoDAOInstance.list().size() + " elementi.");
			
			

		}

	}

	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testInsertCompagnia inizio.............");

		int quantiElementiInseriti = compagniaDAOInstance
				.insert(new Compagnia("Test1", 70000, new java.sql.Date(2020, 01, 30)));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED");

		System.out.println(".......testInsertCompagnia fine: PASSED.............");
	}

	private static void testGetCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testGet inizio.............");
		List<Compagnia> elencoPresenti = compagniaDAOInstance.list();

		Compagnia elementoRicercoColDAO = compagniaDAOInstance.get(44l);

		System.out.println(elementoRicercoColDAO);

		System.out.println(".......testGet fine: PASSED.............");
	}

	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnia inizio.............");

		List<Compagnia> listaDelleCompagnie = compagniaDAOInstance.list();

		if (listaDelleCompagnie.isEmpty()) {
			throw new RuntimeException("testDeleteCompagnia : FAILED, i nomi non corrispondono");

		}

		Compagnia compagniaCheVoglioEliminare = listaDelleCompagnie.get(0);

		compagniaDAOInstance.delete(compagniaCheVoglioEliminare);
		if (listaDelleCompagnie.isEmpty()) {
			throw new RuntimeException("testDeleteCompagnia : FAILED, i nomi non corrispondono");
		}
		System.out.println(".......testDeleteCompagnia fine: PASSED.............");

	}

	private static void testFindAllByRagioneSocialeContiene(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindAllByRagioneSocialeContiene inizio.............");
		List<Compagnia> listaCompagnie = compagniaDAOInstance.list();
		if (listaCompagnie.isEmpty()) {
			throw new RuntimeException("testFindAllByRagioneSocialeContiene : FAILED, i nomi non corrispondono");
		}
		String ragioneSocialeDaCercare = "Test1";
		listaCompagnie = compagniaDAOInstance.findAllByRagioneSocialeContiene(ragioneSocialeDaCercare);

		if (listaCompagnie.isEmpty()) {
			throw new RuntimeException("testFindAllByRagioneSocialeContiene : FAILED, i nomi non corrispondono");
		}
		System.out.println(".......testFindAllByRagioneSocialeContiene fine: PASSED.............");

	}

	private static void testFindAllByDataAssunzioneMaggioreDi(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi inizio.............");

		List<Compagnia> listaCompagnia = compagniaDAOInstance.list();
		if (listaCompagnia.isEmpty()) {
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, i nomi non corrispondono");
		}

		Date dataPerRicerca = new SimpleDateFormat("dd-MM-yyyy").parse("01-02-2019");
		listaCompagnia = compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(dataPerRicerca);

		if (listaCompagnia.isEmpty()) {
			throw new RuntimeException("testFindAllByDataAssunzioneMaggioreDi : FAILED, i nomi non corrispondono");
		}
		System.out.println(".......testFindAllByDataAssunzioneMaggioreDi fine: PASSED.............");

	}
	// TEST IMPIEGATO

	private static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testInsertImpiegato inizio.............");

		Compagnia compagniaPerId = new Compagnia();
		compagniaPerId.setId(61l);

		int quantiElementiInseriti = impiegatoDAOInstance
				.insert(new Impiegato("Alessandro", "Sava", "hsdjhdfdsf", new java.sql.Date(2020, 01, 30), new java.sql.Date(2020, 01, 30),compagniaPerId));
		if (quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED");

		System.out.println(".......testInsertImpiegato fine: PASSED.............");
	}

	private static void testGetImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testGetImpiegato inizio.............");
		List<Impiegato> elencPresenti = impiegatoDAOInstance.list();

		if (elencPresenti.isEmpty()) {
			throw new RuntimeException("testGetImpiegato : FAILED, i nomi non corrispondono");
		}

		Impiegato elementoCheRicercoColDAO = impiegatoDAOInstance.get(1L);

		System.out.println(elementoCheRicercoColDAO);

		System.out.println(".......testGetImpiegato fine: PASSED.............");
	}


	private static void testDeleteImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println(".......testDeleteCompagnia inizio.............");

		List<Impiegato> listaImpiegati = impiegatoDAOInstance.list();
		 
		Impiegato impiegatoCheVoglioEliminare= listaImpiegati.get(0);
		
		impiegatoDAOInstance.delete(impiegatoCheVoglioEliminare);
		

		if (listaImpiegati.isEmpty()) {
			throw new RuntimeException("testDeleteCompagnia : FAILED, i nomi non corrispondono");

		}

	}
	
}
