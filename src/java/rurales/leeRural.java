/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rurales;

import conn.ConectionDB;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Vector;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author anonimus
 */
public class leeRural {

    private Vector vectorDataExcelXLSX = new Vector();
    ConectionDB con = new ConectionDB();

    public boolean obtieneArchivo(String path, String file) {
        String excelXLSXFileName = path + "/exceles/" + file;
        vectorDataExcelXLSX = readDataExcelXLSX(excelXLSXFileName);
        displayDataExcelXLSX(vectorDataExcelXLSX);
        return true;
    }

    public Vector readDataExcelXLSX(String fileName) {
        Vector vectorData = new Vector();

        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);

            XSSFWorkbook xssfWorkBook = new XSSFWorkbook(fileInputStream);

            // Read data at sheet 0
            XSSFSheet xssfSheet = xssfWorkBook.getSheetAt(0);

            Iterator rowIteration = xssfSheet.rowIterator();

            // Looping every row at sheet 0
            while (rowIteration.hasNext()) {
                XSSFRow xssfRow = (XSSFRow) rowIteration.next();
                Iterator cellIteration = xssfRow.cellIterator();

                Vector vectorCellEachRowData = new Vector();

                // Looping every cell in each row at sheet 0
                while (cellIteration.hasNext()) {
                    XSSFCell xssfCell = (XSSFCell) cellIteration.next();
                    vectorCellEachRowData.addElement(xssfCell);
                }

                vectorData.addElement(vectorCellEachRowData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return vectorData;
    }

    public void displayDataExcelXLSX(Vector vectorData) {
        // Looping every row data in vector
        String claveUni = "";

        for (int i = 0; i < 1; i++) {
            Vector vectorCellEachRowData = (Vector) vectorData.get(i);
            for (int j = 0; j < 1; j++) {
                try {
                    String Clave = (vectorCellEachRowData.get(j).toString() + "").trim();
                    NumberFormat formatter = new DecimalFormat("0000");
                    Clave = formatter.format(Double.parseDouble(Clave));
                    claveUni = Clave;
                } catch (Exception e) {
                }
            }
        }
        try {
            con.conectar();
            try {
                con.insertar("DELETE FROM tb_invTemp WHERE F_IdUni='" + claveUni + "'");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            con.cierraConexion();
        } catch (Exception e) {
        }
        for (int i = 0; i < vectorData.size(); i++) {
            Vector vectorCellEachRowData = (Vector) vectorData.get(i);
            String qry = "insert into tb_unireq values (";
            // looping every cell in each row
            for (int j = 0; j < 3; j++) {

                if (j == 0) {
                    try {
                        String Clave = (vectorCellEachRowData.get(j).toString() + "").trim();
                        NumberFormat formatter = new DecimalFormat("0000");
                        Clave = formatter.format(Double.parseDouble(Clave));
                        /*System.out.println(Clave);
                         Clave.replaceAll("^\\s*", "");
                         Clave.replaceAll(" ", "");
                         Clave.replaceAll("&nbsp;", "");
                         for (int x = 0; x < Clave.length(); x++) {
                         System.out.println(Clave.charAt(x) + " = " + Clave.codePointAt(x));
                         };*/
                        qry = qry + "'" + Clave + "', ";
                    } catch (Exception e) {
                    }
                } else if (j == 1) {

                    try {
                        String ClaPro = ((vectorCellEachRowData.get(j).toString()) + "");
                        DecimalFormat formatter = new DecimalFormat("0000.00");
                        DecimalFormatSymbols custom = new DecimalFormatSymbols();
                        custom.setDecimalSeparator('.');
                        custom.setGroupingSeparator(',');
                        formatter.setDecimalFormatSymbols(custom);
                        String[] punto1 = ClaPro.split("\\.");
                        int alfa = 0;

                        for (int u = 0; u < punto1.length; u++) {
                            alfa++;
                        }

                        if (alfa == 2) {

                            ClaPro = formatter.format(Double.parseDouble(ClaPro));
                            String[] punto = ClaPro.split("\\.");
                            System.out.println(punto.length);
                            if (punto.length > 1) {
                                System.out.println(ClaPro + "***" + punto[0] + "////" + punto[1]);
                                if (punto[1].equals("01")) {
                                    ClaPro = agrega(punto[0]) + ".01";
                                } else if (punto[1].equals("02")) {
                                    ClaPro = agrega(punto[0]) + ".02";
                                } else if (punto[1].equals("00")) {
                                    ClaPro = agrega(punto[0]);
                                } else {
                                    ClaPro = agrega(punto[0]);
                                }
                                System.out.println(ClaPro);
                            }
                        }
                        qry = qry + "'" + agrega(ClaPro) + "' ,0, ";

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (j == 2) {
                    try {
                        String Pz = ((int) Double.parseDouble(vectorCellEachRowData.get(j).toString()) + "");
                        qry = qry + "'" + Pz.trim() + "' , ";
                    } catch (Exception e) {
                    }
                } 
            }
            qry = qry + "0,0)"; // agregar campos fuera del excel
            try {
                con.conectar();
                try {
                    con.insertar(qry);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                con.cierraConexion();
            } catch (Exception e) {
            }
        }
        
        

    }

    public String agrega(String clave) {
        String clave2 = "";
        if (clave.length() < 4) {

            if (!clave.substring(0, 1).equals("0")) {
                //System.out.println(clave);
                if (clave.length() == 1) {
                    clave2 = ("000" + clave);
                }
                if (clave.length() == 2) {
                    clave2 = ("00" + clave);
                }
                if (clave.length() >= 3) {
                    clave2 = ("0" + clave);
                }

            }
        } else {
            clave2 = clave;
        }
        return clave2;
    }

}
