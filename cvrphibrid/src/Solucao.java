import java.util.ArrayList;

//A classe Solucao tem o conjunto de todas as rotas  e o custo total encontrado
 
public class Solucao {
	
	private ArrayList <Rota> rotas = new ArrayList <Rota>(); 
	private double custoTotal=0;

	public void addRota (Rota rota){
		rotas.add(rota);
	}

	public double getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(double custoTotal) {
		this.custoTotal = custoTotal;
	}
	

	public ArrayList<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(ArrayList<Rota> rotas) {
		this.rotas = rotas;
	}

	
	public String toString() {
		return rotas + "\nCustoTotal=" + custoTotal + "\n";
	}
	
}
