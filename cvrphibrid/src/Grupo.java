import java.util.ArrayList;
import java.util.LinkedList;

public class Grupo {
	
	private double CGx; //coordenada x do centro geométrico do grupo
	private double CGy; //coordenada y do centro geométrico do grupo
	
	private  LinkedList <Clientes> clientes = new LinkedList <Clientes>(); //lista de clientes no grupo
	private int capacidadeDisponivel; //capacidade disponível no momento

	public Grupo() {
	}	
	
	
	public String toString() {
		return "Grupo [GCx=" + CGx + ", GCy=" + CGy + ", clientes="
				+ clientes + ", Capacidade Disponivel=" + capacidadeDisponivel + "]\n";
	}

	public int getcapacidadeDisponivel() {
		return capacidadeDisponivel;
	}

	public void setcapacidadeDisponivel(int capacidadeDisponivel) {
		this.capacidadeDisponivel = capacidadeDisponivel;
	}

	public double getCGx() {
		return CGx;
	}

	public void setCGx(double cGx) {
		CGx = cGx;
	}

	public double getCGy() {
		return CGy;
	}

	public void setCGy(double cGy) {
		CGy = cGy;
	}

	public LinkedList<Clientes> getClientes() {
		return clientes;
	}

	public void setClientes(LinkedList<Clientes> clientes) {
		this.clientes = clientes;
	}
	
	
	 // Calcula qual  o cliente que ainda não foi inserido no grupo e que está mais perto 

	public Clientes encontraClienteMaisPertodCentroG (InstanciasProblema instancia) {
		Clientes clientePerto = null;
		double distanciaCurta = Double.POSITIVE_INFINITY;
		double distanciadoCG = 0;
		int idDeposito = instancia.getIdDeposito();
		
		for (int i = 0; i < instancia.getNumClientes(); i++) {
			Clientes c = instancia.getClientes()[i];
			if (i+1 == idDeposito || c.noGrupo() == true) { 
				continue;
			}
			else {
				//calcula a distância do cliente c ao centro geométrico do grupo
				distanciadoCG = c.distanciaEuclediana (this.CGx, this.CGy);
				if (distanciadoCG < distanciaCurta) {
					distanciaCurta = distanciadoCG;
				    clientePerto = c;
				}
			}
		}
		return clientePerto;
		
	}
	
	//calcula o coordenada x do centro geométrico
	public double calculaCGx () {
		int sum = 0;
		for (int i = 0; i < clientes.size(); i++) {
			sum = sum + clientes.get(i).getX();
		}
		return sum/clientes.size();
	}
	
	//calcula o coordenada y do centro geométrico
	public double calculaCGy () {
		int sum = 0;
		for (int i = 0; i < clientes.size(); i++) {
			sum = sum + clientes.get(i).getY();
		}
		return sum/clientes.size();
	}
	
	//adiciona o cliente c ao grupo, e adiciona c ao conjunto de clientes visitados da instância
	public void adicionaclientenoGrupo (Clientes c, InstanciasProblema instancia) {
		clientes.add(c);
		c.setInCluster(true);
		instancia.getvisitaCliente().add(c);
	}

}

