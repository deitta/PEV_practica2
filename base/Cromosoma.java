package base;

public abstract class Cromosoma {
	public Gen[] genes;
	
	protected double fitness; // aptitud
	protected double punt; // puntRelat = aptitud / sumaAptitud
	protected double puntAcu; // para seleccion
	
	private int nGenes = 27;
	protected double adaptacion;
	
	public Cromosoma() {
		this.genes = new Gen[nGenes];
		for (int i = 0; i < nGenes; i++)
			genes[i] = new Gen(i);
	}

	public void inicializaCromosoma() {
		for (int i = 0; i < getnGenes(); i++) {
			genes[i].inicializaGen(); //este rellena los valores de los genes
		}
	}
	
	public void insertar(int ciudad, int pos) {
		this.genes[pos].ciudad = ciudad;
	}

	public int evaluaCromosoma(){
		int fitness = 0, ciudad = 25;

		for (int i = 0; i < nGenes; i++){
			fitness += genes[i].distanciaA(ciudad);
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

	public abstract boolean isMaximizar();
	
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
		return "(Adap: " + String.format("%.3f", adaptacion) + ", Fit: " + String.format("%.3f", fitness) + ")";
	}
}

