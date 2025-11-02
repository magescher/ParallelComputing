public class Johnson extends LLP{ 
    final int[][]pre; 
    final int[][]w; 
    int[] G; 
    int num; 

    public Johnson(int num, int[][]pre,int[][]w){ 
        super(num); 
        this.pre=pre; 
        this.w=w; 
        G= new int[num]; 
        
        for (int i=0;i<num;i++) G[i]=0; 
    } 
    
    public boolean ensure(int j){ 
        boolean changed= false; 
        for (int i:pre[j]) if (G[j]<G[i]-w[i][j]){ 
            G[j]=G[i]-w[i][j]; 
            changed=true; 
        } 
        return changed; 
    } 
    
    public int[]getSolution(){ 
        return G;
    } 
}