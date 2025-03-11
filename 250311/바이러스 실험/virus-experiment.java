import java.util.*;
import java.io.*;

public class Main {
		
	static int [] dx = {-1,1,0,0,-1,1,-1,1};
	static int [] dy = {0,0,-1,1,-1,1,1,-1};
	static int n;
	static int m;
	static int k;
	static int [][] feed; //양분 맵
	static int [][] add;
	static HashMap<List<Integer>, PriorityQueue<Integer>> virus; //행, 열, 그 칸에 들어있는 바이러스들
	static ArrayDeque<List<Integer>> q;
	
	
	//번식은 사이클에 속하지 않는다. 한 사이클 내에서 같은 칸에 나이가 어린 바이러스부터 양분섭취 후 나이가 많은 애가 섭취. 섭취 후 바로 다음 사이클이 되면서 양분 증가
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(bf.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        feed = new int [n][n];
        add = new int [n][n];
        virus = new HashMap<>();
        q = new ArrayDeque<>();
        
        for(int i=0; i<n; i++) {
        	StringTokenizer ss = new StringTokenizer(bf.readLine());
        	for(int j=0; j<n; j++) {
        		feed[i][j]=5;
        		add[i][j]=Integer.parseInt(ss.nextToken());
        	}
        }
        //System.out.println(Arrays.deepToString(feed));
        
        for(int i=0; i<m; i++) {
        	StringTokenizer sv = new StringTokenizer(bf.readLine());
        	int row = Integer.parseInt(sv.nextToken())-1;
        	int col = Integer.parseInt(sv.nextToken())-1;
        	
        	List<Integer> info = Arrays.asList(row, col);
        	
        	if(virus.keySet().contains(info)) {
        		virus.get(info).offer(Integer.parseInt(sv.nextToken())); //나이 저장
        		
        	}
        	else { //virus = {[r,c]=[속해있는 마릿수, age1, age2...], ...}
        		PriorityQueue<Integer> contains = new PriorityQueue<>();
        		contains.offer(Integer.parseInt(sv.nextToken()));
        		virus.put(info, contains);
        	}
        	//System.out.println(virus);
        	
        	
        }
        for(int i=0; i<k; i++) {
        	aging();
        	//한 사이클에서 aging 끝나고 마지막에 번식하자.
        	while(!q.isEmpty()) {
        		reproduction(q.poll());
        	}
        	for(int r=0; r<n; r++) {
        		for(int c=0; c<n; c++) {
        			feed[r][c]+=add[r][c];
        		}
        	}
        }
        int total=0;
        for(List<Integer> key : virus.keySet()) {
        	//System.out.println(virus);
        	int temp = virus.get(key).size();
        	//System.out.println("temp= "+temp);
        	total+=virus.get(key).size();
        }
        //System.out.println(Arrays.deepToString(feed));
        System.out.println(total);
	}
    
    //사이클 따라 바이러스 먹이고, 나이 올리고, 소멸하고, 번식하기 위해 바로 접근하기 위해서는 해쉬맵?
    static void aging() {
    	for(List<Integer> key: virus.keySet()) {
    		PriorityQueue<Integer> end = new PriorityQueue<>();
    		int row = key.get(0);
    		int col = key.get(1);
    		PriorityQueue<Integer> start = virus.get(key);
    		while(!start.isEmpty()) {
	    		int age = start.poll();
	    		if(feed[row][col]>=age) { //양분이 충분해서 나이 증가
	    			feed[row][col]-=age;
	    			age++;
	    			end.offer(age);
	    			if(age%5==0) { //번식해야되는 개체라면, 
	        			q.offer(key);
	        		}
	    		}
	    		else { //소멸
	    			feed[row][col]+=(int) (age/2);
	    		}
	        	virus.replace(key, end);
	    	}
    	}
    	//System.out.println(Arrays.deepToString(feed));
    	//System.out.println(virus);
    }
    
    static void reproduction(List<Integer> key) {/////////////번식 따로 구현
    	int row = key.get(0);
    	int col = key.get(1);
    	for(int i=0; i<8; i++) { //8방탐색
			int nr = row+dx[i];
			int nc = col+dy[i];
			if(nr>=0 && nr<n && nc>=0 && nc<n) { //나이가 1인 바이러스 생성
				List<Integer> loc = Arrays.asList(nr, nc);
				if(virus.keySet().contains(loc)) {
	        		virus.get(loc).offer(1); //나이 저장
	        		
	        	}
	        	else { 
	        		PriorityQueue<Integer> contains = new PriorityQueue<>();
	        		contains.offer(1);
	        		virus.put(loc, contains);
	        	}
			}
		}
    	//System.out.println(virus);
    }
    
    

}
