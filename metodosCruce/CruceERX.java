package metodosCruce;

import java.util.ArrayList;

import base.Cromosoma;
import base.Gen;

// Es bastante lento
public class CruceERX implements AlgoritmoCruce {
	boolean encontrada;
	
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
		ArrayList<Integer>[] conectividades = calculaConectividades(padre1, padre2); // Tabla de conectividades. Cada elemento del array representa a una ciudad y es un ArrayList con la lista de ciudades adyacentes 

		hijo1.genes[0].setCiudad(padre1.genes[0].getCiudad());
		hijo2.genes[0].setCiudad(padre2.genes[0].getCiudad());
		
		encontrada = false;
		vueltaAtras(1, hijo1, conectividades);
		encontrada = false;
		vueltaAtras(1, hijo2, conectividades);		
	}

	// usando el metodo vuelta atras prueba las distintas combinaciones de rellenar al hijo hasta encontrar una que no presente conflictos
	private void vueltaAtras(int k, Cromosoma hijo, ArrayList<Integer>[] conectividades) {
		ArrayList<Integer> conexiones = conectividades[hijo.genes[k-1].getCiudad()];
		
		for (int i = 0; i < conexiones.size() && !encontrada; i++) {
			hijo.genes[k].setCiudad(conexiones.get(i));
			if (esValida(k+1, hijo.genes)) {
				if (k == hijo.genes.length-1)
					encontrada = true;
				else vueltaAtras(k+1, hijo, conectividades);
			}
		}
	}

	// si no hay ningun gen repetido hasta donde se ha completado el array (k) devuelve true, en caso contrario devuelve false
	private boolean esValida(int k, Gen[] genes) {
		boolean[] incluidos = new boolean[genes.length+1]; // +1 porque solo hay 27 genes (porque omitimos Madrid), pero hay 28 ciudades
		
		for (int i = 0; i < incluidos.length; i++)
			incluidos[i] = false;
		
		for (int i = 0; i < k; i++) {
			if (incluidos[genes[i].getCiudad()]) return false;
			else incluidos[genes[i].getCiudad()] = true;
		}
		
		return true;
	}

	// devuelve la tabla de conectividades
	private ArrayList<Integer>[] calculaConectividades(Cromosoma padre1, Cromosoma padre2){
		int nGenes = padre1.genes.length, ciudadP1, ciudadP2, anterior, siguiente;
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] conectividades = new ArrayList[nGenes+1]; // +1 para incluir madrid
		
		for (int i = 0; i < nGenes+1; i++)
			conectividades[i] = new ArrayList<Integer>();
		
		// rellenamos la tabla de conectividades
		for (int i = 0; i < nGenes; i++) {
			ciudadP1 = padre1.genes[i].getCiudad();
			ciudadP2 = padre2.genes[i].getCiudad();
			anterior = (nGenes+(i-1))%nGenes;
			siguiente = (nGenes+(i+1))%nGenes;
			
			int ciudad = padre1.genes[anterior].getCiudad();
			
			if (!conectividades[ciudadP1].contains(ciudad))
				conectividades[ciudadP1].add(ciudad);
			ciudad = padre1.genes[siguiente].getCiudad();
			if (!conectividades[ciudadP1].contains(ciudad))
				conectividades[ciudadP1].add(ciudad);
			ciudad = padre2.genes[anterior].getCiudad();
			if (!conectividades[ciudadP2].contains(ciudad))
				conectividades[ciudadP2].add(ciudad);
			ciudad = padre2.genes[siguiente].getCiudad();
			if (!conectividades[ciudadP2].contains(ciudad))
				conectividades[ciudadP2].add(ciudad);
		}
		
		return conectividades;
	}
}
