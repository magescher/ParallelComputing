public class StableMarriage extends LLP { 
    final int m,w; 
    final int[][]mprefs; 
    final int[][]wprefs; 
    int[] G; 
    
    public StableMarriage(int[][] mprefs,int[][]wprefs){ 
        super(mprefs.length); 
        this.m=mprefs.length; 
        this.w=wprefs.length; 
        this.mprefs=mprefs; 
        this.wprefs=wprefs; 
        this.G=new int[m]; 
    } 
    
    public boolean forbidden(int j){ 
        int z=mprefs[j][G[j]]; 
        int[]wpref=wprefs[z]; 
        
        for (int i=0;i<m;i++){ 
            if (i==j) continue;  
            if (z!=mprefs[i][G[i]]) continue;
            if (wpref[i]<wpref[j]) return true; 
        } 
        return false; 
    } 
    public void advance(int j) { 
        G[j]++; 
    } 
    public int[]getSolution(){
        int[] assignment= new int[m]; 
        for (int i=0;i<m;i++) 
            assignment[i]=mprefs[i][G[i]]; 
        return assignment; 
    } 
}