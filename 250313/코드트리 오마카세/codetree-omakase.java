import java.util.*;
import java.io.*;

public class Main {

    static int L, Q, t, pivot, loc, time = 0, sushi_num, people = 0;
    static HashMap<Integer, ArrayDeque<String>> rail = new HashMap<>();
    static HashMap<String, int[]> guests = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        for (int q = 0; q < Q; q++) {
            time++;
            StringTokenizer cmd = new StringTokenizer(bf.readLine());
            int inc = Integer.parseInt(cmd.nextToken());
            t = Integer.parseInt(cmd.nextToken());

            // 시간이 다를 경우, 남은 초밥 처리
            if (time != t) {
                processTime(t);
            }

            if (inc == 100) { // 초밥 올리기
                int x = Integer.parseInt(cmd.nextToken());
                String name = cmd.nextToken();
                pivot = t % L;
                loc = (x - pivot + L) % L;

                rail.putIfAbsent(loc, new ArrayDeque<>());
                rail.get(loc).offer(name);

                processGuests();
            } else if (inc == 200) { // 손님 추가
                int x = Integer.parseInt(cmd.nextToken());
                pivot = t % L;
                String g = cmd.nextToken();
                int n = Integer.parseInt(cmd.nextToken());
                loc = (x - pivot + L) % L;

                if (rail.containsKey(loc)) {
                    ArrayDeque<String> deque = rail.get(loc);
                    Iterator<String> it = deque.iterator();
                    while (it.hasNext() && n > 0) {
                        if (it.next().equals(g)) {
                            it.remove();
                            n--;
                        }
                    }
                }

                if (n > 0) {
                    guests.put(g, new int[]{x, n});
                }

                processGuests();
            } else if (inc == 300) { // 사진 촬영
                processGuests();
                sushi_num = rail.values().stream().mapToInt(ArrayDeque::size).sum();
                System.out.println(guests.size() + " " + sushi_num);
            }
        }
    }

    static void processTime(int targetTime) {
        while (time < targetTime) {
            pivot = time % L;
            processGuests();
            time++;
        }
    }

    static void processGuests() {
        pivot = time % L;
        Iterator<Map.Entry<String, int[]>> it = guests.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, int[]> entry = it.next();
            String g = entry.getKey();
            int[] info = entry.getValue();
            loc = (info[0] - pivot + L) % L;

            if (rail.containsKey(loc)) {
                ArrayDeque<String> deque = rail.get(loc);
                Iterator<String> sushiIt = deque.iterator();
                while (sushiIt.hasNext()) {
                    if (sushiIt.next().equals(g)) {
                        sushiIt.remove();
                        info[1]--;
                        if (info[1] == 0) {
                            it.remove();
                        }
                        break;
                    }
                }
            }
        }
    }
}
