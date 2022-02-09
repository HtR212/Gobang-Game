public class EasyAI {
	public static int[] evaluate(int[][] chess, int chesscolor) {
		int x = 0, y = 0, max = 0;
		for(int i = 0; i < 15; i++) {
			for(int j = 0; j < 15; j++) {
				int eval = 0;
				if(chess[i][j]==0) {
					//up
					for(int k = 1; k < 5; k++) {
						if(i-k<0)
							break;
						if(chess[i-k][j]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i-k][j]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//upright
					for(int k = 1; k < 5; k++) {
						if(i-k<0||j+k>=15)
							break;
						if(chess[i-k][j+k]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i-k][j+k]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//right
					for(int k = 1; k < 5; k++) {
						if(j+k>=15)
							break;
						if(chess[i][j+k]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i][j+k]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//downright
					for(int k = 1; k < 5; k++) {
						if(i+k>=15||j+k>=15)
							break;
						if(chess[i+k][j+k]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i+k][j+k]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//down
					for(int k = 1; k < 5; k++) {
						if(i+k>=15)
							break;
						if(chess[i+k][j]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i+k][j]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//downleft
					for(int k = 1; k < 5; k++) {
						if(i+k>=15||j-k<0)
							break;
						if(chess[i+k][j-k]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i+k][j-k]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//left
					for(int k = 1; k < 5; k++) {
						if(j-k<0)
							break;
						if(chess[i][j-k]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i][j-k]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
					//upleft
					for(int k = 1; k < 5; k++) {
						if(i-k<0||j-k<0)
							break;
						if(chess[i-k][j-k]==0) {
							eval += (5-k)*10;
							break;
						}
						if(chess[i-k][j-k]==chesscolor) {
							eval += k*10;
							break;
						}
						eval += k*100;
					}
				}
				if(eval>max) {
					max = eval;
					x = i;
					y = j;
				}
			}
		}
		return new int[] {x,y};
	}
}
