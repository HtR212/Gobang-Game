import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class UI extends JFrame{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UI gobang;
    private int chess[][] = new int[15][15];
    private JMenuBar bar = new JMenuBar();
    private JMenu[] menus = {new JMenu("  Mode   "),new JMenu("Operation")};
    private JRadioButtonMenuItem[] modes = {new JRadioButtonMenuItem("  PvP   "), new JRadioButtonMenuItem("  PvE   ")};
    private JMenuItem[] operations = {new JMenuItem("Start   "), new JMenuItem("Undo    "), new JMenuItem("Pause   "), new JMenuItem("Restart ")};
    private GamePanel board = new GamePanel(chess);
    final int X = 75, Y = 50, CHESSPIECE = 30;
    final float SIZE = 35.0f;
    private ButtonGroup modeButton = new ButtonGroup();
    private boolean pvp = true;
    private ActionListener a = new MenuListener();
    private PanelListener p = new PanelListener();
    private Graphics graph;
    private boolean black = true;
    private int record[] = new int[450];
    private int count = 0;
    private JFrame bw, ww;
    private boolean pause = false;
    private int locx = 0, locy = 0, rx = 0, ry = 0;
    private int chesscolor = 2;
    
    /**
     * Set the UI layout
     */
    public UI() {
        super("Gobang");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(655,655);
        setLocationRelativeTo(null);
        menu();
        setJMenuBar(bar);
        board();
        setResizable(false);
        setVisible(true);
        graph = board.getGraphics();
        blackwin();
        whitewin();
        tracker.start();
    }
    
    /**
     * Set the MenuBar
     */
    public void menu() {
        for(int i=0; i<modes.length; i++) {
            menus[0].add(modes[i]);
            modeButton.add(modes[i]);
            modes[i].addActionListener(a);
        }
        for(int i=0; i<operations.length; i++) {
            menus[1].add(operations[i]);
            operations[i].addActionListener(a);
        }
        for(JMenu menu : menus) {
            bar.add(menu);
        }
        modes[0].setSelected(true);
        operations[1].setEnabled(false);
        operations[2].setEnabled(false);
        operations[3].setEnabled(false);
        modes[0].setActionCommand("pvp");
        modes[1].setActionCommand("pve");
        operations[0].setActionCommand("start");
        operations[1].setActionCommand("undo");
        operations[2].setActionCommand("pause");
        operations[3].setActionCommand("restart");
    }
    
    public void board() {
        add(board,BorderLayout.CENTER);
        board.setBackground(new Color(230, 190, 130));
    }
    
    public void paintchesspiece(int x, int y) {
        int xx, yy;
        xx=Math.round((x-X)/SIZE)*((int)SIZE)+X-CHESSPIECE/2;
        yy=Math.round((y-Y)/SIZE)*((int)SIZE)+Y-CHESSPIECE/2;
//        if(chess[Math.round((x-X)/SIZE)][Math.round((y-Y)/SIZE)]==0) {
        record[count*2]=Math.round((x-X)/SIZE);
        record[count*2+1]=Math.round((y-Y)/SIZE);
        graph.setColor(black?Color.BLACK:Color.WHITE);
        graph.fillOval(xx, yy, CHESSPIECE, CHESSPIECE);
        chess[record[count*2]][record[count*2+1]]=black?1:2;
        count++;
        black=!black;
//        }
    }
    
    public void tracking(int x, int y) {
        int xx, yy;
        xx=Math.round((x-X)/SIZE)*((int)SIZE)+X-CHESSPIECE/2;
        yy=Math.round((y-Y)/SIZE)*((int)SIZE)+Y-CHESSPIECE/2;
        if(chess[Math.round((x-X)/SIZE)][Math.round((y-Y)/SIZE)]==0) {
            rx = Math.round((x-X)/SIZE);
            ry = Math.round((y-Y)/SIZE);
            graph.setColor(black?Color.BLACK:Color.WHITE);
            graph.fillOval(xx, yy, CHESSPIECE, CHESSPIECE);
        }
    }
    
    public boolean win(int x, int y) {
        int s = 0;
        for(int i=x-1; i>=0; i--) {
            if(chess[i][y]!=chess[x][y]) {break;}
            s++;
        }
        for(int i=x+1; i<15; i++) {
            if(chess[i][y]!=chess[x][y]) {break;}
            s++;
        }
        if(s>3) {return true;}
        s=0;
        for(int i=y-1; i>=0; i--) {
            if(chess[x][i]!=chess[x][y]) {break;}
            s++;
        }
        for(int i=y+1; i<15; i++) {
            if(chess[x][i]!=chess[x][y]) {break;}
            s++;
        }
        if(s>3) {return true;}
        s=0;
        for(int i=x-1; i>=0; i--) {
            if((y-x+i)<0) {break;}
            if(chess[i][y-x+i]!=chess[x][y]) {break;}
            s++;
        }
        for(int i=x+1; i<15; i++) {
            if((y-x+i)>14) {break;}
            if(chess[i][y-x+i]!=chess[x][y]) {break;}
            s++;
        }
        if(s>3) {return true;}
        s=0;
        for(int i=x-1; i>=0; i--) {
            if((x-i+y)>14) {break;}
            if(chess[i][x-i+y]!=chess[x][y]) {break;}
            s++;
        }
        for(int i=x+1; i<15; i++) {
            if((x-i+y)<0) {break;}
            if(chess[i][x-i+y]!=chess[x][y]) {break;}
            s++;
        }
        if(s>3) {return true;}
        return false;
    }
    
    public void blackwin() {
        bw = new JFrame();
        bw.setSize(150,100);
        bw.setLocationRelativeTo(null);
        bw.setAlwaysOnTop(true);
        bw.setResizable(false);
        JLabel bwin = new JLabel("Black Wins",SwingConstants.CENTER);
        JButton brestart = new JButton("Restart");
        bw.add(bwin,BorderLayout.CENTER);
        bw.add(brestart,BorderLayout.SOUTH);
        brestart.setActionCommand("restart");
        brestart.addActionListener(a);
    }
    
    public void whitewin() {
        ww = new JFrame();
        ww.setSize(150,100);
        ww.setLocationRelativeTo(null);
        ww.setAlwaysOnTop(true);
        ww.setResizable(false);
        JLabel wwin = new JLabel("White Wins",SwingConstants.CENTER);
        JButton wrestart = new JButton("Restart");
        ww.add(wwin,BorderLayout.CENTER);
        ww.add(wrestart,BorderLayout.SOUTH);
        wrestart.setActionCommand("restart");
        wrestart.addActionListener(a);
    }
    
    Runnable track = new Runnable() {
        public void run() {
            while(true) {
                try {
                    if(!pause) {
                        if(locx>(X-CHESSPIECE/2)&&locx<(X+14*((int)SIZE)+CHESSPIECE/2)&&locy>(Y-CHESSPIECE/2)&&locy<(Y+14*((int)SIZE)+CHESSPIECE/2)){
                            if(Math.round((locx-X)/SIZE)!=rx||Math.round((locy-Y)/SIZE)!=ry) {board.repaint();}
                            tracking(locx, locy);
                            //System.out.print("paint");
                        }else {
                            board.repaint();
                        }
                        //System.out.println("tracking ");
                    }
                    Thread.sleep(30);
                }catch(InterruptedException e) {
                    System.out.println("Interrupted");
                }
            }
        }
    };
    
    Thread tracker = new Thread(track);
    
    /**
     * A featured game panel based on JPanel
     * @author Haotian Ren
     *
     */
    public class GamePanel extends JPanel{
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int chess[][];
        
        public GamePanel(int chess[][]) {
            this.chess = chess;
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(int i=0; i<15; i++) {
                g.drawLine(X,Y+i*((int)SIZE),X+14*((int)SIZE),Y+i*((int)SIZE));
                g.drawLine(X+i*((int)SIZE),Y,X+i*((int)SIZE),Y+14*((int)SIZE));
            }
            paintChess(g);
        }
        
        public void paintChess(Graphics g) {
            for(int i=0; i<15; i++) {
                for(int j=0; j<15; j++) {
                    if(chess[i][j]==1) {
                        g.setColor(Color.BLACK);
                        g.fillOval(X+i*((int)SIZE)-CHESSPIECE/2, Y+j*((int)SIZE)-CHESSPIECE/2, CHESSPIECE, CHESSPIECE);
                    }
                    if(chess[i][j]==2) {
                        g.setColor(Color.WHITE);
                        g.fillOval(X+i*((int)SIZE)-CHESSPIECE/2, Y+j*((int)SIZE)-CHESSPIECE/2, CHESSPIECE, CHESSPIECE);
                    }
                }
            }
        }
        
    }
    
    /**
     * 
     * @author Haotian Ren
     *
     */
    class MenuListener implements ActionListener{
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(e.getActionCommand().equals("pvp")) {
                pvp = true;
            }
            if(e.getActionCommand().equals("pve")) {
                pvp = false;
            }
            if(e.getActionCommand().equals("start")) {
            	modes[0].setEnabled(false);
            	modes[1].setEnabled(false);
                operations[0].setEnabled(false);
                operations[2].setEnabled(true);
                operations[3].setEnabled(true);
                board.addMouseListener(p);
                board.addMouseMotionListener(p);
                operations[0].setText("Resume");
                if(pvp)
                	if(count>0) {operations[1].setEnabled(true);}
                if(!pvp) {
                	if(count == 0)
		                if(chesscolor == 1)
		                	paintchesspiece(X+7*((int)SIZE), Y+7*((int)SIZE));
                	if((count>0&&chesscolor==2)||(count>1&&chesscolor==1)) {operations[1].setEnabled(true);}
                }
                pause = false;
            }
            if(e.getActionCommand().equals("undo")) {
            	if(pvp) {
	                count--;
	                if(count==0) {operations[1].setEnabled(false);}
	                chess[record[count*2]][record[count*2+1]]=0;
	                black=!black;
	                board.repaint();
            	}else {
            		count--;
            		chess[record[count*2]][record[count*2+1]]=0;
            		count--;
            		chess[record[count*2]][record[count*2+1]]=0;
            		if((count==0&&chesscolor==2)||(count==1&&chesscolor==1)) {operations[1].setEnabled(false);}
            		board.repaint();
            	}
            }
            if(e.getActionCommand().equals("pause")) {
                operations[0].setEnabled(true);
                operations[1].setEnabled(false);
                operations[2].setEnabled(false);
                board.removeMouseListener(p);
                board.removeMouseMotionListener(p);
                pause = true;
            }
            if(e.getActionCommand().equals("restart")) {
            	modes[0].setEnabled(true);
            	modes[1].setEnabled(true);
                bw.setVisible(false);
                ww.setVisible(false);
                operations[0].setEnabled(true);
                operations[1].setEnabled(false);
                operations[2].setEnabled(false);
                for(int i=0; i<15; i++) {
                    for(int j=0; j<15; j++) {
                        chess[i][j]=0;
                    }
                }
                count=0;
                black=true;
                board.removeMouseListener(p);
                board.removeMouseMotionListener(p);
                operations[0].setText("Start");
//                board.addMouseListener(p);
//                board.addMouseMotionListener(p);
                board.repaint();
            }
        }
        
    }
    
    /**
     * 
     * @author Haotian Ren
     *
     */
    class PanelListener implements MouseListener, MouseMotionListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            if(!pause&&e.getX()>(X-CHESSPIECE/2)&&e.getX()<(X+14*((int)SIZE)+CHESSPIECE/2)&&e.getY()>(Y-CHESSPIECE/2)&&e.getY()<(Y+14*((int)SIZE)+CHESSPIECE/2)&&chess[Math.round((e.getX()-X)/SIZE)][Math.round((e.getY()-Y)/SIZE)]==0){
                paintchesspiece(e.getX(), e.getY());
                if(count>0) {operations[1].setEnabled(true);}
                if(win(record[(count-1)*2],record[(count-1)*2+1])) {
                    board.removeMouseListener(p);
                    board.removeMouseMotionListener(p);
                    operations[1].setEnabled(false);
                    operations[2].setEnabled(false);
                    pause = true;
                    if(black) {
                        System.out.println("WHITE WINS");
                        ww.setVisible(true);
                    }else {
                        System.out.println("BLACK WINS");
                        bw.setVisible(true);
                    }
                }
                if(!pvp&&pause==false) {
                	pause = true;
                	operations[0].setEnabled(false);
                	operations[1].setEnabled(false);
                	operations[2].setEnabled(false);
                	operations[3].setEnabled(false);
                	int[] t = new int[2];
                	if(count<4)
                		t = EasyAI.evaluate(chess, chesscolor);
                	else
                		t = AlphaBeta.AI(chess, chesscolor, 2, 2);
                	paintchesspiece(X+t[0]*((int)SIZE), Y+t[1]*((int)SIZE));
                	pause = false;
                	operations[0].setEnabled(true);
                	operations[1].setEnabled(true);
                	operations[2].setEnabled(true);
                	operations[3].setEnabled(true);
                	if(win(record[(count-1)*2],record[(count-1)*2+1])) {
                        board.removeMouseListener(p);
                        board.removeMouseMotionListener(p);
                        operations[1].setEnabled(false);
                        operations[2].setEnabled(false);
                        pause = true;
                        if(black) {
                            System.out.println("WHITE WINS");
                            ww.setVisible(true);
                        }else {
                            System.out.println("BLACK WINS");
                            bw.setVisible(true);
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub
            locx = e.getX();
            locy = e.getY();
        }
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gobang = new UI();
            }
        });
    }
    
}
