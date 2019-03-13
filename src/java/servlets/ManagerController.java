/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.Cover;
import entity.CoverBook;
import entity.History;
import entity.Reader;
import entity.User;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import securitylogic.RoleLogic;
import session.BookFacade;
import session.CoverBookFacade;
import session.CoverFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.UserRolesFacade;
import utils.PagePathLoader;

/**
 *
 * @author jvm
 */
@WebServlet(name = "ManagerController", urlPatterns = {
    "/showListReaders",
    "/showPageForGiveBook",
    "/showPageForReturnBook",
    "/giveBook",
    "/showAddNewBook",
    "/addNewBook",
    "/showAddNewReader",
    "/returnBook",
    "/showUploadFile",
    
})
public class ManagerController extends HttpServlet {
    @EJB private BookFacade bookFacade;
    @EJB private ReaderFacade readerFacade;
    @EJB private HistoryFacade historyFacade;
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private CoverFacade coverFacade;
    @EJB private CoverBookFacade coverBookFacade;
    
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        RoleLogic rl = new RoleLogic();
        Calendar c = new GregorianCalendar();
        
        HttpSession session = request.getSession(false);
        if(session == null){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        User regUser = (User) session.getAttribute("regUser");
        if(regUser == null){
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        Boolean isRole = rl.isRole(RoleLogic.ROLE.MANAGER.toString(), regUser);
        if(!isRole){
            request.setAttribute("info", "Вы должны быть менеджером!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        
        request.setAttribute("role", rl.getRole(regUser));
        
        String path = request.getServletPath();
        
        switch (path) {
            case "/showListReaders":
                List<Reader> listReaders = readerFacade.findAll();
                request.setAttribute("listReaders", listReaders);
                request.setAttribute("info", "showListReaders,привет из сервлета!");
                request.getRequestDispatcher(PagePathLoader.getPagePath("showListReaders")).forward(request, response);
                break;
            case "/showPageForGiveBook":
                List<Book> listBooks = bookFacade.findAll();
                listReaders = readerFacade.findAll();
                request.setAttribute("listBooks", listBooks);
                request.setAttribute("listReaders", listReaders);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showPageForGiveBook")).forward(request, response);
                break;
            case "/giveBook":
                String bookId = request.getParameter("bookId");
                String readerId = request.getParameter("readerId");
                Book book = bookFacade.find(new Long(bookId));
                Reader reader = readerFacade.find(new Long(readerId));
                if(book.getCount()>0){
                    book.setCount(book.getCount()-1);
                    bookFacade.edit(book);
                    History history = new History(book, reader, c.getTime());
                    historyFacade.create(history);
                    request.setAttribute("info", "Книга " + book.getName() + " выдана");
                }else{
                    request.setAttribute("info", "Все книги выданы");
                }       
                request.getRequestDispatcher(PagePathLoader.getPagePath("managerIndex")).forward(request, response);
                break;
            case "/showAddNewBook":
                List<Cover> listCovers = coverFacade.findAll();
                request.setAttribute("listCovers", listCovers);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showAddNewBook")).forward(request, response);
                break;
            case "/addNewBook":
                String name = request.getParameter("name");
                String author = request.getParameter("author");
                String isbn = request.getParameter("isbn");
                String count = request.getParameter("count");
                book = new Book(isbn, name, author, new Integer(count));
                bookFacade.create(book);
                String coverId = request.getParameter("coverId");
                Cover cover = coverFacade.find(new Long(coverId));
                CoverBook coverBook = new CoverBook(book, cover);
                coverBookFacade.create(coverBook);
                request.setAttribute("info", "Книга \""+book.getName()+"\"добавлена");
                request.getRequestDispatcher(PagePathLoader.getPagePath("managerIndex")).forward(request, response);
                break;
            case "/showAddNewReader":
                request.getRequestDispatcher(PagePathLoader.getPagePath("showAddNewReader")).forward(request, response);
                break;
            case "/showPageForReturnBook":
                List<History> listHistories = historyFacade.findGivenBooks();
                request.setAttribute("listHistories", listHistories);
                request.getRequestDispatcher(PagePathLoader.getPagePath("showReturnBook")).forward(request, response);
                break;
            case "/returnBook":
                String historyId = request.getParameter("returnHistoryId");
                History history = historyFacade.find(new Long(historyId));
                if(history == null){
                    request.setAttribute("info", "Такой книги не выдавалось");
                    request.getRequestDispatcher(PagePathLoader.getPagePath("managerIndex")).forward(request, response);
                    return;
                }       
                book = history.getBook();
                if(book.getQuantity()>book.getCount()){
                    book.setCount(book.getCount()+1);
                    bookFacade.edit(book);
                    history.setDateEnd(c.getTime());
                    historyFacade.edit(history);
                    request.setAttribute("info", "Книга "+book.getName()+" возвращена");
                }else{
                    request.setAttribute("info", "Все книги уже возвращены");
                }       
                request.getRequestDispatcher(PagePathLoader.getPagePath("managerIndex")).forward(request, response);
                break;
            case "/showUploadFile":
                request.getRequestDispatcher(PagePathLoader.getPagePath("showUploadFile")).forward(request, response);
                break;
            default:   
                request.setAttribute("info", "Нет такой странички");
                request.getRequestDispatcher(PagePathLoader.getPagePath("managerIndex")).forward(request, response);
        }
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
