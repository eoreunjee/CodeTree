import java.util.*;
import java.io.*;




public class Main {

	static int n;
	static int [] guest;
	static int [] ability;
	static long result;
	
	    public static void main(String[] args) throws Exception {
	        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	        
	        n = Integer.parseInt(bf.readLine());
	        guest = new int [n];
	        StringTokenizer st = new StringTokenizer(bf.readLine());
	        for(int i=0; i<n; i++){
	            guest[i] = Integer.parseInt(st.nextToken());
	        }

	        StringTokenizer ss = new StringTokenizer(bf.readLine());
	        ability = new int[2];
	        ability[0]=Integer.parseInt(ss.nextToken());
	        ability[1]=Integer.parseInt(ss.nextToken());

	        result=0;
	        for(int p: guest){
	            result+=check_pp(p);
	        }
	        System.out.println(result);
	    }
	

	static int check_pp(int peopNum){
		if(peopNum<=ability[0]) {
			return 1;
		}
		else {
		    int tp = (peopNum-ability[0])/ability[1]; //(232-8)/6 = 37.333333-> 37
		    //System.out.println("tp="+tp);
		    int rest = (peopNum-ability[0])%ability[1]; //(232-8)/6 = 37.333333-> 37
		    //System.out.println("rest="+rest);
		    if(rest!=0){
		        tp++;
		    }
		    tp++;
		    //System.out.println("result="+tp);
		    return tp;
		}
		
	}

}
