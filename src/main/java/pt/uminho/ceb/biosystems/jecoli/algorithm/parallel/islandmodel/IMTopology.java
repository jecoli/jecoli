package pt.uminho.ceb.biosystems.jecoli.algorithm.parallel.islandmodel;

import pt.uminho.ceb.biosystems.jecoli.algorithm.components.utilities.libgraph.Graph;

public class IMTopology implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	int type;
	
	Graph gr; 
	
	
	//Topologies 
	 public static final int RING = 1;
	 public static final int STAR = 2;
	 public static final int MESH=3;
	 public static final int BIRING=4;

	
	public IMTopology(int numpops, int type) throws Exception
	{
		this.type = type;
		this.gr = new Graph(numpops);
		createTopology();
	}
	
	//Define the connections
	private void createTopology() throws Exception
	{
		switch (type)
		{
			case RING:
				for(int i=0; i < gr.getDimension(); i++)
				{
					gr.setConnection(i, (i+1)%gr.getDimension(), true);
				}
				break;
			case STAR:
				for(int i=0;i<gr.getDimension()-1;i++)
				{
					gr.setConnection(0, i+1, true);
					gr.setConnection(i+1, 0, true);
				}
				break;
			case MESH:
				for(int i=0;i<gr.getDimension();i++)
				{
					for(int j=0;j<gr.getDimension();j++)
					{
						if(i!=j)
						{
							gr.setConnection(i, j,true);
						}
					}
				}
				break;
			case BIRING:
				for(int i=0;i<gr.getDimension();i++)
				{
					gr.setConnection(i, (i+1)%gr.getDimension(), true);
					gr.setConnection((i+1)%gr.getDimension(), i, true);
				}
				break;
			default:
				throw new Exception("Topology not implemented");
		}
	}

	public int getType() {
		return type;
	}
	
	public String getStringType(){
		String stype;
		switch (type)
		{
			case RING:
				stype="Ring";
				break;
			case STAR:
				stype="Star";
				break;
			case MESH:
				stype="Mesh";
				break;
			case BIRING:
				stype="Bidirectional Ring";
				break;
			default:
				stype="None";
		}
		return stype;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Graph getGraph() {
		return gr;
	}

	public void setGraph(Graph gr) {
		this.gr = gr;
	}
}
