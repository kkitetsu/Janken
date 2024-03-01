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
		ROCK(0, "rock"),
		SCISSORS(1, "scissors"),
		PAPER(2, "paper");
		
		private final int value;
		private final String type;
		
		Janken(int value, String type) {
			this.value = value;
			this.type  = type;
		}
		
		public int getValue() { return value; }
		public String getType() { return type; }
		
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
		Set<String> set = new HashSet<>();  
		set.add(userMove.type);
		for (int i = 0; i < botMove.size(); i++) {
			set.add(botMove.get(i).type);
		}
		
		// if the set contains paper, rock, and scissor, it must be a draw
		if (set.contains(Janken.PAPER.type) && 
			set.contains(Janken.ROCK.type) && 
			set.contains(Janken.SCISSORS.type)) {
			return "引き分け";
		}
		
		set.remove(userMove.type); // Now the set only contains the bots' move
		
		/**
		 * User's move is passed in the argument.
		 * The set only contains the bot's move, and there are no duplicate.
		 * Since there are no dulicate for bots move, it is 1 vs 1, or a user vs a bot.
		 * Therefore, the only possible situation is either user wins or loses.
		 * So, we can safely call the function getResult to obtain the game result in string. 
		 */
		
		// First, obtain the bot's move in string format
		String tmpp = "";
		for (String each : set) {
			tmpp = each;
		}
		
		// Then, prepare a Janken for bot
		Janken tmp = Janken.PAPER;
		
		if (tmpp.equals("rock")) {
			tmp = Janken.ROCK;
		} else if (tmpp.equals("scissors")) {
			tmp = Janken.SCISSORS;
		}
		
		// After we have bot's move in Janken, call getResult with userMove Janken and tmp Janken
		return getResult(userMove, tmp);
	}
	
	/**
	 * Determine if user wins a single bot.
	 * Based on the Janken enum, 0 is rock, 1 is scissor, and 2 is paper.
	 * So, 0 wins 1, 1 wins 2, and 2 wins 0.
	 * 
	 * @param userInput
	 * @param bot
	 * @return true is user wins, false otherwise
	 */
	public boolean userWins(int userInput, int bot) {
		return (userInput == 0 && bot == 1) || (userInput == 1 && bot == 2) || (userInput == 2 && bot == 0);
	}
	
	public boolean isDraw(int userInput, int bot) {
		return userInput == bot;
	}
	
	/**
	 * Get the game result of a user vs a bot.
	 * 
	 * @param userInput
	 * @param bot
	 * @return "引き分け", "勝ち", or "負け"
	 */
	public String getResult(Janken userInput, Janken bot) {
		if (isDraw(userInput.value, bot.value)) {
			return "引き分け";
		} else if (userWins(userInput.value, bot.value)) {
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
