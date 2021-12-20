import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

// A classe possui a matriz de adjac�ncia, o n�mero de clientes, os clientes, o dep�sito e seu identificador, a lista dos clientes visitados


 
public class InstanciasProblema {
	private double [][] matrizAdajacenc;
	private int numclientes;
	private Clientes [] clientes;
	private int idDeposito;
	private Clientes deposito;
	private ArrayList <Clientes> visitaCliente;

	public InstanciasProblema() {
		this.visitaCliente = new ArrayList <Clientes> ();
	}
	
	public Clientes getDeposito() {
		return deposito;
	}

	public void setDeposito(Clientes deposito) {
		this.deposito = deposito;
	}

	public int getNumClientes() {
		return numclientes;
	}

	public void setNumClientes(int numclientes) {
		this.numclientes = numclientes;
	}

	public ArrayList<Clientes> getvisitaCliente() {
		return visitaCliente;
	}

	public void setvisitaCliente(ArrayList<Clientes> visitaCliente) {
		this.visitaCliente = visitaCliente;
	}

	public double[][] getmatrizAdajacenc() {
		return matrizAdajacenc;
	}

	public void setMatrizAdjacen(double[][] matrizAdajacenc) {
		this.matrizAdajacenc= matrizAdajacenc;
	}
	
	public int getNumdeClientes() {
		return numclientes;
	}

	
	public void setNumdeClientes(int numdecliente) {
		this.numclientes = numdecliente;
	}
	
	public Clientes[] getClientes() {
		return clientes;
	}

	public void setClientes(Clientes[] nodes) {
		this.clientes = nodes;
	}
	
	public int getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(int idDeposito) {
		this.idDeposito = idDeposito;
	}
	
	
	public String toString() {
		return "InstanciasProblema [" +				
				"numclientes = "
				+ numclientes + ", idDeposito = " + idDeposito + "]";
	}

    //a matriz de adjacente � criada calculando a dist�ncia entre cada cliente i e cada cliente j
	
	public void criaMatrizAdjacente () {
		matrizAdajacenc = new double [numclientes][numclientes];
		for (int i = 0; i < numclientes; i++) {
			for (int j = 0; j < numclientes; j++) {
				matrizAdajacenc[i][j] = clientes[i].distanciaEuclediana(clientes[j]);
			}
				
		}
	}	
	
	//leitura do arquivo de dados  
	public void iniciaInstanciadoArquivo (String arquivo) {
		
		InputStream leitura;
		try {
			leitura = new FileInputStream (arquivo);
			InputStreamReader adaptador = new InputStreamReader(leitura);
			BufferedReader lelinha = new BufferedReader(adaptador);
			lelinha.readLine();
			lelinha.readLine();
			lelinha.readLine();
			
			String dimensaoString = lelinha.readLine();
			dimensaoString = dimensaoString.replace("DIMENSAO : ", "");
			numclientes = Integer.parseInt(dimensaoString); 

			//cria-se um vetor de clientes de tamanho numClientes para a inst�ncia
			clientes = new Clientes [numclientes];
			
			lelinha.readLine();
			String capacidadeString = lelinha.readLine();
			capacidadeString = capacidadeString.replace("CAPACIDADE : ", "");
			PortaMala.capacidade = Integer.parseInt(capacidadeString); //capacidade dos ve�culos
			lelinha.readLine();
			
			//para cada cliente � lido o seu id, e sua posi��o
			int id;
			int i;
			int x;
			int y;
			String linha;
			for(i  =0; i < numclientes; i++) {
				linha = lelinha.readLine();
				
				Scanner scan = new Scanner(linha);
				id = scan.nextInt();
				x = scan.nextInt();
				y = scan.nextInt();
				scan.close();
				
				Clientes novoNo = new Clientes(id, x, y);
				clientes[i] = novoNo;
			}
			
            lelinha.readLine(); 
			
			int demanda;
			//leitura da demanda pra cada cliente
			for(i=0;i<numclientes;i++) {
				linha = lelinha.readLine();
				
				Scanner scan = new Scanner(linha);
				scan.nextInt();
				demanda = scan.nextInt();
				clientes[i].setDemanda(demanda);
				
				scan.close();
			}
			lelinha.readLine(); 
			
			linha = lelinha.readLine();
			Scanner scan = new Scanner(linha);
			idDeposito = scan.nextInt();
			//cria��o do dep�sito
			deposito = new Clientes(idDeposito, clientes[idDeposito-1].getX(), clientes[idDeposito-1].getY());
			
			scan.close();
			lelinha.close();
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(NoSuchElementException e) {
			System.out.println("Arquivo no formato invalido");
		}
		catch(NumberFormatException e) {
			System.out.println("Arquivo no formato invalido");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//cria-se a matriz de adjacencia
		criaMatrizAdjacente ();
			
	}

}
