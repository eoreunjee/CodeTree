import java.util.*;
import java.io.*;




public class Main {

	static int n;
	static int [] guest;
	static int [] ability;
	static int result;
	
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
		if(peopNum<ability[0]) {
			return peopNum;
		}
	    int tp = (peopNum-ability[0])/ability[1];
	    int rest = (peopNum-ability[0])%ability[1];

	    if(rest!=0){
	        tp++;
	    }
	    tp++;

	    return tp;
	}

}
