import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static int N;
	static int[][] map;
	static boolean[] visit = new boolean[101];
	static int max;
	static boolean flag;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st;

			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
		
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			max = -1;
			flag = false;
			
			for (int move = N - 1; move > 1; move--) { // 한 방향으로 이동하는 횟수
				if (flag) {
					break;
				}
				for (int i = 1; i < move; i++) {
					int j = move - i;
					// 오른쪽 위 i번, 오른쪽 아래 j번 왼쪽 아래 i번, 왼쪽 위 j번
					// move + 1개의 셀을 차지함 시작지점은 처음에 위로 i번 가기 때문에
					for (int xIdx = i; xIdx <= N - 1 - j; xIdx++) {
						for (int yIdx = 0; yIdx <= N - 1 - move; yIdx++) {
							goAround(xIdx, yIdx, i, j);
						}
					}
				}
			}
			
			bw.append("#"+test_case+" "+max+"\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}

	// move 1: right up, move 2: right down, move 3 : left down, move 4 : left up
	private static void goAround(int xIdx, int yIdx, int move1, int move2) {
		Arrays.fill(visit, false);
		int x = xIdx;
		int y = yIdx;
		for (int i = 0; i < move1; i++) {
			if (!visit[map[x][y]]) {
				visit[map[x--][y++]] = true;
			}
			else
				return;
		}
		for (int i = 0; i < move2; i++) {
			if (!visit[map[x][y]]) {
				visit[map[x++][y++]] = true;
			}
			else
				return;
		}
		for (int i = 0; i < move1; i++) {
			if (!visit[map[x][y]]) {
				visit[map[x++][y--]] = true;
			}
			else
				return;
		}
		for (int i = 0; i < move2; i++) {
			if (!visit[map[x][y]]) {
				visit[map[x--][y--]] = true;
			}
			else
				return;
		}
		if (max < (move1 + move2) * 2) {
			max = (move1 + move2) * 2;
			flag = true;
		}
			
	}

}
