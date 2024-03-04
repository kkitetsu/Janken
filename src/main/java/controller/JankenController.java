package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JankenController
 */
@WebServlet("/janken")
public class JankenController extends HttpServlet {
	
	public enum Janken {
		ROCK("rock"),
		SCISSORS("scissors"),
		PAPER("paper");
		
		private final String type;
		
		Janken(String type) {
			this.type  = type;
		}
		
		public String getType() { return type; }
		public boolean equals(Janken other) { return type == other.type;}
		
	}
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JankenController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "WEB-INF/views/janken.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int numBot = Integer.parseInt(request.getParameter("numBot"));
		
		Random random = new Random();
		
		Janken userMove = getUserMove(request.getParameter("selectedJanken"));
		
		List<Janken> botsMove = new ArrayList<>();      // Track the bots move in Janken enum
		List<String> botsMoveList = new ArrayList<>();  // Record the bots move: "rock", "scissor", or "paper"
		for (int i = 0; i < numBot; i++) {
			botsMove.add(getBotMove(random.nextInt(3)));
			botsMoveList.add("Images/" + botsMove.get(i).type + ".png");
		}
		
		String resultMessage = getGameResult(userMove, botsMove); // "引き分け", "勝ち", or "負け"
		String userMoveStr = "Images/" + userMove.type + ".png";  // e.g. Images/paper.ing
		
		request.setAttribute("result", resultMessage); // "引き分け", "勝ち", or "負け"
		request.setAttribute("userMove", userMoveStr); // Images/paper.ing, Images/rock.ing, or Images/scissors.ing
		request.setAttribute("botsMoveList", botsMoveList);	
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/result.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Get the result of the game.
	 * 
	 * @param userMove
	 * @param botMove
	 * @return "引き分け", "勝ち", or "負け" based on the user's input and bots
	 */
	public String getGameResult(Janken userMove, List<Janken> botMove) {
		if (botMove.size() == 1) {
			return getResult(userMove, botMove.get(0));
		}
		return getGameResultMultiple(userMove, botMove);
	}
	
	/**
	 * Get the game result if 2 or more bots are deployed.
	 * 
	 * @param userMove
	 * @param botMove
	 * @return "引き分け", "勝ち", or "負け" based on the user's input and bots
	 */
	public String getGameResultMultiple(Janken userMove, List<Janken> botMove) {
		/**
		 * Use set to record the game situation.
		 * By using set, we can reasonably ignore the duplicate, since 
		 *   we don't care about duplicates. (2 rocks and 4 rocks, as long as they are played by bots it doesn't mater)
		 */
		Set<Janken> set = new HashSet<>();  
		set.add(userMove);
		for (int i = 0; i < botMove.size(); i++) {
			set.add(botMove.get(i));
		}
		
		// if the set contains paper, rock, and scissor, it must be a draw
		if (set.contains(Janken.PAPER) && 
			set.contains(Janken.ROCK) && 
			set.contains(Janken.SCISSORS)) {
			return "引き分け";
		}
		
		set.remove(userMove); // Now the set only contains the bots' move
		
		// In the case the palyer and bots played exactly same move
		if (set.isEmpty()) {
			return "引き分け";
		}
		
		/**
		 * User's move is passed in the argument.
		 * The set only contains the bot's move, and there are no duplicate.
		 * Since there are no dulicate for bots move, it is 1 vs 1, or a user vs a bot.
		 * Therefore, the only possible situation is either user wins or loses.
		 * So, we can safely call the function getResult to obtain the game result in string. 
		 */
		
		// After we have bot's move in Janken, call getResult with userMove Janken and tmp Janken
		return getResult(userMove, set.iterator().next());
	}
	
	/**
	 * Determine if user wins a single bot.
	 * 
	 * @param userInput
	 * @param bot
	 * @return true is user wins, false otherwise
	 */
	public boolean userWins(Janken userInput, Janken bot) {
		return (userInput == Janken.ROCK && bot == Janken.SCISSORS) || 
			   (userInput == Janken.PAPER && bot == Janken.ROCK) || 
			   (userInput == Janken.SCISSORS && bot == Janken.PAPER);
	}
	
	public boolean isDraw(Janken userInput, Janken bot) {
		return userInput.equals(bot);
	}
	
	/**
	 * Get the game result of a user vs a bot.
	 * 
	 * @param userInput
	 * @param bot
	 * @return "引き分け", "勝ち", or "負け"
	 */
	public String getResult(Janken userInput, Janken bot) {
		if (isDraw(userInput, bot)) {
			return "引き分け";
		} else if (userWins(userInput, bot)) {
			return "勝ち";
		} else {
			return "負け";
		}
		
	}
	
	/**
	 * Get the user's move based on the http servlet request.
	 * 
	 * @param move
	 * @return PAPER, ROCK, or SCISSORS of Janken enum
	 */
	public Janken getUserMove(String move) {
		if (move.equals("paper")) {
			return Janken.PAPER;
		} else if (move.equals("rock")) {
			return Janken.ROCK;
		} else {
			return Janken.SCISSORS;
		}
	}
	
	/**
	 * Get bot's move based on the random generated int value.
	 * @param num
	 * @return PAPER, ROCK, or SCISSORS of Janken enum
	 */
	public Janken getBotMove(int num) {
		if (num == 0) {
			return Janken.PAPER;
		} else if (num == 1) {
			return Janken.ROCK;
		} else {
			return Janken.SCISSORS;
		}
	}

}
