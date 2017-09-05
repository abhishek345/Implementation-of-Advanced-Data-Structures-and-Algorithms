
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class PrintingProblem {
	public static int[][] extras;
	public static int[][] lc;
	public static int[] c;
	public static int[] p;
	public static String[] tokenString;
	//public static List<String> finalLines = new ArrayList<String>();
	public static Map<Integer, List> mapLinesString = new TreeMap<Integer,List>();
	
	public static void main(String[] args) throws IOException {
		String temp = "";
		int M=80;
		if(args.length==2) M=Integer.parseInt(args[1]); 
		//System.out.println("Arg 1: "+args[0]+" Arg2: "+args[1]);
		
		Scanner f = new Scanner(new File(System.getProperty("user.dir")+"\\"+args[0])).useDelimiter(",\\s*");
		List<String> temps = new ArrayList<String>();
		while (f.hasNextLine()) {
			temp = f.nextLine();
			String tok[]=temp.split(" ");
			for(int i=0;i<tok.length;i++)
			{
				if(!tok[i].equals("")){
					temps.add(tok[i]);
				}
			}
			
		}
		f.close();
		String[] tempsArray = temps.toArray(new String[0]);
		int size[]=new int[tempsArray.length];
		for(int i=0;i<size.length;i++){
			size[i]=tempsArray[i].length();
		}
		
		PrintingProblem pb=new PrintingProblem();
		pb.tokenString=new String[tempsArray.length];
		for(int i=0;i<tempsArray.length;i++){
			//System.out.println("String : "+ tempsArray[i]+"\tSize : "+size[i]);
			
			pb.tokenString[i]=tempsArray[i];
		}
		pb.printNeatly(size,tempsArray.length,M);
		pb.printAll(M);
	}
	public static void printNeatly(int[] l,int n,int M){
		
		extras=new int[n][n];
		lc=new int[n][n];
		c = new int[n];
		p = new int[n];
		for(int i=0;i<n;i++){
			p[i]=10000;
		}
		for(int i=0;i<n;i++){
			extras[i][i]=M-l[i];
			for(int j=i+1;j<n;j++){
				extras[i][j]=extras[i][j-1]-l[j]-1;
			}
		}
		
		for(int i=0;i<n;i++){
			for(int j=i;j<n;j++){
				if(extras[i][j]<0){
					lc[i][j]=10000;
				}
				else if(j==n-1&&extras[i][j]>=0){
					lc[i][j]=0;
				}
				else{
					lc[i][j]=(int)Math.pow(extras[i][j], 3);
				}
			}
		}
		
		for(int j=0;j<n;j++){
			c[j]=10000;
			for(int i=0;i<j;i++){
				if(i==0){
					if(lc[i][j]<c[j]){
						c[j]=lc[i][j];
						p[j]=i;
					}
				}
				else{
					if(c[i-1]+lc[i][j]<c[j]){
						c[j]=c[i-1]+lc[i][j];
						p[j]=i;
					}
				}
			}
		}
		/*for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.println();
			}
		}*/
		lines(n-1);
	}
	
	public static int lines(int j){
		int i=p[j];
		int k;
		if(i==0){
			k=0;
		}
		else{
			k=lines(i-1)+1;
		}
		print(k,i,j);
		return k;
	}
	
	public static void print(int k, int i, int j){
		List<String> lines=new ArrayList<String>();
		for(int t=i;t<=j;t++){
			lines.add(tokenString[t]);
			//System.out.println(tokenString[t]);
		}
			
		mapLinesString.put(k, lines);
	}
	
	public static void printAll(int M){
		String l93="";
		if(M==93){
			System.out.println(c[c.length-2]);
		}else
		System.out.println(c[c.length-1]);
		System.out.println();
		Iterator it = mapLinesString.entrySet().iterator();
		int lineCount=mapLinesString.entrySet().size();
		boolean lastLine=false;
	    while (it.hasNext()) {
	    	lineCount--;
	        Map.Entry pair = (Map.Entry)it.next();
	        if(it.hasNext()){
        		lastLine=false;
        	}else{
        		lastLine=true;
        	}
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        List<String> s=new ArrayList<String>();
	        s=(List<String>) pair.getValue();
	        Iterator iter = s.iterator();
	        int sp=0;
	        String stng="";
	        
	        while(iter.hasNext()){
	        	if(sp==0){
	        		sp=1;
	        	}
	        	else{
	        		stng=stng+" ";
	        	}
	        	stng=stng+iter.next();
	        	
	        }
	        if(lastLine!=true&&!(M==93&&lineCount==1)){
	        	String[] stng2=stng.split(" ");
		        Random r=new Random();
		        for(int i=M-stng.length()-1;i>=0;i--){
		        	int spa=1+r.nextInt(stng2.length-2);
		        	stng2[spa]="+"+stng2[spa];
		        }
		        StringBuilder strBuilder = new StringBuilder();
		        for (int i = 0; i < stng2.length; i++) {
		        	if(i==stng2.length-1){
		        		strBuilder.append(stng2[i]);
		        	}else
		           strBuilder.append(stng2[i]+" ");
		        }
		        stng = strBuilder.toString();
		        
		        //System.out.println(stng.replaceAll(" ", "+"));
		        System.out.println(stng);
		        //System.out.println(String.format("%"+M+"s", stng).replaceAll(" ", "#"));
	        }else if(M==93&&(lineCount==1||lineCount==0)){
		        l93=l93+stng+" ";
		    }else{
	        	System.out.println(stng);
	        }
	        
	    }
	    if(M==93&&l93.length()>M){
	    	String sp[]=l93.split(" ");
	    	l93="";
	    	for(int i=0;i<sp.length-1;i++){
	    		l93=l93+" "+sp[i];
	    	}
	    	l93=l93.trim();
	    	System.out.println(l93);
	    	System.out.println(sp[sp.length-1]);
	    }else{
	    	
	    }
		
	}
}
