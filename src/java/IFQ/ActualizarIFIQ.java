/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IFQ;

import conn.ConectionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Americo
 */
public class ActualizarIFIQ extends HttpServlet {

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
        ConectionDB con = new ConectionDB();
        try {
            if (request.getParameter("accion").equals("Actualizar")) {
                con.conectar();
                ResultSet rset = con.consulta("select F_ClaPro, F_DesPro from tb_medica");
                while (rset.next()) {
                    int ban1 = 0;
                    String F_IdIFIQ = "";
                    ResultSet rset2 = con.consulta("select F_Id from tb_ifiq where F_ClaPro = '" + rset.getString("F_ClaPro") + "' and F_ClaCli = '" + request.getParameter("F_ClaCli") + "'");
                    while (rset2.next()) {
                        ban1 = 1;
                        F_IdIFIQ = rset2.getString("F_Id");
                    }
                    if (request.getParameter(rset.getString("F_ClaPro")) != null) {
                        if (ban1 == 0) {
                            con.insertar("insert into tb_ifiq values ('" + request.getParameter("F_ClaCli") + "','" + rset.getString("F_ClaPro") + "','" + request.getParameter(rset.getString("F_ClaPro")) + "','0')");
                        } else {
                            con.insertar("update tb_ifiq set F_Cant = '" + request.getParameter(rset.getString("F_ClaPro")) + "'  where F_Id='" + F_IdIFIQ + "'");
                        }
                    }
                }
                con.cierraConexion();
                response.sendRedirect("facturacion/adminIFIQ.jsp");
                /*response.setContentType("text/html");
                request.setAttribute("F_ClaCli", request.getParameter("F_ClaCli"));
                request.getRequestDispatcher("facturacion/adminIFIQ.jsp").forward(request, response);*/
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
