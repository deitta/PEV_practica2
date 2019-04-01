package metodosMutacion;

import java.util.ArrayList;

import base.Ciudades;
import base.Cromosoma;
import base.Gen;

public class MutacionHeuristica implements AlgoritmoMutacion {
	int indPermutacion;

	@Override
	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado, repetido;
		double prob;
		int N = 3, numPermutaciones = factorial(N), nGenes = pob[0].getnGenes(), mejorHeuristica;
		ArrayList<Integer> ciudadesSelec = new ArrayList<Integer>(); // guardara las N ciudades seleccionadas para cada individuo
		ArrayList<Integer> posicionesSelec = new ArrayList<Integer>(); // guardara las N ciudades seleccionadas para cada individuo
		Gen[] mejorPermutacion = new Gen[nGenes], permutacion;
		ArrayList<Gen[]> permutaciones = new ArrayList<Gen[]>();

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			
			prob = Math.random(); // se genera un numero aleatorio entre [0 1]
			// mutan los genes con prob<probMutacion
			if (prob < probMutacion) {
				mutado = true;
				permutacion = new Gen[nGenes];
				
				for (int k = 0; k < nGenes; k++)
					permutacion[k] = new Gen(pob[i].genes[k].getCiudad());
				
				for (int j = 0; j < numPermutaciones; j++) { // inicializa el arrayList donde guardamos todas las permutaciones
					permutaciones.add(new Gen[nGenes]);
					for (int k = 0; k < nGenes; k++)
						permutaciones.get(j)[k] = new Gen(pob[i].genes[k].getCiudad());
				}
				
				int ciudad, pos;
				for (int j = 0; j < N; j++) { // Selecciona las n ciudades a modificar. Sin repeticiones.
					do { // guarda la ciudad en ciudadesSelec
						repetido = false;
						ciudad = (int) (Math.random()*nGenes+1);
						repetido = ciudad == 25; // si la ciudad es madrid(25) entonces la descartamos
						for (int k = 0; k < j && !repetido; k++) repetido = ciudad == ciudadesSelec.get(k);
					} while (repetido);
					ciudadesSelec.add(ciudad);

					// guarda la posicion de la ciudadSel(j) en posicionesSelec
					pos = 0;
					while (ciudadesSelec.get(j) != pob[i].genes[pos].getCiudad()) pos++;
					posicionesSelec.add(pos);
					
					// quita las ciudades seleccionadas de las futuras permutaciones. para facilitar la comprobacion en contiene(...)
					for (int k = 0; k < numPermutaciones; k++)
						permutaciones.get(k)[posicionesSelec.get(j)] = new Gen(-1);
					permutacion[posicionesSelec.get(j)] = new Gen(-1);
				}
				
				indPermutacion = 0;
				permuta(pob[i].genes, permutacion, ciudadesSelec, posicionesSelec, permutaciones, N, N); // Rellena el arrayList con todas las posibles permutaciones
				
				// Busca la mejor permutacion de las generadas
				mejorPermutacion = permutaciones.get(0);
				mejorHeuristica = resultadoHeuristica(mejorPermutacion, ciudadesSelec, N);
				for (int j = 1; j < indPermutacion; j++) {
					if (resultadoHeuristica(permutaciones.get(j), ciudadesSelec, N) < mejorHeuristica) {
						mejorPermutacion = permutaciones.get(j);
						mejorHeuristica = resultadoHeuristica(mejorPermutacion, ciudadesSelec, N);
					}
				}
				
				pob[i].genes = mejorPermutacion;
				
				if (mutado) {
					pob[i].setFitness(pob[i].evaluaCromosoma());
					for (int j = 0; j < N; j++) {
						ciudadesSelec.remove(0);
						posicionesSelec.remove(0);
					}
					for (int j = 0; j < numPermutaciones; j++)
						permutaciones.remove(0);
				}
			}
		}

	}
	
	private void permuta(Gen[] genes, Gen[] permutacion, ArrayList<Integer> ciudadesSelec, ArrayList<Integer> posicionesSelec, ArrayList<Gen[]> permutaciones, int N, int k) {
		if (k == 0) {
			for (int i = 0; i < genes.length; i++)
				permutaciones.get(indPermutacion)[i].setCiudad(permutacion[i].getCiudad());
			indPermutacion++;
		}
		else {
			int ciudad;
			for (int j = 0; j < N; j++) {
				ciudad = ciudadesSelec.get(j);
				if (!contiene(permutacion, ciudad)) {
					permutacion[posicionesSelec.get(k-1)] = new Gen(ciudadesSelec.get(j));
					permuta(genes, permutacion, ciudadesSelec, posicionesSelec, permutaciones, N, k-1);
				}
			}
			permutacion[posicionesSelec.get(k-1)] = new Gen(-1);
		}
	}
	
	// Devuelve el resultado de aplicar la heuristica -> la suma de las distancias entre las ciudades movidas
	private int resultadoHeuristica(Gen[] permutacion, ArrayList<Integer> ciudadesSelec, int N) {
		int h = 0;
		for (int i = 0; i < permutacion.length; i++) {
			if (ciudadesSelec.contains(permutacion[i].getCiudad())) {
				if (i > 0) h += Ciudades.distanciaA(permutacion[i].getCiudad(), permutacion[i-1].getCiudad());
				if (i < permutacion.length-1) h += Ciudades.distanciaA(permutacion[i].getCiudad(), permutacion[i+1].getCiudad());
			}
		}
		return h;
	}
	
	private boolean contiene(Gen[] genes, int ciudad) {
		for (int i = 0; i < genes.length; i++)
			if (genes[i].getCiudad() == ciudad)
				return true;
		return false;
	}

	private int factorial(int numero){
		if (numero <= 1) return 1;
		else return numero*factorial(numero-1);
	}
}
