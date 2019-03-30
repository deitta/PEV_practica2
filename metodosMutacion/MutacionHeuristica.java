package metodosMutacion;

import java.util.ArrayList;

import base.Ciudades;
import base.Cromosoma;
import base.Gen;

public class MutacionHeuristica implements AlgoritmoMutacion {

	@Override
	public void mutacion(Cromosoma[] pob, double probMutacion, int tamPob) {
		boolean mutado, repetido;
		double prob;
		int N = 3, numPermutaciones = factorial(N), nGenes = pob[0].getnGenes(), mejorHeuristica;
		ArrayList<Integer> ciudadesSelec = new ArrayList<Integer>(); // guardara las N ciudades seleccionadas para cada individuo
		Gen[] mejorPermutacion = new Gen[nGenes];
		Gen[] permutacion;

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			
			prob = Math.random(); // se genera un numero aleatorio entre [0 1]
			// mutan los genes con prob<probMutacion
			if (prob < probMutacion) {
				// Seleccionas las n ciudades que vas a modificar. Sin repeticiones.
				for (int j = 0; j < N; j++) {
					do {
						repetido = false;
						ciudadesSelec.add((int) (Math.random()*nGenes));
						for (int k = 0; k < j && !repetido; k++) repetido = ciudadesSelec.get(j) == ciudadesSelec.get(k);
						if (repetido) ciudadesSelec.remove(j);
					} while (repetido);
				}

				int ciudad;
				// Buscamos la mejor permutacion al tiempo que las generamos
				mejorPermutacion = pob[i].genes; // por poner uno con el que comparar
				mejorHeuristica = resultadoHeuristica(mejorPermutacion, ciudadesSelec, N);
				for (int j = 0; j < numPermutaciones; j++) { // generamos las numPermutaciones
					permutacion = new Gen[nGenes]; // hacemos new en cada permutacion para que no haya problemas con los punteros
					for (int k = 0; k < nGenes; k++) { // rellenamos cada permutacion con su orden unico, cambiando solo el orden de las ciudades seleccionadas
						ciudad = pob[i].genes[k].getCiudad();
						if (ciudadesSelec.contains(ciudad))
							permutacion[k] = new Gen(ciudadesSelec.get((j+ciudadesSelec.indexOf(ciudad))%N));
						else permutacion[k] = new Gen(ciudad);
					}
					if (resultadoHeuristica(permutacion, ciudadesSelec, N) < mejorHeuristica) {
						mejorHeuristica = resultadoHeuristica(permutacion, ciudadesSelec, N);
						mejorPermutacion = permutacion;
					}
				}
				pob[i].genes = mejorPermutacion; // es redundante por que ambos punteros apuntan a lo mismo?
			}
			
			if (mutado) pob[i].setFitness(pob[i].evaluaCromosoma());
			ciudadesSelec.clear();
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

	private int factorial(int numero){
		if (numero <= 1) return 1;
		else return numero*factorial(numero-1);
	}
}
