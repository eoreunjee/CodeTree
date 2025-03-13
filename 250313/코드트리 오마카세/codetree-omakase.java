import java.util.*;
import java.io.*;

public class Main {

    static int L;
    static int Q;
    static int t;
    static int pivot;
    static int loc;
    static HashMap<Integer, LinkedList<String>> rail;
    static HashMap<String, int[]> guests;
    static int time = 0;
    static int sushi_num;
    static int people = 0;
    static LinkedList<String> bin;

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        rail = new HashMap<>();
        guests = new HashMap<>();
        bin = new LinkedList<>(); // 한번만 초기화

        for (int q = 0; q < Q; q++) {
            time++;
            StringTokenizer cmd = new StringTokenizer(bf.readLine());
            int inc = Integer.parseInt(cmd.nextToken());
            t = Integer.parseInt(cmd.nextToken());

            if (time != t) {
                if (guests.isEmpty()) {
                    while (time != t) {
                        time++;
                    }
                }
                if (!guests.isEmpty()) {
                    while (time != t) {
                        pivot = time % L;
                        bin.clear(); // 매번 초기화가 아닌 한 번만 클리어
                        for (String g : guests.keySet()) {
                            int[] guestState = guests.get(g);
                            int guestLoc = guestState[0];
                            if (guestLoc - pivot < 0) {
                                loc = guestLoc - pivot + L;
                            } else {
                                loc = guestLoc - pivot;
                            }

                            LinkedList<String> deque = rail.getOrDefault(loc, new LinkedList<>());
                            for (String sushi : deque) {
                                if (sushi.equals(g)) {
                                    guestState[1]--;
                                    deque.remove(sushi);
                                    if (guestState[1] == 0) {
                                        bin.offer(g);
                                        break;
                                    }
                                }
                            }
                        }

                        while (!bin.isEmpty()) {
                            guests.remove(bin.poll());
                        }
                        time++;
                    }
                }
            }

            if (inc == 100) {
                int x = Integer.parseInt(cmd.nextToken());
                String name = cmd.nextToken();
                pivot = t % L;
                if (x - pivot < 0) {
                    loc = x - pivot + L;
                } else {
                    loc = x - pivot;
                }
                rail.putIfAbsent(loc, new LinkedList<>());
                rail.get(loc).offer(name);

                bin.clear();
                for (String g : guests.keySet()) {
                    int[] guestState = guests.get(g);
                    int guestLoc = guestState[0];
                    if (guestLoc - pivot < 0) {
                        loc = guestLoc - pivot + L;
                    } else {
                        loc = guestLoc - pivot;
                    }

                    LinkedList<String> deque = rail.getOrDefault(loc, new LinkedList<>());
                    for (String sushi : deque) {
                        if (sushi.equals(g)) {
                            guestState[1]--;
                            deque.remove(sushi);
                            if (guestState[1] == 0) {
                                bin.offer(g);
                                break;
                            }
                        }
                    }
                }

                while (!bin.isEmpty()) {
                    guests.remove(bin.poll());
                }
            } else if (inc == 200) {
                int x = Integer.parseInt(cmd.nextToken());
                pivot = t % L;
                String g = cmd.nextToken();
                int n = Integer.parseInt(cmd.nextToken());
                if (x - pivot < 0) {
                    loc = x - pivot + L;
                } else {
                    loc = x - pivot;
                }

                LinkedList<String> deque = rail.getOrDefault(loc, new LinkedList<>());
                for (String sushi : deque) {
                    if (sushi.equals(g)) {
                        n--;
                        deque.remove(sushi);
                        if (n == 0) {
                            break;
                        }
                    }
                }

                if (n > 0) {
                    guests.put(g, new int[]{x, n});
                }

                bin.clear();
                for (String g2 : guests.keySet()) {
                    int[] guestState = guests.get(g2);
                    int guestLoc = guestState[0];
                    if (guestLoc - pivot < 0) {
                        loc = guestLoc - pivot + L;
                    } else {
                        loc = guestLoc - pivot;
                    }

                    LinkedList<String> sushiDeque = rail.getOrDefault(loc, new LinkedList<>());
                    for (String sushi : sushiDeque) {
                        if (sushi.equals(g2)) {
                            guestState[1]--;
                            sushiDeque.remove(sushi);
                            if (guestState[1] == 0) {
                                bin.offer(g2);
                                break;
                            }
                        }
                    }
                }

                while (!bin.isEmpty()) {
                    guests.remove(bin.poll());
                }
            } else if (inc == 300) {
                pivot = t % L;
                bin.clear();
                for (String g : guests.keySet()) {
                    int[] guestState = guests.get(g);
                    int guestLoc = guestState[0];
                    if (guestLoc - pivot < 0) {
                        loc = guestLoc - pivot + L;
                    } else {
                        loc = guestLoc - pivot;
                    }

                    LinkedList<String> deque = rail.getOrDefault(loc, new LinkedList<>());
                    for (String sushi : deque) {
                        if (sushi.equals(g)) {
                            guestState[1]--;
                            deque.remove(sushi);
                            if (guestState[1] == 0) {
                                bin.offer(g);
                                break;
                            }
                        }
                    }
                }

                while (!bin.isEmpty()) {
                    guests.remove(bin.poll());
                }

                sushi_num = 0;
                for (LinkedList<String> deque : rail.values()) {
                    sushi_num += deque.size();
                }

                people = guests.size();
                System.out.println(people + " " + sushi_num);
            }
        }
    }
}
