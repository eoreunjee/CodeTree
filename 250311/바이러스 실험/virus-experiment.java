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
        
        for(int i=0; i<m; i++) {
        	StringTokenizer sv = new StringTokenizer(bf.readLine());
        	int row = Integer.parseInt(sv.nextToken())-1;
        	int col = Integer.parseInt(sv.nextToken())-1;
        	
        	List<Integer> info = Arrays.asList(row, col);
        	
        	if(virus.containsKey(info)) {
        		virus.get(info).offer(Integer.parseInt(sv.nextToken())); // 나이 저장
        	} else { 
        		PriorityQueue<Integer> contains = new PriorityQueue<>();
        		contains.offer(Integer.parseInt(sv.nextToken()));
        		virus.put(info, contains);
        	}
        }

        for(int i=0; i<k; i++) {
        	aging();
        	while(!q.isEmpty()) {
        		reproduction(q.poll());
        	}
        	for(int r=0; r<n; r++) {
        		for(int c=0; c<n; c++) {
        			feed[r][c] += add[r][c];
        		}
        	}
        }
        
        int total = 0;
        for(List<Integer> key : virus.keySet()) {
        	total += virus.get(key).size();
        }
        System.out.println(total);
	}
    
    // 바이러스 성장 및 소멸
    static void aging() {
    	List<List<Integer>> toReproduce = new ArrayList<>(); // 번식할 바이러스 저장
    	
    	HashMap<List<Integer>, PriorityQueue<Integer>> newVirus = new HashMap<>(); // 새롭게 업데이트할 바이러스 정보 저장

    	for(List<Integer> key : virus.keySet()) {
    		int row = key.get(0);
    		int col = key.get(1);
    		PriorityQueue<Integer> start = virus.get(key);
    		
    		PriorityQueue<Integer> end = new PriorityQueue<>();
    		
    		while(!start.isEmpty()) {
	    		int age = start.poll();
	    		if(feed[row][col] >= age) { // 양분 충분하면 성장
	    			feed[row][col] -= age;
	    			age++;
	    			end.offer(age);
	    			if(age % 5 == 0) { 
	        			toReproduce.add(new ArrayList<>(key));  // ✅ 새로운 리스트로 추가
	        		}
	    		} else { // 소멸
	    			feed[row][col] += age / 2;
	    		}
	    	}
    		
    		if (!end.isEmpty()) {
    			newVirus.put(new ArrayList<>(key), end); // ✅ 새로운 키로 저장
    		}
    	}

    	virus = newVirus; // 업데이트된 바이러스 정보 적용
    	q.addAll(toReproduce); // 번식할 바이러스 추가
    }
    
    // 번식
    static void reproduction(List<Integer> key) {
    	int row = key.get(0);
    	int col = key.get(1);
    	for(int i=0; i<8; i++) { 
			int nr = row + dx[i];
			int nc = col + dy[i];
			if(nr >= 0 && nr < n && nc >= 0 && nc < n) {  
				List<Integer> loc = Arrays.asList(nr, nc);
				if(virus.containsKey(loc)) {
	        		virus.get(loc).offer(1);  
	        	} else {  
	        		PriorityQueue<Integer> contains = new PriorityQueue<>();
	        		contains.offer(1);
	        		virus.put(new ArrayList<>(loc), contains); // ✅ 새로운 키로 저장
	        	}
			}
		}
    }
}
