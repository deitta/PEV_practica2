package base;

public abstract class Cromosoma {
	public Gen[] genes;
	
	protected double fitness; // aptitud
	protected double punt; // puntRelat = aptitud / sumaAptitud
	protected double puntAcu; // para seleccion
	
	protected int lcrom;
	private int nGenes;
	private double tolerancia;
	protected double adaptacion;
	private int idFuncion;

	int indGen;
	int posGen;
	
	public Cromosoma(double tolerancia, int idFuncion, int nGenes) {
		this.setTolerancia(tolerancia);
		this.setIdFuncion(idFuncion);
		this.genes = new Gen[nGenes];
		this.setnGenes(nGenes);
	}

	public void inicializaCromosoma() {
		for (int i = 0; i < getnGenes(); i++) {
			genes[i].inicializaGen(); //este rellena los valores de los genes
		}
	}
	
	public void inicializaGenes(int indGen, double max, double min){ //solamente crea los genes vacios
		genes[indGen] = new GenBinario(max, min, getTolerancia());
	}
	
	public void cruzar(Cromosoma padre, int pos) { // es el indice del alelo
		indGen = 0;
		posGen = 0;
		obtenIndGen(pos);
		genes[indGen].insertar(((GenBinario) padre.genes[indGen]).getAlelo(posGen), posGen);
	}
	
	public void mutar(int pos) {
		indGen = 0;
		posGen = 0;
		obtenIndGen(pos);
		genes[indGen].insertar(Math.abs(((GenBinario)genes[indGen]).getAlelo(posGen)-1), posGen);
	}

	public abstract double evaluaCromosoma();

	private void obtenIndGen(int pos){
		indGen = 0;
		int posAcum = genes[indGen].getLongitud();
		while (posAcum <= pos && posAcum < lcrom) {
			indGen++;
			posAcum += genes[indGen].getLongitud();
		}
		posGen = pos-(posAcum-genes[indGen].getLongitud());
	}

	public void copiaCromosoma(Cromosoma cromosoma) {
		for (int i = 0; i < nGenes; i++)
			genes[i].copiaGen(cromosoma.genes[i]);
		
		fitness = cromosoma.fitness;
		punt = cromosoma.punt;
		puntAcu = cromosoma.puntAcu;
		
		lcrom = cromosoma.lcrom;
		nGenes = cromosoma.nGenes;
		tolerancia = cromosoma.tolerancia;
		adaptacion = cromosoma.adaptacion;
		idFuncion = cromosoma.idFuncion;
	}
	
// SET & GET

	public abstract boolean isMaximizar();

	public int getLongitud(){
		int l = 0;
		for (int i = 0; i < getnGenes(); i++){
			l += genes[i].getLongitud();
		}
		return l;
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
	
	public int getLcrom(){
		return this.lcrom;
	}

	public int getnGenes() {
		return nGenes;
	}

	public void setnGenes(int nGenes) {
		this.nGenes = nGenes;
	}

	public double getTolerancia() {
		return tolerancia;
	}

	public void setTolerancia(double tolerancia) {
		this.tolerancia = tolerancia;
	}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public double getPunt() {
		return punt;
	}

	// Para la depuracion
	public String toString(){
		return "(Adap: " + String.format("%.3f", adaptacion) + ", Fit: " + String.format("%.3f", fitness) + ")";
	}
}

