import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * @author Ruchit Modi
 *
 */
public class TicTacToe extends JFrame implements ActionListener {
 private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new TicTacToe();
			}
		});
	}

	private MyJButton[][] button;
	public int[][] gameState;
	private JPanel gameBoard = new JPanel();
	private JLabel whoseTurnLabel;
	private JMenuBar menubar;
	private JMenu firstTurnMenu, versusMenu;
	private JMenuItem player1First, player2First, vsHuman, vsComp;
	int turn, total = 0;
	Random r = new Random();
	String player1Name = "Player 1";
	String player2Name = "Computer";
	private String player1Symbol = "X";
	private String player2Symbol = "O";
	public boolean vsComputer = true;
	Object restartMsg = "Are you sure you want to Restart Game with new settings?";
	
	public TicTacToe() {
		super("Tic Tac Toe");
		setVisible(true);
		setSize(200, 250);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		player1First = new JMenuItem(player1Name + " First");
		player1First.addActionListener(this);
		player2First = new JMenuItem(player2Name + " First");
		player2First.addActionListener(this);
		firstTurnMenu = new JMenu("Turn");firstTurnMenu.setMnemonic(KeyEvent.VK_U);
		firstTurnMenu.add(player1First);firstTurnMenu.add(player2First);
		vsHuman = new JMenuItem("Vs Human", KeyEvent.VK_H);
		vsHuman.addActionListener(this);
		vsComp = new JMenuItem("Vs Computer", KeyEvent.VK_C);
		vsComp.addActionListener(this);
		versusMenu = new JMenu("Versus");versusMenu.setMnemonic(KeyEvent.VK_E);
		versusMenu.add(vsHuman);versusMenu.add(vsComp);
		menubar = new JMenuBar();
		menubar.add(firstTurnMenu);menubar.add(versusMenu);
		setJMenuBar(menubar);
		initBoard();
		startPlay();
		setLocationRelativeTo(null);
	}

	private void initBoard() {
		// TODO Auto-generated method stub
		total = 0;
		setLayout(new BorderLayout());
		gameBoard.setBorder(new EmptyBorder(20, 20, 10, 20));
		add(gameBoard, BorderLayout.CENTER);
		gameBoard.setLayout(new GridLayout(3, 3));
		button = new MyJButton[3][3];gameState = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				button[i][j] = new MyJButton(i, j, this);
				gameState[i][j] = 0;
				gameBoard.add(button[i][j]);
			}
		}
		whoseTurnLabel = new JLabel();
		whoseTurnLabel.setBorder(new EmptyBorder(0, 20, 0, 20));
		add(whoseTurnLabel, BorderLayout.SOUTH);
	}

	private void startPlay() {
		// TODO Auto-generated method stub
		if (turn == 0) {
			player1Symbol = "X";player2Symbol = "O";
			whoseTurnLabel.setText(player1Name + "'s Turn" + " (" + player1Symbol + ")");
		} else {
			player2Symbol = "X";player1Symbol = "O";
			whoseTurnLabel.setText(player2Name + "'s Turn" + " (" + player2Symbol + ")");
			if (vsComputer) {
				computerTurn();
			}
		}
	}

	public void buttonClicked(MyJButton b) {
		// TODO Auto-generated method stub
		total++;
		if (turn == 0) {
			b.setText(player1Symbol);
			gameState[b.row][b.col] = -1;
			b.flag = -1;
			whoseTurnLabel.setText(player2Name + "'s Turn" + " (" + player2Symbol + ")");
		} else {
			b.setText(player2Symbol);
			gameState[b.row][b.col] = 1;
			b.flag = 1;
			whoseTurnLabel.setText(player1Name + "'s Turn" + " (" + player1Symbol + ")");
		}
		turn = 1 - turn;
	}

	public int checkIfWon(int[][] state) {
		// TODO Auto-generated method stub
		for (int i = 0, rowcount = 0, colcount = 0; i < 3; i++, rowcount = 0, colcount = 0) {
			for (int j = 0; j < 3; j++) {
				rowcount += state[i][j];
				colcount += state[j][i];
			}
			if (rowcount == -3 || colcount == -3) {
				return -1;
			}
			if (rowcount == 3 || colcount == 3) {
				return 1;
			}
		}
		if (state[1][1] == -1) {
			if ((state[0][0] == -1 && state[2][2] == -1) || (state[0][2] == -1 && state[2][0] == -1)) {
				return -1;
			}
		}
		if (state[1][1] == 1) {
			if ((state[0][0] == 1 && state[2][2] == 1) || (state[0][2] == 1 && state[2][0] == 1)) {
				return 1;
			}
		}
		return 0;
	}

	public void gameOver(int response) {
		// TODO Auto-generated method stub
		switch (response) {
		case -1:
			wonMsg(player1Name + " Wins");
			break;
		case 1: 
			wonMsg(player2Name + " Wins");
			break;
		default:
			wonMsg("It's a Tie");
			break;
		}
	}

	public void wonMsg(String msg) {
		// TODO Auto-generated method stub
		whoseTurnLabel.setText(msg);
		JOptionPane.showMessageDialog(this, msg);
		if (player1Symbol == "X") {
			turn = 0;
		} else {
			turn = 1;
		}
		restartGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int selection = JOptionPane.showConfirmDialog(this, restartMsg);
		if (selection == JOptionPane.YES_OPTION) {
			if (e.getSource() == player1First) {
				turn = 0;
				
			} else if (e.getSource() == player2First) {
				turn = 1;
			} else if (e.getSource() == vsHuman) {
				vsComputer = false;
				player2Name = "Player 2";
			} else if (e.getSource() == vsComp) {
				vsComputer = true;
				player2Name = "Computer";
			}
			player2First.setText(player2Name + " First");
			restartGame();
		}
	}

	private void restartGame() {
		// TODO Auto-generated method stub
		gameBoard.removeAll();
		remove(whoseTurnLabel);
		initBoard();
		startPlay();
	}

	public void computerTurn() {
		// TODO Auto-generated method stub
		int depth = 9-total, alpha = -10, beta = 10;
		ResultSet result = alphabeta(gameState, depth, alpha, beta, true);
		button[result.step.x][result.step.y].mouseClicked(null);
	}

	private ResultSet alphabeta(int[][] gameState, int depth, int alpha, int beta,
			boolean maxplayer) {
		// TODO Auto-generated method stub
		ResultSet result = new ResultSet(checkIfWon(gameState));
		if (result.value != 0) {
			result.value = (Math.abs(result.value)+depth)*result.value;
			return result;
		} 
		if (depth == 0) {
			result.value = 0;
			return result;
		}	
		if (maxplayer) {
			result.value = -10;
			List<Integer> listOfNumbers = new ArrayList<Integer>();
			for (int i = 0; i < 9; listOfNumbers.add(i++));
			for (int i = 9; i > 0; i--) {
				int stochastic = listOfNumbers.remove(r.nextInt(i));
				int row = stochastic/3, col = stochastic%3;
				if (gameState[row][col] == 0) {
					gameState[row][col] = 1;
					ResultSet childresult = alphabeta(gameState, depth-1, alpha, beta, false);
					if (childresult.value > result.value) {
						result.value = childresult.value;
						result.step.setLocation(row, col);
					}
					alpha = Math.max(alpha, result.value);
					gameState[row][col] = 0;
					if (alpha >= beta) {
						break;
					}
				}
			}
		} else {
			result.value = 10;
			List<Integer> listOfNumbers = new ArrayList<Integer>();
			for (int i = 0; i < 9; listOfNumbers.add(i++));
			for (int i = 9; i > 0; i--) {
				int stochastic = listOfNumbers.remove(r.nextInt(i));
				int row = stochastic/3, col = stochastic%3;
				if (gameState[row][col] == 0) {
					gameState[row][col] = -1;
					ResultSet childresult = alphabeta(gameState, depth-1, alpha, beta, true);
					if (childresult.value < result.value) {
						result.value = childresult.value;
						result.step.setLocation(row, col);
					}
					beta = Math.min(beta, result.value);
					gameState[row][col] = 0;
					if (beta <= alpha) {
						break;
					}
				}
			}
		}
		return result;
	}

}

class ResultSet {

	public int value;
	public Point step;
	
	public ResultSet(int value) {
		// TODO Auto-generated constructor stub
		this.value = value;
		step = new Point();
	}
	
}