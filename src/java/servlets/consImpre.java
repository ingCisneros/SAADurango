/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Dao.consDao;
import ImplDao.consDaoImpl;
import Modelos.cons;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author anonimus
 */
public class consImpre extends HttpServlet {

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
            out.println("<title>Servlet consImpre</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet consImpre at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        int bandera = Integer.parseInt(request.getParameter("ban"));

        switch (bandera) {
            case 1:
                
                JSONObject json = new JSONObject();
                JSONArray jsona = new JSONArray();

                consDao c = new consDaoImpl();

                List<cons> facTemp = new ArrayList<cons>();
                facTemp = c.diario();
                for (int i = 0; i < facTemp.size(); i++) {

                    cons co = new cons();
                    co = (cons) facTemp.get(i);

                    json.put("fol", co.getIdFac());
                    json.put("unidad", co.getNomCli());
                    json.put("entrega", co.getDate());

                    jsona.add(json);
                    json = new JSONObject();

                }
                out.print(jsona);
                out.close();
                   
            break;    
            case 2:

                String date = request.getParameter("fecha");
                 json = new JSONObject();
                 jsona = new JSONArray();

                c = new consDaoImpl();

                facTemp = new ArrayList<cons>();
                facTemp = c.listFec(date);
                for (int i = 0; i < facTemp.size(); i++) {

                    cons co = new cons();
                    co = (cons) facTemp.get(i);

                    json.put("fol", co.getIdFac());
                    json.put("unidad", co.getNomCli());
                    json.put("entrega", co.getDate());

                    jsona.add(json);
                    json = new JSONObject();

                }
                out.print(jsona);
                out.close();
                break;

            case 3:
                    
               String fol= request.getParameter("fol");
               String nombre = request.getParameter("name");
               
               c= new consDaoImpl();
               
               boolean Deleted = c.delete(fol, nombre);
               
                              
                
            break;
                
            case 4:
                
                int claUni = Integer.parseInt(request.getParameter("uni"));
                
                 json = new JSONObject();
                 jsona = new JSONArray();

                c = new consDaoImpl();

                facTemp = new ArrayList<cons>();
                facTemp = c.ByUni(claUni);
                for (int i = 0; i < facTemp.size(); i++) {

                    cons co = new cons();
                    co = (cons) facTemp.get(i);

                    json.put("fol", co.getIdFac());
                    json.put("unidad", co.getNomCli());
                    json.put("entrega", co.getDate());

                    jsona.add(json);
                    json = new JSONObject();

                }
                out.print(jsona);
                out.close();
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
