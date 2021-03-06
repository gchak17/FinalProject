package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Account;
import managers.AccountData;
import managers.FriendRequestManager;

/**
 * Servlet implementation class RejectFriendRequestServlet
 */
@WebServlet("/RejectFriendRequestServlet")
public class RejectFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RejectFriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FriendRequestManager friendRequestManager = (FriendRequestManager) getServletContext().getAttribute("friendRequestManager");
		AccountData accountData = (AccountData)getServletContext().getAttribute("accountData");
		Account requestReciever = (Account) request.getSession().getAttribute("user");
		
		Long requestSenderID = Long.parseLong(request.getParameter("requestSenderID"));
		Account requestSender = accountData.getAccountByID(requestSenderID);
		
		friendRequestManager.deleteFriendRequest(requestSender.getID(), requestReciever.getID());
		
		requestSender.removeFriend(requestReciever.getID());
		requestReciever.removeFriend(requestSender.getID());
		
		request.getSession().removeAttribute("requestSender");
		request.getRequestDispatcher("NotificationPage.jsp").forward(request, response);
	}

}
