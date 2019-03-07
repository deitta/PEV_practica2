package base;

import cromosoma.FactoriaCromosoma;
import metodosCruce.AlgoritmoCruce;
import metodosCruce.FactoriaCruce;
import metodosSeleccion.AlgoritmoSeleccion;
import metodosSeleccion.FactoriaSeleccion;

public class AlgoritmoGenetico {
	Cromosoma[] pob;
	Cromosoma elMejor;
	int tamPob;
	int numMaxGen; //# max de generaciones
	int posMejor;
	double probCruce; //si es menor cruza
	double probMutacion; // si es menor muta
	double tolerancia;  // precision
	
	String seleccion;
	String cruceBinario;
	String cruceReal;
	int participantes;
	int idFuncion;
	int nGenes;
	double elitismo; // elitismo*tamPob = tamElite
	Cromosoma[] elite;


	// Para graficas
	double[] genMedia; //rojo
	double[] genMejor; //verde
	double[] mejorAbsoluto; //azul
	
	public AlgoritmoGenetico() {
		tamPob = 100;
		numMaxGen = 100;
		posMejor = 0;
		idFuncion = 1;
		probCruce = 0.6;
		probMutacion = 0.05;
		tolerancia = 0.001;
		seleccion = "Ruleta";
		cruceBinario = "Monopunto";
		cruceReal = "Monopunto";
		participantes = 3;
		nGenes = 2;
		elitismo = 0;
		pob = new Cromosoma[tamPob];
		elite = new Cromosoma[(int) (tamPob*elitismo)];

		for (int i = 0; i < tamPob; i++)
			pob[i] = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
		for (int i = 0; i < (int) (tamPob*elitismo); i++)
			elite[i] = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
		elMejor = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
	}
	
	public void inicializaPoblacion() {
		pob = new Cromosoma[tamPob];
		elite = new Cromosoma[(int) (tamPob*elitismo)];

		genMedia = new double[numMaxGen];
		genMejor = new double[numMaxGen];
		mejorAbsoluto = new double[numMaxGen];

		for (int i = 0; i < tamPob; i++) {
			pob[i] = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
			pob[i].inicializaCromosoma();
			pob[i].fitness = pob[i].evaluaCromosoma();
		}
		for (int i = 0; i < (int) (tamPob*elitismo); i++)
			elite[i] = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);

		elMejor = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
		elMejor.copiaCromosoma(pob[0]);
	}

	public void evalua(){
		double puntAcu = 0; // puntuación acumulada
		double sumaAptitud = 0; // suma de la aptitud
		
		sumaAptitud = pob[0].adaptacion;
		posMejor = 0;
		for (int i = 1; i < tamPob; i++){
			sumaAptitud += pob[i].adaptacion;
			if (pob[i].adaptacion > pob[posMejor].adaptacion){
				posMejor = i;
			}
		}

		for (int i = 0; i < tamPob; i++){
			pob[i].punt = pob[i].adaptacion / sumaAptitud;
			pob[i].puntAcu = pob[i].punt + puntAcu;
			puntAcu += pob[i].punt;
		}

		if(pob[posMejor].adaptacion > elMejor.adaptacion)
				elMejor.copiaCromosoma(pob[posMejor]);
	}

	private void adaptarMinimizacion(int tamElite){
		double fmax = pob[0].fitness;

		for (int i = 1; i < tamPob; i++)
			if (pob[i].fitness > fmax)
				fmax = pob[i].fitness;
		for (int i = 0; i < tamElite; i++)
			if (elite[i].fitness > fmax)
				fmax = elite[i].fitness;
		if (elMejor.fitness > fmax)
			fmax = elMejor.fitness;
		
		fmax *= 1.05; // margen para evitar suma adaptacion = 0

		// adapta la poblacion
		for (int i = 0; i < tamPob; i++)
			pob[i].adaptacion = fmax - pob[i].fitness;

		// adapta la elite
		for (int i = 0; i < tamElite; i++)
			elite[i].adaptacion = fmax - elite[i].fitness;

		// adapta el mejor
		elMejor.adaptacion = fmax - elMejor.fitness;
	}

	private void adaptarMaximizacion(int tamElite){
		double fmin = pob[0].fitness;

		for (int i = 1; i < tamPob; i++)
			if (pob[i].fitness < fmin)
				fmin = pob[i].fitness;
		for (int i = 0; i < tamElite; i++)
			if (elite[i].fitness < fmin)
				fmin = elite[i].fitness;
		if (elMejor.fitness < fmin)
			fmin = elMejor.fitness;
		
		fmin = Math.abs(fmin * 1.05); // margen para evitar suma adaptacion = 0

		// adapta la poblacion
		for (int i = 0; i < tamPob; i++)
			pob[i].adaptacion = pob[i].fitness + fmin;

		// adapta la elite
		for (int i = 0; i < tamElite; i++)
			elite[i].adaptacion = elite[i].fitness + fmin;

		// adapta el mejor
		elMejor.adaptacion = elMejor.fitness + fmin;
	}
	
	public void seleccion(){
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		
		for (int i = 0; i < tamPob; i++)
			nuevaPob[i] = FactoriaCromosoma.getFuncionCromosoma(idFuncion, tolerancia, nGenes);
		
		AlgoritmoSeleccion algoSeleccion = FactoriaSeleccion.getAlgoritmoDeSeleccion(seleccion, participantes);
		algoSeleccion.seleccion(pob, nuevaPob, tamPob);
		pob = nuevaPob;
	}
	
	public void cruce(){
		AlgoritmoCruce algoCruce;
		if (idFuncion != 5) algoCruce = FactoriaCruce.getAlgoritmoDeCruce(cruceBinario);
		else algoCruce = FactoriaCruce.getAlgoritmoDeCruce(cruceReal);
		algoCruce.cruce(pob, tamPob, probCruce);
	}
	
	public void mutacion(){
		boolean mutado;
		double prob;

		for (int i = 0; i < tamPob; i++) {
			mutado = false;
			for (int j = 0; j < pob[0].lcrom; j++) {
				// se genera un numero aleatorio entre [0 1]
				prob = Math.random();
				// mutan los genes con prob<probMutacion
				if (prob < probMutacion){
					pob[i].mutar(j);
					mutado = true;
				}
			}
			if (mutado)
				pob[i].fitness = pob[i].evaluaCromosoma();
		}
	}

	public void separaElite(int tamElite){
		int posPeor = 0; // apunta al peor individuo dentro de la elite

		// coge a los primeros de la poblacion como a la elite
		// buscamos el peor dentro de la elite
		for (int i = 0; i < tamElite; i++){
			elite[i].copiaCromosoma(pob[i]);
			if (elite[i].adaptacion < elite[posPeor].adaptacion) posPeor = i;
		}

		// recorre la poblacion buscando a los mejores individuos
		for (int i = tamElite; i < tamPob; i++){
			// si encontramos a algun individuo mejor que el peor de la elite este ocupara su lugar y volvemos a buscar el peor dentro de la elite
			if (elite[posPeor].adaptacion < pob[i].adaptacion) {
				elite[posPeor].copiaCromosoma(pob[i]);
				for (int j = 0; j < tamElite; j++)
					if (elite[j].adaptacion < elite[posPeor].adaptacion) posPeor = j;
			}
		}
	}

	public void incluyeElite(int tamElite){
		// indices en pob de los peores de la poblacion
		int[] pobPeores = new int[tamElite];
		int posMejor = 0; // apunta al mejor individuo dentro de pobPeores
		
		// coge a los primeros de la poblacion como a los peores
		// y buscamos el mejor dentro de los peores
		for (int i = 0; i < tamElite; i++){
			pobPeores[i] = i;
			if (pob[pobPeores[i]].adaptacion > pob[pobPeores[posMejor]].adaptacion) posMejor = i;
		}

		// recorre la poblacion buscando a los peores individuos
		for (int i = tamElite; i < tamPob; i++){
			// si encontramos a algun individuo peor que el mejor de los individuosPeores este ocupara su lugar y volvemos a buscar el mejor dentro de los peores
			if (pob[pobPeores[posMejor]].adaptacion > pob[i].adaptacion) {
				pobPeores[posMejor] = i;
				for (int j = 0; j < tamElite; j++)
					if(pob[pobPeores[j]].adaptacion > pob[pobPeores[posMejor]].adaptacion) posMejor = j;
			}
		}

		// Sustituimos a los peores de la poblacion por la elite
		for (int i = 0; i < tamElite; i++)
			pob[pobPeores[i]].copiaCromosoma(elite[i]);
	}

	private void media(int generacion){
		double sumaAbs = 0;

		for (int i = 0; i < tamPob; i++)
			sumaAbs += pob[i].fitness;

		genMedia[generacion] = sumaAbs/tamPob;
	}

	private void mejor(int generacion){
		genMejor[generacion] = pob[posMejor].fitness;
	}

	private void mejorAbs(int generacion){
		if (generacion == 0) mejorAbsoluto[0] = pob[posMejor].fitness;
		else if (pob[0].isMaximizar()){
			if (mejorAbsoluto[generacion-1] < pob[posMejor].fitness)
				mejorAbsoluto[generacion] = pob[posMejor].fitness;
			else mejorAbsoluto[generacion] = mejorAbsoluto[generacion-1];
		} else{
			if (mejorAbsoluto[generacion-1] > pob[posMejor].fitness)
				mejorAbsoluto[generacion] = pob[posMejor].fitness;
			else mejorAbsoluto[generacion] = mejorAbsoluto[generacion-1];
		}
	}
	
	public void AlgoritmoGeneticoFuncion(){
		int generacionActual = 0;
		int tamElite = (int) (tamPob*elitismo);
		
		inicializaPoblacion();

		if(pob[0].isMaximizar()) adaptarMaximizacion(tamElite);
		else adaptarMinimizacion(tamElite);
		
		evalua();
		
		while (generacionActual < numMaxGen) {
			if (tamElite > 0) separaElite(tamElite);
			
			seleccion();
			cruce();
			mutacion();

			if (tamElite > 0) incluyeElite(tamElite);

			if(pob[0].isMaximizar()) adaptarMaximizacion(tamElite);
			else adaptarMinimizacion(tamElite);

			evalua();
			
			// para las graficas
			media(generacionActual);
			mejor(generacionActual);
			mejorAbs(generacionActual);

			generacionActual++;
		}
	}




	//Getters and setters

	public double[] getGenMedia() {
		return genMedia;
	}

	public double[] getGenMejor() {
		return genMejor;
	}

	public double[] getMejorAbsoluto() {
		return mejorAbsoluto;
	}

	public int getNumMaxGen() {
		return numMaxGen;
	}

	public void setNumMaxGen(int numMaxGen) {
		this.numMaxGen = numMaxGen;
	}

	public double getProbCruce() {
		return probCruce;
	}

	public void setProbCruce(double probCruce) {
		this.probCruce = probCruce;
	}

	public double getProbMutacion() {
		return probMutacion;
	}

	public void setProbMutacion(double probMutacion) {
		this.probMutacion = probMutacion;
	}

	public double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public String getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String seleccion) {
		this.seleccion = seleccion;
	}

	public String getCruceBinario() {
		return cruceBinario;
	}

	public void setCruceBinario(String cruce) {
		this.cruceBinario = cruce;
	}

	public String getCruceReal() {
		return cruceReal;
	}

	public void setCruceReal(String cruce) {
		this.cruceReal = cruce;
	}

	public int getParticipantes() {
		return participantes;
	}

	public void setParticipantes(int participantes) {
		this.participantes = participantes;
	}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public int getnGenes() {
		return nGenes;
	}

	public void setnGenes(int nGenes) {
		this.nGenes = nGenes;
	}

	public int getTamPob() {
		return tamPob;
	}

	public void setTamPob(int tamPob) {
		this.tamPob = tamPob;
	}
	
	public double getElitismo() {
		return elitismo;
	}

	public void setElitismo(double elitismo) {
		this.elitismo = elitismo;
	}

	public Cromosoma getElMejor() {
		return elMejor;
	}

	
	
	

	// Para depurar
	public String toString(){
		String agString = "Elite: ";
		for (int i = 0; i < elite.length - 1; i++)
			agString += elite[i].toString() + ", ";
		if (elite.length - 1 >= 0) agString += elite[elite.length - 1].toString();
		agString += "\nPoblacion:";

		for (int i = 0; i < pob.length - 1; i++)
			agString += pob[i].toString() + ", ";
		if (pob.length - 1 >= 0) agString += pob[pob.length - 1].toString() + "\nEl mejor: " + elMejor.toString();
		
		
		return agString;
	}
}
