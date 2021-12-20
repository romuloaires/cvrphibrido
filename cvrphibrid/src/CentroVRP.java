import java.util.ArrayList;



 // A classe CentroVRP tem a lista de grupos criados e a inst�ncia do problema que vai ser resolvido
 
public class CentroVRP {
	
	private ArrayList <Grupo> grupos = new ArrayList <Grupo>();
	private InstanciasProblema instancias;
	Double melhorSolucaoC;
	
	
	public CentroVRP(InstanciasProblema instancias) {
		this.instancias = instancias;
	}
	
	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<Grupo> grupos) {
		this.grupos = grupos;
	}

	
	 // retorna a melhor solu��o com as rotas e o custo total
	
	public Double iniciarCentroVRP(int maxdeIteracSimul) {
		construindoGrupos (); //fase 1: constru��o dos grupos
		
	    Solucao solucao = aplicaCMCGrupos(maxdeIteracSimul); //encontrar a melhor solu��o 
		Solucao melhorSolucaoA = solucao;
		
		do {
			//fase 2: ajustamento de grupos
			boolean resp = ajustaGrupo();
			if (!resp) {
				break;
			}
			else {
				
				//se os grupos foram ajustados, encontrou a rota subotima usando KNN 
				TSPNearestNeighbour tspNearestNeighbour = new TSPNearestNeighbour();
				double melhorsolucaoB=tspNearestNeighbour.tsp(instancias.getmatrizAdajacenc());
				System.out.println("nearest:"+ melhorsolucaoB);
							
                if (melhorSolucaoA.getCustoTotal() < melhorsolucaoB){
               	 melhorSolucaoC =  Double.valueOf(melhorSolucaoA.getCustoTotal()).doubleValue();
               	 
               	 }else {
               	 melhorSolucaoC =  Double.valueOf(melhorsolucaoB).doubleValue();
               	 }
 			}
				
		} while (true);
		return melhorSolucaoC;
	}
	
	 // usando Simulated Annealing para cada um dos grupos
	 
	 //retorna todas as rotas e o custo total de todas as rotas
	 
	public Solucao aplicaCMCGrupos(int maxIterSimulated){
		Solucao solucao =  new Solucao();
		
        double custoTotal = 0;
		for (int i = 0; i < grupos.size(); i++) {
			Cmc cmcurto = new Cmc(instancias, grupos.get(i).getClientes());
			
			//para o grupo i � chamado o Simulated Annealing para encontrar a rota com o melhor custo 
			
			double melhorCusto = cmcurto.simulatedAnnealing();
			cmcurto.getRota().setCustoRota(melhorCusto);
			Rota melhorRota=cmcurto.getRota();
			
			/*Como o Simulated Annealing pode dar resultados diferentes para cada chamada devido aos valores rand�micos que s�o
			 * usados, chamamos o Simulated Annealing maxIterSimulated vezes e peagamos a melhor rota encontra nessas maxIterSimulated
			 * itera��es
			 * */
			for (int j = 1; j < maxIterSimulated; j++){
				double cost = cmcurto.simulatedAnnealing();
				cmcurto.getRota().setCustoRota(cost);
	            if (cost<melhorCusto){
	            	melhorCusto=cost;
	            	melhorRota = cmcurto.getRota();
	            }
			}
			
			solucao.addRota(melhorRota);
			custoTotal = custoTotal + melhorCusto ;
		}
		solucao.setCustoTotal(custoTotal);
		return solucao;
	}
	
	
    /*para gerar a semente de cada grupo �
	encontrado o cliente  mais distante do dep�sito e
	 que ainda nao foi agrupado */
	
	public Clientes maisDistanteDeposito () {
		Clientes clienteMDistante = null;
		double distMDistante = 0;
		double distDeposito = 0;
		int idDeposito = instancias.getIdDeposito();
		
		for (int i = 0; i < instancias.getNumdeClientes(); i++) {
			Clientes c = instancias.getClientes()[i];
			if (i+1 == idDeposito || c.noGrupo() == true) { 
				continue;
			}
			else {
				/*se o cliente c n�o est� no grupo e nao � o dep�sito,
				 *  ent�o pega da matriz de adjac�ncia
				 *  a dist�ncia ao dep�sito*/
				distDeposito = instancias.getmatrizAdajacenc()[i][idDeposito-1];
				if (distDeposito > distMDistante) {
					distMDistante = distDeposito;
					clienteMDistante = c;
				}
			}
			
		}
		return clienteMDistante;
		
	}
	
	//Fase 1: construi todos os grupos da instancia do problema
    public void construindoGrupos () {
		
		int Q = PortaMala.capacidade;
		/*enquanto ainda existam clientes ainda n�o visitados,
		contruir os grupos*/
		while (instancias.getvisitaCliente().size() < (instancias.getNumdeClientes()-1)) {
			Clientes vj = maisDistanteDeposito (); //vj ser� a semente do grupo
			Grupo l = new Grupo ();
			//o grupo l � criado e adicionado ao conjunto do grupo
			grupos.add(l);
			l.setcapacidadeDisponivel(Q);
			
			/*enquanto ainda existam clientes ainda n�o visitados e
			a demanda do cliente n�o ultrapassa a capacidade restante
			dispon�vel no ve�culo */ 
			
			while (instancias.getvisitaCliente().size() < (instancias.getNumdeClientes()-1) && l.getcapacidadeDisponivel() >= vj.getDemanda()) {
				// ent�o o cliente vj � adicionado no cluster e o centro geom�trico � recalculado
				l.adicionaclientenoGrupo (vj, instancias);
				l.setcapacidadeDisponivel(l.getcapacidadeDisponivel() - vj.getDemanda());
				l.setCGx(l.calculaCGx());
				l.setCGy(l.calculaCGy());
				vj = l.encontraClienteMaisPertodCentroG (instancias);
			}
		}
    }
    
    //Fase 2: ajustamento dos grupos gerados na fase 1 PAREI AQUI
    public boolean ajustaGrupo () {
    	boolean ajustado = false;
    	for (int i = 0; i < grupos.size(); i++) {
    		Grupo li=grupos.get(i);
    		/* Verifica-se para cada cliente i do grupo li se ele est� mais pr�ximo do centroide 
    		  do grupo vizinho i do que o centroide de seu grupo atual. 
    		  Caso isso ocorra e a capacidade do grupo vizinho n�o seja ultrapassada, ent�o
    		  esse cliente vai para o grupo vizinho e os centroides de ambos grupos s�o recalculados.  */
    		for (int k=0; k< li.getClientes().size();k++) {
    			Clientes vk=li.getClientes().get(k);
    			for (int j = 0; j < grupos.size(); j++) {
    				Grupo lj = grupos.get(j);
    				
    				double distanciaVkdoCGLj = vk.distanciaEuclediana (lj.getCGx(), lj.getCGy());
    				double distanciaVkdoCGLi = vk.distanciaEuclediana (li.getCGx(), li.getCGy());
    				if (i != j && distanciaVkdoCGLj < distanciaVkdoCGLi && lj.getcapacidadeDisponivel() >= vk.getDemanda()) {
    					li.getClientes().remove(vk);
    					lj.getClientes().add(vk);
    					li.setcapacidadeDisponivel(li.getcapacidadeDisponivel() + vk.getDemanda());
    					lj.setcapacidadeDisponivel(lj.getcapacidadeDisponivel() - vk.getDemanda());
    					
    					// recalcula o centro geometrico
    					li.setCGx(li.calculaCGx());
    					li.setCGy(li.calculaCGy());
    					
    					lj.setCGx(lj.calculaCGx());
    					lj.setCGy(lj.calculaCGy());
    					
    					
    					ajustado = true;
    				}
    			}
    		}
    	}
    	return ajustado;
    }

}
