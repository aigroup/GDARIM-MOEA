package logica.ag;
 
import java.util.*;

public class GrayCode
{	
	public BitSet result;
	
	public BitSet getResult() {
		return result;
	}
	public void setResult(BitSet result) {
		this.result = result;
	}
	public GrayCode(int i) {
		result = IntToGray(i);
	}
	public BitSet setGrayCode(String s){	
		result.clear();
 		StringTokenizer st=new StringTokenizer(s," ,{}");
 		while (st.hasMoreElements()){
       		result.set(Integer.parseInt(st.nextToken()));
 		}
 		return result;	
	}
	public GrayCode(BitSet e) {
		result = e;
	}

	public BitSet IntToGray(int n) {
		result = new BitSet();		
        for( int i=31; i >= 0; i-- ) {
            if( ( (1 << i) & n ) != 0 )
                result.set( i );
            else
                result.clear( i );
        }
		return result;
	}

/*
	public BitSet IntToGray(int n)
	{		
		BitSet bitSet = new BitSet(n);
		result = new BitSet();

		for(int i = bitSet.length() - 1; i >= 0; i--)
			result.set(i, bitSet.get(i+1) ^ bitSet.get(i)); 
		
		return result;
	}
*/
/*	public EBitSet IntToGray(int n)
	{		
		EBitSet bitSet = new EBitSet(n);
		result = new EBitSet();

		for(int i = bitSet.length() - 1; i >= 0; i--)
			result.set(i, bitSet.get(i)); 
		
		return result;
	}
*/
/*	public int GrayToInt(BitSet bitSet)
	{		
		result = new BitSet();

		for(int i = bitSet.length() - 2; i >= 0; i--)
			result.set(i, result.get(i+1) ^ bitSet.get(i)); 
	
		return getNumber(result);
	}
*/

    public String toStringExpandido() {
        String bbits = new String();
        for( int j=0; j < result.size(); j++ )
            bbits += ( result.get( j ) ? "1" : "0" );
        return "Patron de bits: "+bbits;
    }
    
	public int getNumber(BitSet b)
	{		
		int number = 0;
	    
		for (int i=0; i < b.length(); i++)
			if (b.get(i))
				number += Math.pow((double)2, (double)i);
		
		return number;
	}
	
	public int getNumber()
	{		
		int number = 0;
	    
		for (int i=0; i < result.length(); i++)
			if (result.get(i))
				number += Math.pow((double)2, (double)i);
		
		return number;
	}

	public int GrayToInt()
	{		
		for(int i = result.length() - 2; i >= 0; i--)
			result.set(i, result.get(i));  
		
		return getNumber();
	}


	public String toString(){
		return ""+getNumber(result);
	}

	public static void main(String[] args){
		GrayCode gc = new GrayCode(33);
		System.out.println(gc.getResult());
		//		gc.setGrayCode(gc.IntToGray(1354).toString());
		System.out.println("codigo gray de "+gc.GrayToInt()+" es: "+gc);
//		System.out.println("codigo gray2 de 1 "+gc.GrayToInt(gc.setGrayCode(gc.IntToGray(10).toString())));
	}
}

