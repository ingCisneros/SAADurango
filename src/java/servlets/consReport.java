/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Dao.consDao;
import ImplDao.consDaoImpl;
import Modelos.cons;
import clases.unirPdf;
import conn.ConectionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author anonimus
 */
public class consReport extends HttpServlet {

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
            out.println("<title>Servlet consReport</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet consReport at " + request.getContextPath() + "</h1>");
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

        int bandera = Integer.parseInt(request.getParameter("ban"));

        switch (bandera) {
            case 1:
                String pad = getServletContext().getRealPath("/ConsPdf/");
                System.out.println(pad);
                FileInputStream ficheroInput = null;
                File dir = new File(pad + "complete.pdf");

                int tamanoInput = 0;
                byte[] datosPDF = null;

                ficheroInput = new FileInputStream(dir);
                tamanoInput = ficheroInput.available();
                datosPDF = new byte[tamanoInput];
                ficheroInput.read(datosPDF, 0, tamanoInput);
                response.setHeader("Content-disposition", "inline; filename=ConcentradoCompleto.pdf");
                response.setContentType("application/pdf");
                response.setContentLength(tamanoInput);
                response.getOutputStream().write(datosPDF);

                ficheroInput.close();
                break;

            case 2:
                 pad = getServletContext().getRealPath("/ConsPdf/");
                System.out.println(pad);
                ficheroInput = null;
                 dir = new File(pad + "completeFec.pdf");

                 tamanoInput = 0;
                 datosPDF = null;

                ficheroInput = new FileInputStream(dir);
                tamanoInput = ficheroInput.available();
                datosPDF = new byte[tamanoInput];
                ficheroInput.read(datosPDF, 0, tamanoInput);
                response.setHeader("Content-disposition", "inline; filename=Concentrado.pdf");
                response.setContentType("application/pdf");
                response.setContentLength(tamanoInput);
                response.getOutputStream().write(datosPDF);

                ficheroInput.close();
                break;
                 case 3:
                 pad = getServletContext().getRealPath("/ConsPdf/");
                System.out.println(pad);
                ficheroInput = null;
                 dir = new File(pad + "completeUni.pdf");

                 tamanoInput = 0;
                 datosPDF = null;

                ficheroInput = new FileInputStream(dir);
                tamanoInput = ficheroInput.available();
                datosPDF = new byte[tamanoInput];
                ficheroInput.read(datosPDF, 0, tamanoInput);
                response.setHeader("Content-disposition", "inline; filename=Concentrado.pdf");
                response.setContentType("application/pdf");
                response.setContentLength(tamanoInput);
                response.getOutputStream().write(datosPDF);

                ficheroInput.close();
                break;
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

        response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("application/pdf");

        int bandera = Integer.parseInt(request.getParameter("ban"));

        switch (bandera) {
            case 1:
                ServletOutputStream out = response.getOutputStream();

                List<cons> c = new ArrayList<cons>();
                List<InputStream> pdfs = new ArrayList<InputStream>();
                consDao cons = new consDaoImpl();

                ConectionDB con = new ConectionDB();
                Connection conexion;
                String pad = "";
                String range = "";
                String [] folNew = request.getParameterValues("folios[]");
                int longitud = folNew.length;
                try {

                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    conexion = con.getConn();

                    c = cons.diario();

                    cons C = new cons();

                    for (int i = 0; i < longitud; i++) {

                       
                        
                        JasperReport reporte = (JasperReport) JRLoader.loadObject(getServletContext().getRealPath("/reportes/concentradoGlobal.jasper"));
                        
                        Map parameters = new HashMap();
                        parameters.put("idFact", folNew[i]);
                        System.out.println(folNew[i]);
                        pad = getServletContext().getRealPath("/ConsPdf/");
                        System.out.println(pad);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, pad + "Folio-" +folNew[i]+ "");
                        range = pad;
                        pdfs.add(new FileInputStream(pad + "Folio-" + folNew[i] + ""));

                    }

                    OutputStream output = new FileOutputStream(range + "complete.pdf");

                    unirPdf.concatPDFs(pdfs, output, true);

                    for (int o = 0; o < longitud; o++) {
                        System.out.println(folNew[o]);
                       

                        pad = getServletContext().getRealPath("/ConsPdf/");

                        File archivo = new File(pad + "Folio-" + folNew[o] + "");
                        archivo.delete();
                        
                        
                    }

                } catch (Exception e) {
                   System.out.println(e.getMessage()); 
                }
                break;

            case 2:
                out = response.getOutputStream();
                String date = request.getParameter("date");
                c = new ArrayList<cons>();
                pdfs = new ArrayList<InputStream>();
                cons = new consDaoImpl();

                con = new ConectionDB();

                pad = "";
                range = "";
                try {

                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    conexion = con.getConn();

                    c = cons.listFec(date);

                    cons C = new cons();

                    for (int i = 0; i < c.size(); i++) {

                        C = (cons) c.get(i);

                        JasperReport reporte = (JasperReport) JRLoader.loadObject(getServletContext().getRealPath("/reportes/concentradoGlobal.jasper"));

                        Map parameters = new HashMap();
                        parameters.put("idFact", C.getIdFac());

                        pad = getServletContext().getRealPath("/ConsPdf/");
                        System.out.println(pad);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, pad + "Folio-" + C.getIdFac() + "");
                        range = pad;
                        pdfs.add(new FileInputStream(pad + "Folio-" + C.getIdFac() + ""));

                    }

                    OutputStream output = new FileOutputStream(range + "completeFec.pdf");

                    unirPdf.concatPDFs(pdfs, output, true);

                    for (int o = 0; o < c.size(); o++) {

                        C = (cons) c.get(o);

                        pad = getServletContext().getRealPath("/ConsPdf/");

                        File archivo = new File(pad + "Folio-" + C.getIdFac() + "");
                        archivo.delete();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                out = response.getOutputStream();
                int uni = Integer.parseInt(request.getParameter("uni"));
                c = new ArrayList<cons>();
                pdfs = new ArrayList<InputStream>();
                cons = new consDaoImpl();

                con = new ConectionDB();

                pad = "";
                range = "";
                try {

                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    conexion = con.getConn();

                    c = cons.ByUni(uni);

                    cons C = new cons();

                    for (int i = 0; i < c.size(); i++) {

                        C = (cons) c.get(i);

                        JasperReport reporte = (JasperReport) JRLoader.loadObject(getServletContext().getRealPath("/reportes/concentradoGlobal.jasper"));

                        Map parameters = new HashMap();
                        parameters.put("idFact", C.getIdFac());

                        pad = getServletContext().getRealPath("/ConsPdf/");
                        System.out.println(pad);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, conexion);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, pad + "Folio-" + C.getIdFac() + "");
                        range = pad;
                        pdfs.add(new FileInputStream(pad + "Folio-" + C.getIdFac() + ""));

                    }

                    OutputStream output = new FileOutputStream(range + "completeUni.pdf");

                    unirPdf.concatPDFs(pdfs, output, true);

                    for (int o = 0; o < c.size(); o++) {

                        C = (cons) c.get(o);

                        pad = getServletContext().getRealPath("/ConsPdf/");

                        File archivo = new File(pad + "Folio-" + C.getIdFac() + "");
                        archivo.delete();

                    }

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
