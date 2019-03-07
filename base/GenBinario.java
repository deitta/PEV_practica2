package base;

public class GenBinario extends Gen{
	int[] alelos;
	
	public GenBinario(double max2, double min2, double tolerancia) {
		super(max2, min2, tolerancia);
		calculaLongitud();
		alelos = new int[longitud];
	}
	
	private void calculaLongitud(){
		longitud = (int) Math.ceil(Math.log(1 + (max-min)/tolerancia)/Math.log(2));
	}

	public void inicializaGen(){
		for (int i = 0; i < longitud; i++)
			alelos[i] = (int) (Math.random()*2);
	}
	
	public double fenotipo() {
		double valor = min + (max - min) * binDec()/(Math.pow(2,longitud) - 1);
		return valor;
	}
	
	public void copiaGen(Gen gen){
		this.longitud = gen.longitud;
		this.max = gen.max;
		this.min = gen.min;
		this.tolerancia = gen.tolerancia;
		for (int i = 0; i < longitud; i++){
			this.alelos[i] = ((GenBinario) gen).alelos[i];
		}
	}
	
	private int binDec(){
		int decimal = 0;
		for(int i = 0; i < longitud; i++)
			decimal += Math.pow(2,i)*alelos[i];
		return decimal;
	}

	public int[] getAlelos() {
		return alelos;
	}

	public void setAlelos(int[] alelos) {
		this.alelos = alelos;
	}
	
	public int getAlelo(int pos) {
		return alelos[pos];
	}
	
	public void insertar(int alelo, int pos) {
		alelos[pos] = alelo;
	}
	
	public int getLongitud(){
		return longitud;
	}
}
