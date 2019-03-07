package base;

public abstract class Gen {

	double max, min, tolerancia;
	int longitud;
	
	public Gen(double max, double min, double tolerancia){
		this.max = max;
		this.min = min;
		this.tolerancia = tolerancia;
		
	}

	public abstract void inicializaGen();

	public abstract double fenotipo();
	
	public abstract void copiaGen(Gen gen);

	public int getLongitud() {
		return 1;
	}
	
	public void insertar(int i, int posGen) {
	}
	
}
