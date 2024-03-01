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
		// TODO Auto-generated method stub
		int numBot = Integer.parseInt(request.getParameter("numBot"));
		
		Random random = new Random();
		
		Janken userMove = getUserMove(request.getParameter("selectedJanken"));
		
		List<Janken> botsMove = new ArrayList<>();
		List<String> botsMoveList = new ArrayList<>();
		for (int i = 0; i < numBot; i++) {
			botsMove.add(getBotMove(random.nextInt(3)));
			botsMoveList.add("Images/" + botsMove.get(i).type + ".png");
		}
		
		String resultMessage = getGameResult(userMove, botsMove);
		String userMoveStr = "Images/" + userMove.type + ".png";
		
		request.setAttribute("result", resultMessage);
		request.setAttribute("userMove", userMoveStr);	
		request.setAttribute("botsMoveList", botsMoveList);	
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/result.jsp");
		dispatcher.forward(request, response);
	}
	
	public String getGameResult(Janken userMove, List<Janken> botMove) {
		if (botMove.size() == 1) {
			return getResult(userMove, botMove.get(0));
		}
		return getGameResultMultiple(userMove, botMove);
	}
	
	public String getGameResultMultiple(Janken userMove, List<Janken> botMove) {
		Set<String> set = new HashSet<>();
		set.add(userMove.type);
		for (int i = 0; i < botMove.size(); i++) {
			set.add(botMove.get(i).type);
		}
		if (set.contains(Janken.PAPER.type) && 
			set.contains(Janken.ROCK.type) && 
			set.contains(Janken.SCISSORS.type)) {
			return "引き分け";
		}
		
		set.remove(userMove.type); // Now the set only contains the bots' move
		/**
		 * Case 1: {user: paper, set(scissor, 
		 * 
		 * 
		 */
		String tmpp = "";
		for (String each : set) {
			System.out.println(each);
			tmpp = each;
		}
		Janken tmp = Janken.PAPER;

		if (tmpp.equals("rock")) {
			tmp = Janken.ROCK;
		} else if (tmpp.equals("scissors")) {
			tmp = Janken.SCISSORS;
		}
		return getResult(userMove, tmp);
	}
	
	public boolean userWins(int userInput, int bot) {
		return (userInput == 0 && bot == 1) || (userInput == 1 && bot == 2) || (userInput == 2 && bot == 0);
	}
	
	public boolean isDraw(int userInput, int bot) {
		return userInput == bot;
	}
	
	public String getResult(Janken userInput, Janken bot) {
		if (isDraw(userInput.value, bot.value)) {
			return "引き分け";
		} else if (userWins(userInput.value, bot.value)) {
			return "勝ち";
		} else {
			return "負け";
		}
		
	}
	
	public Janken getUserMove(String move) {
		if (move.equals("paper")) {
			return Janken.PAPER;
		} else if (move.equals("rock")) {
			return Janken.ROCK;
		} else {
			return Janken.SCISSORS;
		}
	}
	
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
