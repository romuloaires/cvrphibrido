public class Clientes {
	
	private int id; 
	private int demanda;
	private int x;
	private int y;
	private boolean noGrupo; 
	
	public Clientes(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.noGrupo = false;
	}
		
	
	  // se o cliente está em um grupo
	 
	public boolean noGrupo() {
		return noGrupo;
	}

	public void setInCluster(boolean noGrupo) {
		this.noGrupo = noGrupo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDemanda() {
		return demanda;
	}

	public void setDemanda(int demanda) {
		this.demanda = demanda;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
	public String toString() {
    	/*return "Cliente [id=" + id + ", demanda=" + demanda + ", x=" + x
    	+ ", y=" + y + "" +
						", noGrupo=" + noGrupo + "" +
    	"]";*/
    	return "" + id;
	}

    //retorna a distancia entre dois clientes
	public double distanciaEuclediana (Clientes cliente2) {
    	double distancia;
    	distancia = Math.sqrt((this.x - cliente2.x)*(this.x - cliente2.x) + (this.y - cliente2.y) * (this.y - cliente2.y));
		return distancia;
	}
    
	//calcula a distância euclideana entre cliente e (x,y)
	 
    public double distanciaEuclediana (double x, double y) {
    	double distancia;
    	distancia = Math.sqrt((this.x - x)*(this.x - x) + (this.y - y) * (this.y - y));
		return distancia;
	}
	
}
