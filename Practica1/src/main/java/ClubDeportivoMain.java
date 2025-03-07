import clubdeportivo.ClubDeportivo;
import clubdeportivo.ClubException;
import clubdeportivo.Grupo;

public class ClubDeportivoMain {
	public static void main(String[] args) {
		String [] grupo1 = {"123A","Kizomba","10","10","25.0"};
		
		try {
			ClubDeportivo club = new ClubDeportivo("UMA",1);
			Grupo pilates = new Grupo("456B","Pilates",8,5,50.0);
			club.anyadirActividad(grupo1);
			club.anyadirActividad(pilates);
			System.out.println(club);			
			System.out.println("Ingresos: " + club.ingresos());

			
			
		} catch (ClubException e) {
			System.out.println(e.getMessage());
		}

	// 	try {
	// 	// Crear club normal y club de alto rendimiento
	// 	ClubDeportivo club = new ClubDeportivo("Club Deportivo");
	// 	ClubDeportivoAltoRendimiento clubAlto = new ClubDeportivoAltoRendimiento("Club Alto", 15, 20, 10);

	// 	// Prueba: añadir actividad válida
	// 	String[] datosValidos = {"G1", "Futbol", "10", "5", "50"};
	// 	club.anyadirActividad(datosValidos);
	// 	clubAlto.anyadirActividad(datosValidos);

	// 	System.out.println("Club tras añadir actividad válida:");
	// 	System.out.println(club);

	// 	System.out.println("Club Alto Rendimiento tras añadir actividad válida:");
	// 	System.out.println(clubAlto);

	// 	// Prueba: añadir grupo nulo
	// 	try {
	// 		club.anyadirActividad((Grupo) null);
	// 	} catch (ClubException e) {
	// 		System.out.println("Correcto: no se puede añadir grupo nulo - " + e.getMessage());
	// 	}

	// 	// Prueba: añadir grupo con plazas inválidas
	// 	try {
	// 		String[] datosInvalidos = {"G2", "Natación", "-5", "2", "40"};
	// 		club.anyadirActividad(datosInvalidos);
	// 	} catch (ClubException e) {
	// 		System.out.println("Correcto: datos inválidos - " + e.getMessage());
	// 	}

	// 	// Prueba: matricular personas correctamente
	// 	club.matricular("Futbol", 3);
	// 	System.out.println("Club tras matricular 3 personas en Futbol:");
	// 	System.out.println(club);

	// 	// Prueba: matricular más personas de las que hay libres
	// 	try {
	// 		club.matricular("Futbol", 10);
	// 	} catch (ClubException e) {
	// 		System.out.println("Correcto: no se pueden matricular más personas de las plazas libres - " + e.getMessage());
	// 	}

	// 	// Prueba: añadir más grupos de los permitidos
	// 	for (int i = 0; i < 12; i++) {  // Más de los 10 iniciales
	// 		String[] extraDatos = {"G" + (i+2), "Baloncesto", "5", "2", "30"};
	// 		club.anyadirActividad(extraDatos);
	// 	}
	// 	System.out.println("Club tras intentar añadir 12 actividades:");
	// 	System.out.println(club);

	// } catch (ClubException e) {
	// 	System.out.println("Error inesperado: " + e.getMessage());
	// }
     }
}
