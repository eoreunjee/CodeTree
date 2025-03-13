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
        				time++;
        			}
        		}
        		if(!guests.isEmpty()) {
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
