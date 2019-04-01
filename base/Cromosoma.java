package base;

public class Cromosoma {
	public Gen[] genes;
	
	protected double fitness; // aptitud
	protected double punt; // puntRelat = aptitud / sumaAptitud
	protected double puntAcu; // para seleccion
	
	private int nGenes = 27; // 27 genes porque son 28 ciudades, pero siempre empezamos y acabamos por la misma, madrid.
	protected double adaptacion;
	private boolean maximizar = false;
	
	public Cromosoma() {
		this.genes = new Gen[nGenes];
		inicializaCromosoma();
	}

	public void inicializaCromosoma() {
		int ciudad;
		boolean incluidos[] = new boolean[nGenes+1];
		
		for (int i = 0; i < nGenes; i++)
			incluidos[i] = false;
		
		for (int i = 0; i < getnGenes(); i++) {
			do {
				ciudad = (int) (Math.random()*(nGenes+1)); //Ponemos mas uno porque el numero de ciudades que hay es 27, una mas que genes en nuestro cromosoma
			} while(incluidos[ciudad] || ciudad == 25); // descartamos la ciudad 25 porque corresponde a Madrid que es la primera en recorrer y por tanto la obviamos del cromosoma.
			genes[i] = new Gen(ciudad);
			incluidos[ciudad] = true;
		}
	}
	
	// creo que sobra
	public void insertar(int ciudad, int pos) {
		this.genes[pos].ciudad = ciudad;
	}

	public int evaluaCromosoma(){
		int fitness = 0, ciudad = 25;

		for (int i = 0; i < nGenes; i++){
			fitness += Ciudades.distanciaA(genes[i].ciudad, ciudad);
			ciudad = genes[i].ciudad;
		}

		return fitness;
	}

	public void copiaCromosoma(Cromosoma cromosoma) {
		for (int i = 0; i < nGenes; i++)
			genes[i].copiaGen(cromosoma.genes[i]);
		
		fitness = cromosoma.fitness;
		punt = cromosoma.punt;
		puntAcu = cromosoma.puntAcu;
		
		nGenes = cromosoma.nGenes;
		adaptacion = cromosoma.adaptacion;
	}
	
// SET & GET

	public boolean isMaximizar(){
		return maximizar;
	}
	
	public double getFitness(){
		return this.fitness;
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}

	public double getPuntAcu() {
		return puntAcu;
	}

	public int getnGenes() {
		return nGenes;
	}

	public void setnGenes(int nGenes) {
		this.nGenes = nGenes;
	}

	public double getPunt() {
		return punt;
	}

	// Para la depuracion
	public String toString(){
		String cromosoma = "(Adap: " + String.format("%.3f", adaptacion) + ", Fit: " + String.format("%.3f", fitness) + ")\n";
		for (int i = 0; i < nGenes - 1; i++){
			cromosoma += '[' + Integer.toString(i) + "] " + this.genes[i].toString() + ", ";
		}
		cromosoma += '[' + Integer.toString(nGenes - 1) + "] " + this.genes[nGenes - 1].toString();

		return cromosoma;
	}

	public double getAdptacion() {
		return adaptacion;
	}
}

