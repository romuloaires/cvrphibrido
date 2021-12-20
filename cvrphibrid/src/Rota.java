import java.util.LinkedList;

public class Rota {
	private LinkedList <Clientes> rota; //rota inclui o depósito no ínicio e no fim 
	private double custoRota=0;
	
	
	public Rota(LinkedList <Clientes> rota) {
		this.rota = (LinkedList<Clientes>) rota.clone();
	}
	
		
	public LinkedList<Clientes> getRota() {
		return rota;
	}

	public void setRota(LinkedList<Clientes> rota) {
		this.rota = rota;
	}
	
	
	public double getCustoRota() {
		return custoRota;
	}


	public void setCustoRota(double custoRota) {
		this.custoRota = custoRota;
	}


	public String toString() {
		return "Rota = " + rota + ", custoRota = " + custoRota + "\n";
	}
 
     // Calcula o custo total da rota
    
	public double calCustodaRota (InstanciasProblema instancia) {
		double custoTotal = 0;
		for (int i = 0; i < rota.size(); i++) {
		    Clientes de = rota.get(i);
			Clientes para;
			if (i+1 < rota.size()) {
				para = rota.get(i+1);
			}
			else {
				para = rota.get(0);
			}
			//pega o custo entre o cliente de e para da matriz de Adjacência
			double distDePara = instancia.getmatrizAdajacenc()[de.getId()-1][para.getId()-1];
			custoTotal = custoTotal + distDePara;
		}
		return custoTotal;
	}
}
