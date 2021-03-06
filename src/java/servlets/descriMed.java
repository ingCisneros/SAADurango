/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Dao.descDao;
import ImplDao.descDaoImpl;
import Modelos.med;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

/**
 *
 * @author anonimus
 */
public class descriMed extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet descriMed</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet descriMed at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
         PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

        String query = request.getParameter("term");

        try {
            query = query.toLowerCase();
            descDao u = new descDaoImpl();
            List<med> lUni = new ArrayList<med>();
            lUni = (List) u.byDes(query);
            med Med = new med();
            JSONArray arrayObj = new JSONArray();
            System.out.println(query);

            for (int i = 0; i < lUni.size(); i++) {
                Med = (med) lUni.get(i);

                String medicamento = Med.getDesc().toLowerCase();
                if (medicamento.contains(query)) {
                    arrayObj.add(Med.getDesc());
                }
            }

            out.println(arrayObj.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession(true);
        int bandera = Integer.parseInt(request.getParameter("ban"));

        switch (bandera) 
        {
            case 1:
                String descripcion = request.getParameter("descri");
                Map dv = new LinkedHashMap();
                try {
                    descDao cl = new descDaoImpl();
                    List<med> l_a = new ArrayList<med>();
                    l_a = (List) cl.bydDescClave(descripcion);
                    med Med = new med();
                    for (int i = 0; i < l_a.size(); i++) {
                        Med = (med) l_a.get(i);
                        dv.put("clave", Med.getClave());
                        dv.put("descrip", Med.getDesc());

                    }
                    String json1 = null;
                    json1 = new Gson().toJson(dv);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json1);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            break;    
            
        }
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
