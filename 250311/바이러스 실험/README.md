# **시간 복잡도 분석**

## **1.1 입력 처리 (O(N²))**
- 연구소의 크기(N×N)와 초기 양분, 바이러스 데이터를 입력받음.
- **시간 복잡도: O(N²)** (이중 반복문)

## **1.2 바이러스 성장 및 번식 (O(K × N² log M))**
- `K`년 동안 각 칸에서 바이러스의 성장 및 번식 과정 수행.
- **각 칸에서 바이러스는 우선순위 큐(PriorityQueue)로 관리되며, 삽입/삭제 연산이 log(M) 걸림.**
- 최악의 경우 모든 칸(N²)에 대해 log(M) 연산이 필요하므로 **O(K × N² log M)**

## **1.3 바이러스 번식 (O(N²))**
- 번식 조건을 만족하는 바이러스가 8방향 탐색하며 새로운 바이러스를 생성.
- 최악의 경우 모든 칸에서 번식 발생 시 **O(N²)**

## **1.4 양분 갱신 (O(N²))**
- 죽은 바이러스를 양분으로 변환 후, 추가 양분을 보충.
- **시간 복잡도: O(N²)**

---

## **2. 전체 시간 복잡도**
주어진 `K`년 동안 위 연산을 반복 수행하므로:
\[
O(K × (N² log M + N² + N²)) = O(K × N² log M)
\]

최악의 경우:
- **N ≤ 10, K ≤ 1000, M ≤ 100000** (바이러스 개수)
- **O(1000 × 100 × log 100000) ≈ O(10⁶)**
약 **백만 번 연산** 수준이므로, **1초 이내에 실행 가능**.

## **3. 결론**
### **최악의 시간 복잡도**
**O(K × N² log M) ≈ O(10⁶)**  

**➡ 바이러스 성장 및 번식 과정에서 우선순위 큐를 활용하여 효율적으로 구현 가능!**

## **4. 느낀 점**
이 문제를 분석하면서 **우선순위 큐(PriorityQueue)를 활용한 정렬 기반 접근법**이 얼마나 효과적인지 다시 한 번 확인할 수 있었다. 바이러스의 나이순 정렬이 필요했기 때문에 **Heap을 활용한 정렬이 적절한 선택**이었다.

또한, **바이러스가 죽을 때 양분을 즉시 업데이트했던 것이 초기 오답의 원인**이었다.  
처음에는 바이러스가 죽자마자 해당 칸의 양분을 증가시켰지만,  
이후에 살아 있는 바이러스가 양분을 섭취할 때 영향을 주어 **잘못된 결과가 발생**했다.  

이를 해결하기 위해 **한 사이클이 종료된 후에 한 번에 양분을 갱신하는 방식으로 수정**했더니 정상적으로 정답이 도출되었다.

이 경험을 통해 **연산의 순서가 결과에 미치는 영향을 신중히 고려해야 한다는 점**을 배웠다.  
앞으로는 **구현 순서와 데이터 갱신 타이밍을 더욱 주의 깊게 설계하는 습관**을 들여야겠다.

```java
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
	static int [][] dead;
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
        dead = new int [n][n];
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
        			feed[r][c]+=dead[r][c];
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
    	dead = new int [n][n];
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
	    			dead[row][col]+=(int) (age/2);
	    		}
	    	}
    		virus.replace(key, end);
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
```



# [바이러스 실험 ![Gold3][g3]](https://www.codetree.ai/training-field/search/problems/virus-experiment)

|유형|출처|
|---|---|
|[Simulation](https://www.codetree.ai/training-field/search/?tags=Simulation)|[일반연습 / 문제은행](https://www.codetree.ai/training-field/home)|








[b5]: https://img.shields.io/badge/Bronze_5-%235D3E31.svg
[b4]: https://img.shields.io/badge/Bronze_4-%235D3E31.svg
[b3]: https://img.shields.io/badge/Bronze_3-%235D3E31.svg
[b2]: https://img.shields.io/badge/Bronze_2-%235D3E31.svg
[b1]: https://img.shields.io/badge/Bronze_1-%235D3E31.svg
[s5]: https://img.shields.io/badge/Silver_5-%23394960.svg
[s4]: https://img.shields.io/badge/Silver_4-%23394960.svg
[s3]: https://img.shields.io/badge/Silver_3-%23394960.svg
[s2]: https://img.shields.io/badge/Silver_2-%23394960.svg
[s1]: https://img.shields.io/badge/Silver_1-%23394960.svg
[g5]: https://img.shields.io/badge/Gold_5-%23FFC433.svg
[g4]: https://img.shields.io/badge/Gold_4-%23FFC433.svg
[g3]: https://img.shields.io/badge/Gold_3-%23FFC433.svg
[g2]: https://img.shields.io/badge/Gold_2-%23FFC433.svg
[g1]: https://img.shields.io/badge/Gold_1-%23FFC433.svg
[p5]: https://img.shields.io/badge/Platinum_5-%2376DDD8.svg
[p4]: https://img.shields.io/badge/Platinum_4-%2376DDD8.svg
[p3]: https://img.shields.io/badge/Platinum_3-%2376DDD8.svg
[p2]: https://img.shields.io/badge/Platinum_2-%2376DDD8.svg
[p1]: https://img.shields.io/badge/Platinum_1-%2376DDD8.svg
[passed]: https://img.shields.io/badge/Passed-%23009D27.svg
[failed]: https://img.shields.io/badge/Failed-%23D24D57.svg
[easy]: https://img.shields.io/badge/쉬움-%235cb85c.svg?for-the-badge
[medium]: https://img.shields.io/badge/보통-%23FFC433.svg?for-the-badge
[hard]: https://img.shields.io/badge/어려움-%23D24D57.svg?for-the-badge
