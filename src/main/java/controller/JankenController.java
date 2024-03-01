package controller;

import java.io.IOException;
import java.util.Random;

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
		String userInput1 = request.getParameter("rock");
		String userInput2 = request.getParameter("scissors");
		String userInput3 = request.getParameter("paper");
		Janken userMove = getUserMove(userInput1, userInput2, userInput3);
		Random random = new Random();
		int bot = random.nextInt(3);
		Janken botMove = getBotMove(bot);
		
		String url = "WEB-INF/views/result.jsp";
		String resultMessage = getResult(userMove, botMove);
		
		// Images/paper.png
		String userMoveStr = "Images/" + userMove.type + ".png";
		String botMoveStr  = "Images/" + botMove.type + ".png";
		
		request.setAttribute("result", resultMessage);
		request.setAttribute("userMove", userMoveStr);	
		request.setAttribute("botMove", botMoveStr);	
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
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
	
	public Janken getUserMove(String r, String c, String p) {
		if (r != null) {
			return Janken.ROCK;
		} else if (c != null) {
			return Janken.SCISSORS;
		} else {
			return Janken.PAPER;
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
