import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


 //A classe cmc(caminho mais curto) tem a inst�ncia do problema, os clientes do grupo a partir de qual se criar� a rota
 
public class Cmc {
	private InstanciasProblema instancia;
	private LinkedList <Clientes> grupo;  //n�o inclui o dep�sito
    private Rota rota; // inclui o dep�sito
	
	public Cmc(InstanciasProblema instancia, LinkedList <Clientes> grupo) {
		this.instancia = instancia;
		this.grupo=grupo;
		criaRotaCliente();
	}
	
	//a rota � criada colocando todos os clientes nela. O dep�sito � colocado no inicio e no fim da rota
    public void criaRotaCliente(){
    	Clientes deposito=instancia.getDeposito();
		rota = new Rota(grupo);
		rota.getRota().addFirst(deposito);
		rota.getRota().addLast(deposito);
    }

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	//  O algoritmo Simulated Annealing � aplicado na rota criada retornando a rota de menor custo
	 
	public double simulatedAnnealing () {
		
		int tamanhoRota = rota.getRota().size();   
		
		Clientes c1;
		Clientes c2;
		
		if(tamanhoRota == 3) //apenas um cliente
			return  rota.calCustodaRota(instancia); 
		else if(tamanhoRota == 4) { //2 clientes
			double custo = rota.calCustodaRota(instancia);
			//troco os dois clientes de posi��o na rota e vejo se da uma melhor solu��o 
			c1 = rota.getRota().get(1);
			c2 = rota.getRota().get(2);
			rota.getRota().set(1, c2);
			rota.getRota().set(2, c1);
			
			double novoCusto = rota.calCustodaRota(instancia);
			
			if(novoCusto <= custo) return novoCusto;
			else { // caso contr�rio, desfa�o essa troca
				rota.getRota().set(1, c1);
				rota.getRota().set(2, c2);
				return custo;
			}
		}
		
		// fun��o de resfriamento inicializando vari�veis de temperatura, resfriamento
		
 		double temperatura = (double) (tamanhoRota-2)*(tamanhoRota-2);
		double resfriamento = (double) (1/(double)((tamanhoRota-2)*(tamanhoRota-2)));
		int numMudanca = 0;
		int maxnumMudanca = (tamanhoRota-2)*(tamanhoRota-2);
		
		//cria��o de uma rota aleat�ria para servir como primeira solu��o
		Collections.shuffle(grupo);
		criaRotaCliente();
		
		//o custo dessa rota � criado
		double custo = rota.calCustodaRota(instancia);
		
		//enquanto a condi��o � cumprida, � criada uma solu��o vizinha trocando dois clientes de maneira aleat�ria
		while(temperatura >= resfriamento || verquantMudanca(numMudanca,maxnumMudanca)) {
			int pos1;// = randomInt(0, sizeRoute);
			int pos2;// = randomInt(0, sizeRoute);
			//pega-se duas posi��es aleat�rias de clientes na rota para serem trocadas
			do{
				pos1 = randomInt(1, tamanhoRota-1);
				pos2 = randomInt(1, tamanhoRota-1);
			}while(pos1 == pos2);


			c1 = rota.getRota().get(pos1);
			c2 = rota.getRota().get(pos2);
			rota.getRota().set(pos1, c2);
			rota.getRota().set(pos2, c1);
			double novoCusto = rota.calCustodaRota(instancia);

			boolean mudanca= false;
			
			//se o custo dessa nova rota com essa troca de posi��es for menor, a nova solu��o � aceita como melhor
			
			if(novoCusto > custo) { 
				//se o novo custo � maior, s� aceitamos essa nova solu��o se aceitacao for true
				boolean aceitacao = aceita(custo, novoCusto, temperatura);
				if(aceitacao == false) { //a troca � desfeita
					rota.getRota().set(pos1, c1);
					rota.getRota().set(pos2, c2);
					mudanca = false;
				}
				else {
					custo = novoCusto;
					mudanca = true;
				}
			}
			else { //temos uma nova solu��o que � melhor
				custo = novoCusto;
				mudanca = true;
			}
			
			
			if(mudanca == true) {
				numMudanca = numMudanca + 1;
			}
			else numMudanca = numMudanca - 1;
			//a temperatura � diminuida, o sistema vai esfriando
			temperatura = temperatura - (temperatura*resfriamento);
		}
 	    return custo; 
	}

    // retorna um random [min,max]
    public int randomInt (int min, int max) {
	      Random r = new Random();
	      double d = min + r.nextDouble()*(max-min);
	      return (int) d;
    }
    
    //retorna um random [0,1]
    public static double randomDouble() {
    	  Random r = new Random();
	      return r.nextInt(1000)/1000.0;
    }
	
    //devolve se aceita ou n�o a nova solu��o vizinha
    private static boolean aceita(double custo, double novoCusto, double temperatura) {
		double deltaE = custo - novoCusto; //deltaE � sempre negativo
		double probalidade = Math.pow(Math.E, deltaE/temperatura); //fun��o da probabilidade de aceita��o � e^(deltaE/temperatura)

		double random = randomDouble();
		// retorna true se probabilidade de aceita��o for maior que um random gerado 
		if(probalidade > random) return true;
		else return false;
	}
    
    //verifica se a quantidade de trocas feitas  est� entre o numero de mudanca e o numero maximo de mudanca
    private static boolean  verquantMudanca(int numMudanca, int maxNumMudanca) {
		int inverso = maxNumMudanca * -1;
		if(numMudanca <= maxNumMudanca && numMudanca >= inverso) return true;
		return false;
	}

}