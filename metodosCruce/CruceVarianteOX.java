package metodosCruce;

import base.Cromosoma;

public class CruceVarianteOX implements AlgoritmoCruce {
	public void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2) {
ArrayList<Integer> c1 = new Arraylist();
ArrayList<Integer> c2 = new Arraylist();
Randon r = new Random();

for (int i = 0; i < padre1.nGenes;i++){
	double valor = Math.random();
	if(valor>prob)
		c1.add(padre1.genes[i]);	
}
for (int i = 0; i < c1.size();i++){
	c2.add( funcionaux(c1.get(i),0,padre1.nGenes-1,padre2) );
}
int j =0;
for (int i = 0; i < padre2.nGenes;i++){
	if (!c2.contains(i))
 		hijo1.genes[i] = padre2.genes[i];
	else {
		hijo1.genes[i].setValor(c1.get(j));
		j++;
	}

}



private int funcionaux(int num, int puntDC1, int puntDC2, Cromosoma cromosoma){
	while(num != cromosoma.genes[puntDC1].getCiudad() && puntDC1 <= puntDC2)
		puntDC1++;
	if (puntDC1 > puntDC2) return -1;
	return puntDC1;
}





	}

}
