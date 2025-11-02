public class FastComponent extends LLP { 
    final int[][]adj; 
    int[] G; 
    int num; 
    boolean debug=true; 
    
    public FastComponent(int num, int[][]adj){ 
        super(num); 
        this.adj=adj; 
        this.G=new int[num]; 
        for (int i=0;i<num;i++)G[i]=i; 
    } 
    
    public boolean ensure(int j){ 
        boolean changed= false; 
        
        if (G[j]!=G[G[j]]){G[j]=G[G[j]];changed=true; } 
        else 
            for (int i:adj[j]) 
                if (G[j]<G[i]){G[j]=G[i];changed=true; } 
        return changed; 
    } 
    
    public int[]getSolution(){ return G;} 

}
