/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.co.sneh.controller;

import LeeExcel.LeeExcelSAP;
import in.co.sneh.model.SubeOrdenSAP;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.apache.commons.fileupload.FileItemIterator;
import javax.servlet.http.HttpSession;
import conn.*;

/**
 *
 * @author CEDIS TOLUCA3
 */
public class SubeOrdenSapServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConectionDB con = new ConectionDB();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String F_Fecha = "";
        String mensaje = "";
        LeeExcelSAP lee = new LeeExcelSAP();

        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        if (isMultiPart) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                HttpSession sesion = request.getSession(true);
                FileItemIterator itr = upload.getItemIterator(request);
                while (itr.hasNext()) {
                    FileItemStream item = itr.next();
                    if (item.isFormField()) {
                        String fielName = item.getFieldName();
                        InputStream is = item.openStream();
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        String value = new String(b);
                        System.out.println(fielName + ":" + value + "");
                        if (fielName.equals("F_Fecha")) {
                            F_Fecha = value;
                        }
                    } else {
                        String path = getServletContext().getRealPath("/");
                        if (SubeOrdenSAP.processFile(path, item)) {
                            //response.getWriter().println("file uploaded successfully");
                            mensaje = lee.obtieneArchivo(F_Fecha, path, item.getName(), (String) sesion.getAttribute("nombre"));
                            out.println("<script>alert('" + mensaje + "')</script>");

                            //response.sendRedirect("cargaFotosCensos.jsp");
                        } else {
                            out.println("<script>alert('" + mensaje + "')</script>");
                            //response.getWriter().println("file uploading falied");
                            //response.sendRedirect("cargaFotosCensos.jsp");
                        }
                    }
                }
            } catch (FileUploadException fue) {
                out.println("<script>alert('" + mensaje + "')</script>");
                fue.printStackTrace();
            }
            //response.sendRedirect("carga.jsp");
        }

        out.println("<script>window.location='SAP/cargarOrdenCompra.jsp'</script>");
        /*
         * Para insertar el excel en tablas
         */
    }
}
