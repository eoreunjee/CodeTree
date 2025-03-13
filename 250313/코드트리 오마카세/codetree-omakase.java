import java.util.*;
import java.io.*;

public class Main {
	
	static int L;
	static int Q;
	static int t;
	static int pivot;
	static int loc;
	static HashMap<Integer, ArrayDeque<String>> rail;
	static HashMap<String,int []> guests;
	static int time=0;
	static int sushi_num;
	static int people=0;
	static ArrayDeque<String> bin;
		
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        rail = new HashMap<>();
        guests = new HashMap<>();
        
        for(int i=0; i<L; i++) {
        	rail.put(i, new ArrayDeque<String>());
        }
        
        //T와 x의 관계 계산. x=0~L-1, t가 커질수록 x는 1씩 커짐. x=t를 L로 나눈 나머지.
        //x 0 1 2 3 4
        //8초 뒤 2 3 4 0 1 -> 0은 t(8)을 L(5)로 나눈 나머지 자리 3에 있다.
        //x는 3이므로, 0기준의 위치-> 0기준의 위치를 pivot으로 두고, pivot=3, pivot+1=4=x, pivot+2=5=0=x, pivot+3=6=1=x, pivot+4=7=2-> pivot + k가 L이상이면 pivot+k-L=x위치
        //x-pivot= 0, 1, -3, -2, -1-> 2-5, 3-5, 4-5 
        for(int q=0; q<Q; q++) {
//        	System.out.println("현재 시간= "+time+" 주어진 시각= "+t);
        	time++;
        	StringTokenizer cmd = new StringTokenizer(bf.readLine());
        	int inc = Integer.parseInt(cmd.nextToken());
        	t = Integer.parseInt(cmd.nextToken());
        	if(time!=t) {
//        		System.out.println("현재 시간= "+time+" 주어진 시각= "+t);
//        		for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//                    System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//                }
        		if(guests.isEmpty()) {
        			while(time!=t) {
        				time++;
        			}
        		}
        		if(!guests.isEmpty()) {////////////////////////////////////////////////////////////////////////
        			//해쉬맵 손님이름=[자리, 남은 초밥 개수]HashMap<String,int [자리, 남은 초밥개수]> guests;
        			//초밥 레일 HashMap<Integer, ArrayDeque<String>> rail;
            		while(time!=t) {/////////////////////////////////////////////////////////////////////////////
//            			System.out.println("while문 내 현재 시간= "+time+" 주어진 시각= "+t);
            			pivot = time%L;
            			bin = new ArrayDeque<>();
            			for(String g : guests.keySet()) {
            				if(guests.get(g)[0]-pivot<0) {
                    			loc = guests.get(g)[0]-pivot+L;
                    			ArrayDeque<String> deque = rail.getOrDefault(loc, new ArrayDeque<>());
                    			for(String sushi: deque) {
                    				if (sushi.equals(g)) {
                    					guests.get(g)[1]--;
                    					rail.get(loc).remove(sushi);
//                    					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//                    			            System.out.println("isn't it change?" +entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//                    			        }
                    					if(guests.get(g)[1]==0) {
                    						bin.offer(g);
                    						break;
                    					}
                    				}
                    			}
                    		}
                    		else {
                    			loc = guests.get(g)[0]-pivot;
                    			for(String sushi: rail.get(loc)) {
                    				if (sushi.equals(g)) {
                    					guests.get(g)[1]--;
                    					rail.get(loc).remove(sushi);
//                    					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//                    			            System.out.println("isn't it change?" + entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//                    			        }
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
//        		System.out.println("cmd= "+inc);
        		int x = Integer.parseInt(cmd.nextToken());
        		String name = cmd.nextToken();
        		pivot = t%L;
//        		System.out.println("pivot= "+pivot);
        		if(x-pivot<0) {
        			loc = x-pivot+L;
//        			System.out.println("loc= "+ loc);
        			rail.get(loc).offer(name);
//        			System.out.println("rail= "+rail);
        		}
        		else {
        			loc = x-pivot;
//        			System.out.println("loc= "+ loc);
        			rail.get(loc).offer(name);
//        			System.out.println("rail= "+rail);
        		}
        		bin = new ArrayDeque<>();
        		for(String g : guests.keySet()) {
    				if(guests.get(g)[0]-pivot<0) {
            			loc = guests.get(g)[0]-pivot+L;
            			ArrayDeque<String> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(String sushi: deque) {
            				if (sushi.equals(g)) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
//            					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//            			            System.out.println("isn't it change?" +entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//            			        }
            					if(guests.get(g)[1]==0) {
            						bin.offer(g);
            						break;
            					}
            				}
            			}
            		}
            		else {
            			loc = guests.get(g)[0]-pivot;
            			for(String sushi: rail.get(loc)) {
            				if (sushi.equals(g)) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
//            					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//            			            System.out.println("isn't it change?" + entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//            			        }
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
        	else if(inc==200) {//해쉬맵 손님이름=[자리, 남은 초밥 개수] HashMap<String, int []> guests;
//        		System.out.println("cmd= "+inc);
        		int x = Integer.parseInt(cmd.nextToken());
        		pivot = t%L;
        		String g = cmd.nextToken();
        		int n = Integer.parseInt(cmd.nextToken());
        		if(x-pivot<0) {
        			loc = x-pivot+L;
        			for(String sushi: rail.get(loc)) {
        				if (sushi.equals(g)) {
//        					System.out.println(g+" is gone!");
        					n--;
        					rail.get(loc).remove(sushi);
//        					System.out.println("rail= "+rail);
        					if(n==0) {
        						break;
        					}
        				}
        			}
        			if(n>0) {
        				guests.put(g, new int [] {x,n});
//        				for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//        		            System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//        		        }
        			}
        			}
        		else {
        			loc = x-pivot;
        			for(String sushi: rail.get(loc)) {
        				if (sushi.equals(g)) {
//        					System.out.println(g+" is gone!");
        					n--;
        					rail.get(loc).remove(sushi);
//        					System.out.println("rail= "+rail);
        					if(n==0) {
        						break;
        					}
        				}
        			}
        			if(n>0) {
        				guests.put(g, new int [] {x,n});
//        				for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//        		            System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//        		        }
        			}
        		}
        		bin = new ArrayDeque<>();
        		for(String g2 : guests.keySet()) {
    				if(guests.get(g2)[0]-pivot<0) {
            			loc = guests.get(g2)[0]-pivot+L;
            			ArrayDeque<String> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(String sushi: deque) {
            				if (sushi.equals(g2)) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
//            					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//            			            System.out.println("isn't it change?" +entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//            			        }
            					if(guests.get(g2)[1]==0) {
            						bin.offer(g2);
            						break;
            					}
            				}
            			}
            		}
            		else {
            			loc = guests.get(g2)[0]-pivot;
            			for(String sushi: rail.get(loc)) {
            				if (sushi.equals(g2)) {
            					guests.get(g2)[1]--;
            					rail.get(loc).remove(sushi);
//            					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//            			            System.out.println("isn't it change?" + entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//            			        }
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
//        		System.out.println("사진 촬영");
        		bin = new ArrayDeque<>();
        		for(String g : guests.keySet()) {
//        			System.out.println("who? "+g);
    				if(guests.get(g)[0]-pivot<0) {
//    					System.out.println("This one?");
            			loc = guests.get(g)[0]-pivot+L;
            			ArrayDeque<String> deque = rail.getOrDefault(loc, new ArrayDeque<>());
            			for(String sushi: deque) {
            				if (sushi.equals(g)) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
//            					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//            			            System.out.println("isn't it change?" +entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//            			        }
            					if(guests.get(g)[1]==0) {
            						bin.offer(g);
            						break;
            					}
            				}
            			}
            		}
            		else {
//            			System.out.println("Or this one?");
            			loc = guests.get(g)[0]-pivot;
//            			System.out.println("loc = "+loc);
            			for(String sushi: rail.get(loc)) {
            				if (sushi.equals(g)) {
            					guests.get(g)[1]--;
            					rail.get(loc).remove(sushi);
//            					for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//            						System.out.println("isn't it change?" + entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//            			        }
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
        		
        		for(int r=0; r<L; r++) {
//        			System.out.println("rail= "+rail);
        			if(rail.get(r).size()>0) {
        				sushi_num+=rail.get(r).size();
        			}
        			else {
        				continue;
        			}
        		}
        		people=0;
//        		for (Map.Entry<String, int[]> entry : guests.entrySet()) {
//                    System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
//                }
        		people+=guests.size();
        		System.out.println(people+" "+sushi_num);
            	
        	}
        }    
    }
}
