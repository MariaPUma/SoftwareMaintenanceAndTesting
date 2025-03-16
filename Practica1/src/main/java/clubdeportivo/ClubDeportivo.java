package clubdeportivo;

import java.util.StringJoiner;

public class ClubDeportivo {
	private String nombre;
	private int ngrupos;
	private Grupo[] grupos;
	private static final int TAM = 10;

	public ClubDeportivo(String nombre) throws ClubException {
		this(nombre, TAM);
	}

	public ClubDeportivo(String nombre, int n) throws ClubException {
		if(nombre == null || nombre.isEmpty()){
			throw new ClubException("ERROR: el club no puede crearse con un nombre vacío o nulo");
		}
		if (n <= 0) {
			throw new ClubException("ERROR: el club no puede crearse con un número de grupos 0 o negativo");
		}
		this.nombre = nombre;
		grupos = new Grupo[n];
		ngrupos = 0; //inicializar el número de grupos a 0
	}

	private int buscar(Grupo g) {
		int i = 0;
		while (i < ngrupos && !g.equals(grupos[i])) {
			i++;
		}
		if (i == ngrupos) {
			i = -1;
		}
		return i;
	}

	public void anyadirActividad(String[] datos) throws ClubException {
		//Control datos no nulo
		if (datos == null || datos.length == 0){
			throw new ClubException("ERROR: se ha pasado un null o datos vacíos a anyadirActividad");
		}
		try {
			int plazas = Integer.parseInt(datos[2]);
			int matriculados = Integer.parseInt(datos[3]);
			double tarifa = Double.parseDouble(datos[4]);
			Grupo g = new Grupo(datos[0], datos[1], plazas, matriculados, tarifa);
			anyadirActividad(g);
		} catch (NumberFormatException e) {
			throw new ClubException("ERROR: formato de número incorrecto");
		}
	}

	public void anyadirActividad(Grupo g) throws ClubException {
		if (g==null){ // ADDME: anaydido para comprobar los grupos nulos
			throw new ClubException("ERROR: el grupo es nulo");
		}
		int pos = buscar(g);
		if (pos == -1) { // El grupo es nuevo //MODIFICADO
			if (ngrupos<grupos.length){
				grupos[ngrupos] = g;
				ngrupos++; 
			}
			else{ //No se pueden añadir más grupos al club
				throw new ClubException("ERROR: no se pueden añadir más grupos al club");
			}
		} else { // El grupo ya existe --> modificamos las plazas
			grupos[pos].actualizarPlazas(g.getPlazas());
		}
	}

	public int plazasLibres(String actividad) throws ClubException {
		int p = 0;
		int i = 0;
		if (actividad == null) {
			throw new ClubException("ERROR: plazasLibres recibe un null como actividad");
		}
		while (i < ngrupos) {
			if (grupos[i].getActividad().equals(actividad)) {
				p += grupos[i].plazasLibres();
			}
			i++;
		}
		return p;
	}

	public void matricular(String actividad, int npersonas) throws ClubException {
		if (actividad == null) {
			throw new ClubException("ERROR: matricular recibe un null como actividad");
		}
		if (npersonas < 0) {
			throw new ClubException("ERROR: número de personas a matricular incorrecto");
		}
		int plazas = plazasLibres(actividad);
		int i = 0;
		while (i < ngrupos && npersonas > 0) {
			if (actividad.equals(grupos[i].getActividad())) {				
				if (plazas < npersonas) { 
					throw new ClubException("ERROR: no hay suficientes plazas libres para esa actividad en el club.");
				}
				int plazasGrupo = grupos[i].plazasLibres();
				if (npersonas >= plazasGrupo) {
					grupos[i].matricular(plazasGrupo);
					npersonas -= plazasGrupo;
				} else {
					grupos[i].matricular(npersonas);
				}
			}
			i++;
		}
	}

	public double ingresos() {
		double cantidad = 0.0;
		int i = 0;
		while (i < ngrupos) {
			cantidad += grupos[i].getTarifa() * grupos[i].getMatriculados();
			i++;
		}
		return cantidad;
	}

	
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "[ ", " ]");
		int i = 0;
		while (i < ngrupos) {
			sj.add(grupos[i].toString());
			i++;
		}
		return nombre + " --> " + sj.toString();
	}
}
