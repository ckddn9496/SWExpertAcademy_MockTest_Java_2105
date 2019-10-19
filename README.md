# SWExpertAcademy_MockTest_Java_2105

## SW Expert Academy 2105. [모의 SW 역량테스트] 디저트 카페

### 1. 문제설명

출처: https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5VwAr6APYDFAWu

input으로 디저트 카페의 정보를 담은 지도의 한변의 길이 `N`이 들어온다. 이어서 `N * N`의 지도 정보가 들어온다. 지도에 숫자값은 디저트 카페의 종류를 말한다. 한 지점에서 시작하여 대각선으로만 이동하여 경로가 사각형이 되도록 짜서 정확히 시작한 가게로 돌아오는 경로를 짜려고한다. 이때, 경로상에 이미 한번 방문한 카페는 다시 가지않아야만 한다. 이런 경로를 구성하려고 할때 방문할 수 있는 디저트의 수 중 최대 방문횟수를 출력하는 문제. 사각형의 한변의 길이는 최소 `1`이상이어야 한다. 

[제약사항]

    1. 시간제한 : 최대 50개 테스트 케이스를 모두 통과하는 데 C/C++/Java 모두 3초
    2. 디저트 카페가 모여있는 지역의 한 변의 길이 N은 4 이상 20 이하의 정수이다. (4 ≤ N ≤ 20)
    3. 디저트 종류를 나타나는 수는 1 이상 100 이하의 정수이다.


[입력]

> 입력의 맨 첫 줄에는 총 테스트 케이스의 개수 T가 주어지고, 그 다음 줄부터 `T`개의 테스트 케이스가 주어진다.
> 각 테스트 케이스의 첫 번째 줄에는 디저트 카페가 모여있는 지역의 한 변의 길이 `N`이 주어진다.
> 그 다음 `N` 줄에는 `N * N` 크기의 디저트 카페에서 팔고 있는 디저트 종류에 대한 정보가 주어진다.


[출력]

> 테스트 케이스 개수만큼 T개의 줄에 각각의 테스트 케이스에 대한 답을 출력한다.
> 각 줄은 `#t`로 시작하고 공백을 하나 둔 다음 정답을 출력한다. (`t`는 `1`부터 시작하는 테스트 케이스의 번호이다)
> 출력해야 할 정답은 가능한 경우 중 디저트를 가장 많이 먹을 때의 디저트 수 이다.
> 만약, 디저트를 먹을 수 없는 경우 정답은 `-1`이다.


### 2. 풀이

이동할 수 있는 방향에 따라 횟수를 구분하였다. 오른쪽 위 방향으로 이동하는 횟수를 `move1`, 오른쪽 아래 방향으로 이동하는 횟수를 `move2`라고 할때, 경로가 사각형이어야 하기 때문에 대칭적으로 왼쪽 아래로 이동하는 횟수는 `move1`, 왼쪽 위로 이동하는 횟수는 `move2`와 같게된다. 길이 `N`의 지도에서 이동할 수 있는 최대 횟수는 `N-1`번이다. `move1 + move2 = move`라고 할때 `move`를 `N-1`부터 `2`사이의 값으로 지정하여 `move1` 과 `move2`를 각각 설정후 모든 시작가능한 지점에 대하여 탐색을 시작해준다.

```java

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

```

탐색을 시작하면 방문한 디저트 카페를 체크하기위해 `visit`을 초기화하여 이동방향에 따라 디저트카페의 값을 검사하여 방문을 기록한다. 한번이라도 중복된다면 바로 탐색을 중단한다. 마지막까지 중복된 카페가 없다면 `max`값을 갱신한후 플래그를 `true`로 만든다. 검사를 가장 큰 사각형부터 시작하기 때문에 한번이라도 성공한다면 이후에 만들어진 경로의 방문가게 수는 작거나 같기 때문에 검사할 필요가없다.

```java

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

```
