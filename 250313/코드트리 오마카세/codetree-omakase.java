import java.util.*;
import java.io.*;

public class Main {
    static int L, Q, t, pivot, loc;
    static HashMap<String, Integer> id;
    static int idd;
    static HashMap<Integer, HashMap<Integer, Integer>> rail;
    static HashMap<Integer, int[]> guests;
    static int time = 0;
    static int sushi_num = 0;
    static int people = 0;
    static HashSet<Integer> bin;

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        rail = new HashMap<>();
        guests = new HashMap<>();
        id = new HashMap<>();

        for (int q = 0; q < Q; q++) {
            time++;
            StringTokenizer cmd = new StringTokenizer(bf.readLine());
            int inc = Integer.parseInt(cmd.nextToken());
            t = Integer.parseInt(cmd.nextToken());

            // 시간 갱신
            if (time != t) {
                processTimeJump(t);
            }

            if (inc == 100) {
                int x = Integer.parseInt(cmd.nextToken());
                String name = cmd.nextToken();
                if (!id.containsKey(name)) {
                    id.put(name, idd++);
                }
                int sushiType = id.get(name);
                pivot = t % L;
                loc = (x - pivot + L) % L;

                rail.putIfAbsent(loc, new HashMap<>());
                rail.get(loc).put(sushiType, rail.get(loc).getOrDefault(sushiType, 0) + 1);
                sushi_num++; // 초밥 개수 증가

                processSushiConsumption();
            } else if (inc == 200) {
                int x = Integer.parseInt(cmd.nextToken());
                String guestName = cmd.nextToken();
                int g = id.get(guestName);
                int n = Integer.parseInt(cmd.nextToken());
                pivot = t % L;
                loc = (x - pivot + L) % L;

                if (rail.containsKey(loc) && rail.get(loc).containsKey(g)) {
                    int available = rail.get(loc).get(g);
                    int eaten = Math.min(n, available);
                    sushi_num -= eaten; // 초밥 개수 감소
                    n -= eaten;
                    if (available == eaten) {
                        rail.get(loc).remove(g);
                    } else {
                        rail.get(loc).put(g, available - eaten);
                    }
                }

                if (n > 0) {
                    guests.put(g, new int[]{x, n});
                    people++; // 손님 수 증가
                }

                processSushiConsumption();
            } else if (inc == 300) {
                processSushiConsumption();
                System.out.println(people + " " + sushi_num);
            }
        }
    }

    // 시간을 t까지 진행하는 메서드
    static void processTimeJump(int targetTime) {
        while (time < targetTime) {
            time++;
            pivot = time % L;
            bin = new HashSet<>();

            for (int g : guests.keySet()) {
                loc = (guests.get(g)[0] - pivot + L) % L;
                if (rail.containsKey(loc) && rail.get(loc).containsKey(g)) {
                    int available = rail.get(loc).get(g);
                    int eaten = Math.min(guests.get(g)[1], available);
                    sushi_num -= eaten;
                    guests.get(g)[1] -= eaten;

                    if (available == eaten) {
                        rail.get(loc).remove(g);
                    } else {
                        rail.get(loc).put(g, available - eaten);
                    }

                    if (guests.get(g)[1] == 0) {
                        bin.add(g);
                    }
                }
            }

            for (int g : bin) {
                guests.remove(g);
                people--; // 손님 수 감소
            }
        }
    }

    // 손님이 초밥을 먹는 과정 최적화
    static void processSushiConsumption() {
        pivot = time % L;
        bin = new HashSet<>();

        for (int g : guests.keySet()) {
            loc = (guests.get(g)[0] - pivot + L) % L;
            if (rail.containsKey(loc) && rail.get(loc).containsKey(g)) {
                int available = rail.get(loc).get(g);
                int eaten = Math.min(guests.get(g)[1], available);
                sushi_num -= eaten;
                guests.get(g)[1] -= eaten;

                if (available == eaten) {
                    rail.get(loc).remove(g);
                } else {
                    rail.get(loc).put(g, available - eaten);
                }

                if (guests.get(g)[1] == 0) {
                    bin.add(g);
                }
            }
        }

        for (int g : bin) {
            guests.remove(g);
            people--; // 손님 수 감소
        }
    }
}
