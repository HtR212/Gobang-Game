public class AlphaBeta {
	private static int[][] chess;
	private static int chesscolor = 2;
	private static int range;
	private static int D; //Max Depth
	private static int[][] predictions = new int[D][2];
	private static int[] best = {0,0};
	private static int eval(int color) {
		int score = 0;
//		for(int d = 0; d < D; d++) {
//			int i = predictions[d][0];
//			int j = predictions[d][1];
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(chess[i][j]==color) {
					int enemy = 0;
					int count = 0;
					for(int k = 1; k < 5; k++) {
						if(i-k<0) {
							enemy++;
							break;
						}
						if(chess[i-k][j]==3-color) {
							enemy++;
							break;
						}else if(chess[i-k][j]==0)
							break;
						count++;
					}
					for(int k = 1; k < 5; k++) {
						if(i+k>14){
							enemy++;
							break;
						}
						if(chess[i+k][j]==3-color) {
							enemy++;
							break;
						}else if(chess[i+k][j]==0)
							break;
						count++;
					}
					score += cal_score(count,enemy);
					
					enemy = 0;
					count = 0;
					for(int k = 1; k < 5; k++) {
						if(i-k<0||j-k<0){
							enemy++;
							break;
						}
						if(chess[i-k][j-k]==3-color) {
							enemy++;
							break;
						}else if(chess[i-k][j-k]==0)
							break;
						count++;
					}
					for(int k = 1; k < 5; k++) {
						if(i+k>14||j+k>14){
							enemy++;
							break;
						}
						if(chess[i+k][j+k]==3-color) {
							enemy++;
							break;
						}else if(chess[i+k][j+k]==0)
							break;
						count++;
					}
					score += cal_score(count,enemy);
					
					enemy = 0;
					count = 0;
					for(int k = 1; k < 5; k++) {
						if(i-k<0||j+k>14){
							enemy++;
							break;
						}
						if(chess[i-k][j+k]==3-color) {
							enemy++;
							break;
						}else if(chess[i-k][j+k]==0)
							break;
						count++;
					}
					for(int k = 1; k < 5; k++) {
						if(i+k>14||j-k<0){
							enemy++;
							break;
						}
						if(chess[i+k][j-k]==3-color) {
							enemy++;
							break;
						}else if(chess[i+k][j-k]==0)
							break;
						count++;
					}
					score += cal_score(count,enemy);
					
					enemy = 0;
					count = 0;
					for(int k = 1; k < 5; k++) {
						if(j-k<0){
							enemy++;
							break;
						}
						if(chess[i][j-k]==3-color) {
							enemy++;
							break;
						}else if(chess[i][j-k]==0)
							break;
						count++;
					}
					for(int k = 1; k < 5; k++) {
						if(j+k>14){
							enemy++;
							break;
						}
						if(chess[i][j+k]==3-color) {
							enemy++;
							break;
						}else if(chess[i][j+k]==0)
							break;
						count++;
					}
					score += cal_score(count,enemy);
				}
			}
		}
		return score;
	}
	
	private static int cal_score(int count, int enemy) {
		int score = 0;
		if(count>=5)
			score += 100000;
		if(count==4&&enemy<2)
			score += 100000;
		else if(count==3) {
			if(enemy==0)
				score += 10000;
			else if(enemy==1)
				score += 1000;
		}else if(count==2) {
			if(enemy==0)
				score += 1000;
			else if(enemy==1)
				score += 100;
		}else if(count==1) {
			if(enemy==0)
				score += 100;
			else if(enemy==1)
				score += 10;
		}else
			if(enemy==0)
				score += 10;
		return score;
	}
	
	private static int AB(int depth, boolean ai, int alpha, int beta) {
		int v = 0;
		if(depth==0)
			return eval(chesscolor)-eval(3-chesscolor);
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				if(chess[i][j]==0&&checkneighbour(i, j)) {
					chess[i][j] = ai?chesscolor:3-chesscolor;
//					predictions[depth-1] = new int[]{i,j};
					v = -AB(depth-1, !ai, -beta, -alpha);
					chess[i][j] = 0;
					if(v > alpha) {
						if(depth == D) {
							best[0] = i;
							best[1] = j;
						}
						alpha = v;
//						System.out.println(best[0]+" "+best[1]+" "+alpha);
					}
					if(v >= beta)
						return beta;
				}
			}
		}
		return alpha;
	}
	
	private static boolean checkneighbour(int i, int j) {
		int a = 0, b = 0, c = 14, d = 14;
		if(i-range>0)
			a = i-range;
		if(j-range>0)
			b = j-range;
		if(i+range<14)
			c = i+range;
		if(j+range<14)
			d = j+range;
		for(int s = a; s <= c; s++) {
			for(int t = b; t <= d; t++) {
				if(chess[s][t]!=0&&s!=i&&t!=j)
					return true;
			}
		}
		return false;
	}
	
	public static int[] AI(int[][] chess, int chesscolor, int D, int range) {
		AlphaBeta.D = D;
		AlphaBeta.range = range;
		AlphaBeta.chess = chess;
		AlphaBeta.chesscolor = chesscolor;
		AB(D, true, -2147483647, 2147483647);
		return best;
	}
}
