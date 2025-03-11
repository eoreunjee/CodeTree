## **1.1 입력 처리 (O(N))**
- `N`개의 손님 수 데이터를 입력받고, 감독관의 감독 가능 인원을 입력받음.
- **시간 복잡도: O(N)**

## **1.2 감독관 배치 계산 (O(N))**
- 각 시험장에 대해 최소한의 감독관 수를 계산.
- 각 시험장마다 `check_pp()`를 호출하므로 **O(N)**

## **1.3 감독관 배치 계산 세부 과정 (O(1))**
- 총감독관이 감독할 수 있는 인원(`ability[0]`)을 먼저 차감.
- 남은 인원을 부감독관(`ability[1]`)으로 커버.
- 나눗셈과 나머지 연산을 사용하여 **O(1)** 연산으로 해결.

---

## **2. 전체 시간 복잡도**
- `N`개의 시험장에 대해 **O(1)** 연산을 수행하므로 전체 시간 복잡도는 **O(N)**

최악의 경우를 고려하면:

- **N ≤ 1,000,000** (최대 백만 개의 시험장)
- **O(N) ≈ 10⁶** (약 백만 번 연산)

따라서 **충분히 1초 이내에 실행 가능**.

---

## **3. 결론**
## **최악의 시간 복잡도**
**O(N) ≈ O(10⁶)**  

**➡ 간단한 수학적 연산으로 문제를 해결할 수 있으며, 시간 제한 내에 충분히 실행 가능!** 🚀

---

## **4. 느낀 점**
이 문제를 풀면서 **데이터 타입의 중요성**을 다시 한 번 깨달았다.  
처음에는 `int`를 사용했지만, 감독관 수(`result`)를 계산하는 과정에서 **오버플로우**가 발생하여 오답이 나왔다.  
이를 해결하기 위해 **`long` 타입으로 변경**하니 정상적으로 정답이 도출되었다.

## **언제 데이터 타입을 신경 써야 할까?**
- **큰 수의 연산이 반복될 때**  
  → 예: **최대 1,000,000개의 시험장에서 부감독관이 수천만 명 이상 필요할 수도 있음.**  
- **곱셈 연산이 포함될 때**  
  → 작은 수라도 곱해지면 금방 오버플로우 발생 가능.  
- **누적 합을 계산할 때**  
  → 반복문을 통해 계속 더할 경우, `int` 범위를 넘을 수 있음.

이 경험을 통해 **항상 문제에서 주어지는 입력 범위를 고려하고, 적절한 자료형을 선택하는 습관이 중요하다는 것**을 다시 한 번 배우게 되었다. 앞으로는 **입력 크기와 연산 결과를 미리 예측하고 데이터 타입을 결정하는 연습을 더 해야겠다.**

```java
import java.util.*;
import java.io.*;




public class Main {

	static int n;
	static int [] guest;
	static int [] ability;
	static long result;
	
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
		if(peopNum<=ability[0]) {
			return 1;
		}
		else {
		    int tp = (peopNum-ability[0])/ability[1]; //(232-8)/6 = 37.333333-> 37
		    //System.out.println("tp="+tp);
		    int rest = (peopNum-ability[0])%ability[1]; //(232-8)/6 = 37.333333-> 37
		    //System.out.println("rest="+rest);
		    if(rest!=0){
		        tp++;
		    }
		    tp++;
		    //System.out.println("result="+tp);
		    return tp;
		}
		
	}

}
```

# [바이러스 검사 ![Bronze2][b2]](https://www.codetree.ai/training-field/search/problems/virus-detector)

|유형|출처|
|---|---|
|[Greedy](https://www.codetree.ai/training-field/search/?tags=Greedy)|[일반연습 / 문제은행](https://www.codetree.ai/training-field/home)|








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
