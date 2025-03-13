## 정답은 출력되지만 메모리초과, 메모리초과를 해결하니 시간 초과가 발생하였다.
쿼리를 미리 정리해서 불필요한 계산을 줄이거나, 매 쿼리마다 반복적으로 계산하는 부분을 최적화해야 합니다. 예를 들어, 미리 쿼리를 처리하고 결과를 캐싱하는 방법을 고려해야한다.

# 정답 코드
시간 복잡도:
입력 처리: Q개의 쿼리를 처리하는 부분에서 각 쿼리에 대해 O(1). 따라서 입력 처리 시간은 O(Q).

## 쿼리 처리: 
queries 리스트를 순차적으로 처리하고 있습니다. 각 쿼리에 대해 상태 업데이트와 리스트/맵 조회가 일어난다.
각 사람에 대해 주어진 초밥 명령을 관리하면서 각 명령을 처리하는 데 O(1)의 시간이 걸린다.
최악의 경우 모든 사람이 각 쿼리를 하나씩 처리한다고 할 때, O(Q)의 시간 복잡도를 가진다.

## 시간 순서대로 정렬:
queries.sort()에서 시간 복잡도는 O(Q log Q). Q개의 쿼리를 정렬해야 하므로 이 부분에서 가장 큰 시간이 걸린다.

## 전체 시간 복잡도:
전체 시간 복잡도는 O(Q log Q). 입력 처리와 쿼리 처리 부분에서 O(Q)가 소요되지만, 가장 큰 시간이 걸리는 부분은 O(Q log Q)로, 이는 정렬 때문이다.

## 메모리 복잡도:
## 쿼리 리스트 (queries):
queries는 Q개의 쿼리를 저장합니다. 각 쿼리는 Query 객체로 저장되며, 각 Query 객체는 O(1)의 메모리 공간을 차지한다.
따라서 쿼리 리스트의 메모리 복잡도는 O(Q).

## 사람 관련 데이터:
names는 등장한 사람들의 목록을 저장하는 Set이고, p_queries, entry_time, position, exit_time은 사람마다 쿼리와 상태를 저장하는 Map.
각 사람에 대해 1개의 항목이 저장되므로, 사람의 수는 O(N)이고, 각각의 Map과 Set은 O(N)의 메모리를 차지.

## 전체 메모리 복잡도:
전체 메모리 복잡도는 O(Q + N)입니다. 여기서 Q는 쿼리의 수, N은 등장한 사람의 수를 의미한다.


# [코드트리 오마카세 ![Platinum3][p3]](https://www.codetree.ai/training-field/search/problems/codetree-omakase)




```java
import java.util.*;

public class Main {
    public static int L, Q;

    static class Query {
        public int cmd, t, x, n;
        public String name;

        public Query(int cmd, int t, int x, String name, int n) {
            this.cmd = cmd;
            this.t = t;
            this.x = x;
            this.name = name;
            this.n = n;
        }
    }

    // 명령들을 관리합니다.
    public static List<Query> queries = new ArrayList<>();

    // 등장한 사람 목록을 관리합니다.
    public static Set<String> names = new HashSet<>();

    // 각 사람마다 주어진 초밥 명령만을 관리합니다.
    public static Map<String, List<Query>> p_queries = new HashMap<>();

    // 각 사람마다 입장 시간을 관리합니다.
    public static Map<String, Integer> entry_time = new HashMap<>();

    // 각 손님의 위치를 관리합니다.
    public static Map<String, Integer> position = new HashMap<>();

    // 각 사람마다 퇴장 시간을 관리합니다.
    public static Map<String, Integer> exit_time = new HashMap<>();

    public static boolean cmp(Query q1, Query q2) {
        if(q1.t != q2.t)
            return q1.t < q2.t;
        return q1.cmd < q2.cmd;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력:
        L = sc.nextInt();
        Q = sc.nextInt();
        for(int i = 0; i < Q; i++) {
            int cmd = -1;
            int t = -1, x = -1, n = -1;
            String name = "";
            cmd = sc.nextInt();
            if(cmd == 100) {
                t = sc.nextInt();
                x = sc.nextInt();
                name = sc.next();
            }
            else if(cmd == 200) {
                t = sc.nextInt();
                x = sc.nextInt();
                name = sc.next();
                n = sc.nextInt();
            } 
            else {
                t = sc.nextInt();
            }

            queries.add(new Query(cmd, t, x, name, n));

            // 사람별 주어진 초밥 목록을 관리합니다.
            if(cmd == 100)
                p_queries.computeIfAbsent(name, k -> new ArrayList<>()).add(new Query(cmd, t, x, name, n));
            // 손님이 입장한 시간과 위치를 관리합니다.
            else if(cmd == 200) {
                names.add(name);
                entry_time.put(name, t);
                position.put(name, x);
            }
        }

        // 각 사람마다 자신의 이름이 적힌 조합을 언제 먹게 되는지를 계산하여 해당 정보를 기존 Query에 추가합니다. (111번 쿼리)
        for(String name : names) {
            // 해당 사람의 퇴장 시간을 관리합니다.
            // 이는 마지막으로 먹는 초밥 시간 중 가장 늦은 시간이 됩니다.
            exit_time.put(name, 0);

            for(Query q: p_queries.get(name)) {
                // 만약 초밥이 사람이 등장하기 전에 미리 주어진 상황이라면
                int time_to_removed = 0;
                if(q.t < entry_time.get(name)) {
                    // entry_time때의 스시 위치를 구합니다.
                    int t_sushi_x = (q.x + (entry_time.get(name) - q.t)) % L;
                    // 몇 초가 더 지나야 만나는지를 계산합니다.
                    int additionl_time = (position.get(name) - t_sushi_x + L) % L;

                    time_to_removed = entry_time.get(name) + additionl_time;
                }
                // 초밥이 사람이 등장한 이후에 주어졌다면
                else {
                    // 몇 초가 더 지나야 만나는지를 계산합니다.
                    int additionl_time = (position.get(name) - q.x + L) % L;
                    time_to_removed = q.t + additionl_time;
                }

                // 초밥이 사라지는 시간 중 가장 늦은 시간을 업데이트 합니다.
                exit_time.put(name, Math.max(exit_time.get(name), time_to_removed));

                // 초밥이 사라지는 111번 쿼리를 추가합니다.
                queries.add(new Query(111, time_to_removed, -1, name, -1));
            }
        }

        // 사람마다 초밥을 마지막으로 먹은 시간 t를 계산하여 그 사람이 해당 t 때 코드트리 오마카세를 떠났다는 Query를 추가합니다. (222번 쿼리)
        for(String name : names)
            queries.add(new Query(222, exit_time.get(name), -1, name, -1));

        // 전체 Query를 시간순으로 정렬하되 t가 일치한다면 문제 조건상 사진 촬영에 해당하는 300이 가장 늦게 나오도록 cmd 순으로 오름차순 정렬을 합니다.
        // 이후 순서대로 보면서 사람, 초밥 수를 count하다가 300이 나오면 현재 사람, 초밥 수를 출력합니다.
        queries.sort((q1, q2) -> cmp(q1, q2) ? -1 : 1);

        int people_num = 0, sushi_num = 0;
        for(int i = 0; i < queries.size(); i++) {
            if(queries.get(i).cmd == 100) // 초밥 추가
                sushi_num++;
            else if(queries.get(i).cmd == 111) // 초밥 제거
                sushi_num--;
            else if(queries.get(i).cmd == 200) // 사람 추가
                people_num++;
            else if(queries.get(i).cmd == 222) // 사람 제거
                people_num--;
            else // 사진 촬영시 답을 출력하면 됩니다.
                System.out.println(people_num + " " + sushi_num);
        }
    }
}
```

# 내 코드

## 시간 복잡도:

## 입력 처리: 입력 처리에서 Q개의 쿼리를 처. 각 쿼리에 대해 O(1). 따라서 입력 처리 시간은 O(Q).

## 쿼리 처리:
각 쿼리에 대해, 손님의 상태와 초밥을 관리하면서 guests와 rail을 업데이트.
guests와 rail에서 초밥을 찾는 데는 최악의 경우 O(N) 시간. 이는 손님 수와 초밥의 수가 각각 N일 때 발생.
각 쿼리에 대해 초밥을 찾고 상태를 업데이트하는 데 최대 O(N). 따라서 전체 쿼리 처리 시간은 O(Q * N).

## 시간 복잡도:
전체 시간 복잡도는 O(Q * N). Q개의 쿼리를 처리하는 데 각 쿼리에서 최악의 경우 O(N) 시간이 걸리기 때문.

## 메모리 복잡도:
guests, rail, id 등:
guests: 각 손님의 상태를 저장하는 HashMap으로, N명의 손님에 대해 O(N)의 메모리를 차지.
rail: 초밥의 위치를 저장하는 HashMap으로, 초밥의 수에 비례하여 O(N)의 메모리를 차지.
id: 손님의 이름과 ID를 매핑하는 HashMap으로, O(N)의 메모리를 차지.

전체 메모리 복잡도:
전체 메모리 복잡도는 O(N). guests, rail, id는 모두 손님의 수 N에 비례하는 메모리를 차지.

```java
import java.util.*;
import java.io.*;

public class Main {
	
	static int L;
	static int Q;
	static int t;
	static int pivot;
	static int loc;
	static HashMap<String, Integer> id;
	static int idd;
	static HashMap<Integer, ArrayDeque<Integer>> rail;
	static HashMap<Integer,int []> guests;
	static int time=0;
	static int sushi_num;
	static int people=0;
	static ArrayDeque<Integer> bin;
		
	//이름을 id로 바꿔라.
	
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        rail = new HashMap<>();
        guests = new HashMap<>();
        id = new HashMap<>();
        
        for(int q=0; q<Q; q++) {
        	time++;
        	StringTokenizer cmd = new StringTokenizer(bf.readLine());
        	int inc = Integer.parseInt(cmd.nextToken());
        	t = Integer.parseInt(cmd.nextToken());
        	if(time!=t) {
        		if(guests.isEmpty()) {
        			while(time!=t) {
        				time = t;
        			}
        		}
        		else {
            		while(time!=t) {
            			pivot = time%L;
            			bin = new ArrayDeque<>();
            			for(int g : guests.keySet()) {
            				if(guests.get(g)[0]-pivot<0) {
                    			loc = guests.get(g)[0]-pivot+L;
                    			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
                    			for(int sushi: deque) {
                    				if (sushi==g) {
                    					guests.get(g)[1]--;
                    					rail.get(loc).remove(sushi);
                    					if(guests.get(g)[1]==0) {
                    						bin.offer(g);
                    						break;
                    					}
                    				}
                    			}
                    		}
                    		else {
                    			loc = guests.get(g)[0]-pivot;
                    			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
                    			for(int sushi: deque) {
                    				if (sushi==g) {
                    					guests.get(g)[1]--;
                    					rail.get(loc).remove(sushi);
                    					if(guests.get(g)[1]==0) {
                    						bin.offer(g);
                    						break;
                    					}	
                    				}
                    			}
                    		}
            			}
            			while(!bin.isEmpty()) {
                			guests.remove(bin.poll());
                		}
            			time++;
            		}
            	}
        	}
        	
    		if(inc==100) {
        		int x = Integer.parseInt(cmd.nextToken());
        		String name = cmd.nextToken();
        		if(!id.containsKey(name)) {
        			id.put(name, idd);
            		idd++;
        		}
        		
        		
        		pivot = t%L;
        		if(x-pivot<0) {
        			loc = x-pivot+L;
        			rail.putIfAbsent(loc, new ArrayDeque<>());  
        			rail.get(loc).offer(id.get(name));  
        		}
        		else {
        			loc = x-pivot;
        			rail.putIfAbsent(loc, new ArrayDeque<>());
        			rail.get(loc).offer(id.get(name)); 

        		}
        		
        		bin = new ArrayDeque<>();
        		for(int g : guests.keySet()) {
    				if(guests.get(g)[0]-pivot<0) {
            			loc = guests.get(g)[0]-pivot+L;
            			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(int sushi: deque) {
            				if (sushi==g) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
            					if(guests.get(g)[1]==0) {
            						bin.offer(g);
            						break;
            					}
            				}
            			}
            		}
            		else {
            			loc = guests.get(g)[0]-pivot;
            			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(int sushi: deque) {
            				if (sushi==g) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
            					if(guests.get(g)[1]==0) {
            						bin.offer(g);
            						break;
            					}	
            				}
            			}
            		}
    			}
        		while(!bin.isEmpty()) {
        			guests.remove(bin.poll());
        		}
        	}
        	else if(inc==200) {
        		int x = Integer.parseInt(cmd.nextToken());
        		pivot = t%L;
        		String gb = cmd.nextToken();
        		int g = id.get(gb);
        		int n = Integer.parseInt(cmd.nextToken());
        		if(x-pivot<0) {
        			loc = x-pivot+L;
        			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
        			for(int sushi: deque) {
        				if (sushi==g) {
        					n--;
        					rail.get(loc).remove(sushi);
        					if(n==0) {
        						break;
        					}
        				}
        			}
        			if(n>0) {
        				guests.put(g, new int [] {x,n});
        			}
        			}
        		else {
        			loc = x-pivot;
        			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
        			for(Integer sushi: deque) {
        				if (sushi==g) {
        					n--;
        					rail.get(loc).remove(sushi);
        					if(n==0) {
        						break;
        					}
        				}
        			}
        			if(n>0) {
        				guests.put(g, new int [] {x,n});
        			}
        		}
        		bin = new ArrayDeque<>();
        		for(int g2 : guests.keySet()) {
    				if(guests.get(g2)[0]-pivot<0) {
            			loc = guests.get(g2)[0]-pivot+L;
            			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(int sushi: deque) {
            				if (sushi==g2) {
            					guests.get(g2)[1]--;
            					rail.get(loc).remove(sushi);
            					if(guests.get(g2)[1]==0) {
            						bin.offer(g2);
            						break;
            					}
            				}
            			}
            		}
            		else {
            			loc = guests.get(g2)[0]-pivot;
            			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(int sushi: deque) {
            				if (sushi==g2) {
            					guests.get(g2)[1]--;
            					rail.get(loc).remove(sushi);
            					if(guests.get(g2)[1]==0) {
            						bin.offer(g2);
            						break;
            					}	
            				}
            			}
            		}
    			}
        		while(!bin.isEmpty()) {
        			guests.remove(bin.poll());
        		}
        		
        	}
        	else if(inc==300) {
        		pivot = t%L;
        		bin = new ArrayDeque<>();
        		for(int g : guests.keySet()) {
    				if(guests.get(g)[0]-pivot<0) {
            			loc = guests.get(g)[0]-pivot+L;
            			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(int sushi: deque) {
            				if (sushi==g) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
            					if(guests.get(g)[1]==0) {
            						bin.offer(g);
            						break;
            					}
            				}
            			}
            		}
            		else {
            			loc = guests.get(g)[0]-pivot;
            			ArrayDeque<Integer> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(int sushi: deque) {
            				if (sushi==g) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
            					if(guests.get(g)[1]==0) {
            						bin.offer(g);
            						break;
            					}	
            				}
            			}
            		}
    			}
    			while(!bin.isEmpty()) {
        			guests.remove(bin.poll());
        		}
        		sushi_num=0;
        		
        		for(int r: rail.keySet()) {
        			sushi_num+=rail.get(r).size();	
        		}
        		people=0;
        		people+=guests.size();
        		System.out.println(people+" "+sushi_num);
            	
        	}
        }    
    }
}

```

|유형|출처|
|---|---|
|[Sort](https://www.codetree.ai/training-field/search/?tags=Sort), [HashMap](https://www.codetree.ai/training-field/search/?tags=HashMap), [HashSet](https://www.codetree.ai/training-field/search/?tags=HashSet), [Simulation](https://www.codetree.ai/training-field/search/?tags=Simulation)|[일반연습 / 문제은행](https://www.codetree.ai/training-field/home)|








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
