import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

	//Construção Gulosa.
		public class TSPNearestNeighbour {
			Solucao solucao =  new Solucao();
			private ArrayList <Rota> rota = new ArrayList <Rota>(); 
			 Integer a;
			 Rota b;
			 Collection<? extends Solucao> c;
			double custo=0;
			 
			private int numberOfNodes;
			private Stack<Integer> stack;
			LinkedList<Clientes> x;
			
			public TSPNearestNeighbour() {
				stack = new Stack<Integer>();
			}

			public double tsp(double adjacencyMatrix[][]) {
				numberOfNodes = adjacencyMatrix[1].length - 1;
				int[] visited = new int[numberOfNodes + 1];
				visited[1] = 1;
				stack.push(1);
				int element, dst = 0, i;
				double min = Integer.MAX_VALUE;
				boolean minFlag = false;
			//	System.out.print(1 + "\n");

				while (!stack.isEmpty()) {
					element = stack.peek();
					i = 1;
					min = Integer.MAX_VALUE;
					while (i <= numberOfNodes) {
						if (adjacencyMatrix[element][i] > 1 && visited[i] == 0) {
							if (min > adjacencyMatrix[element][i]) {
								min = adjacencyMatrix[element][i];
								dst = i;
								
								minFlag = true;
							}
						}
						i++;
					}
					if (minFlag) {
						visited[dst] = 1;
						stack.push(dst);
						custo= custo + dst;						
						solucao.setCustoTotal(dst);						
						minFlag = false;
						continue;
					}
					 a= stack.pop();
				
				}
				
				
				//System.out.println("custonearest:"+ custo);
				
				return custo;
				
				}
			
		}

