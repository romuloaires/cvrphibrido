
public class Teste {

	public static void main(String[] args) {
		
//pegando inst�ncia do problema  
 
		
		InstanciasProblema instancias = new InstanciasProblema();
		instancias.iniciaInstanciadoArquivo("C:\\Users\\DEREK\\eclipse-workspace\\cvrphibrid\\Entradas\\P-n16-k8.vrp");
		
		System.out.println(instancias);
		
		long tempoInicial = System.currentTimeMillis();
		
//chama o algoritmo centroide baseado na inst�ncia criada anteriormente
		
		CentroVRP centroide =new CentroVRP(instancias);
		
		Double solucao=centroide.iniciarCentroVRP(5);
		long tempoFinal = System.currentTimeMillis();
		System.out.println("Tempo de Execu��o:");
		
//calculando o tempo de execu��o do centroide VRPC  em mm
 
		System.out.println(tempoFinal-tempoInicial);
		System.out.println("Custo:\t"+solucao);
	
	}
	

}
