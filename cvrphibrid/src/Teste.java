
public class Teste {

	public static void main(String[] args) {
		
//pegando instância do problema  
 
		
		InstanciasProblema instancias = new InstanciasProblema();
		instancias.iniciaInstanciadoArquivo("C:\\Users\\DEREK\\eclipse-workspace\\cvrphibrid\\Entradas\\P-n16-k8.vrp");
		
		System.out.println(instancias);
		
		long tempoInicial = System.currentTimeMillis();
		
//chama o algoritmo centroide baseado na instância criada anteriormente
		
		CentroVRP centroide =new CentroVRP(instancias);
		
		Double solucao=centroide.iniciarCentroVRP(5);
		long tempoFinal = System.currentTimeMillis();
		System.out.println("Tempo de Execução:");
		
//calculando o tempo de execução do centroide VRPC  em mm
 
		System.out.println(tempoFinal-tempoInicial);
		System.out.println("Custo:\t"+solucao);
	
	}
	

}
