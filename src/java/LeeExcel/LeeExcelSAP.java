package LeeExcel;

import conn.ConectionDB;
import java.io.FileInputStream;
import java.sql.ResultSet;
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
 * @author Indra Hidayatulloh
 */
public class LeeExcelSAP {

    private Vector vectorDataExcelXLSX = new Vector();
    ConectionDB con = new ConectionDB();

    public String obtieneArchivo(String F_Fecha, String path, String file, String usua) {

        String excelXLSXFileName = path + "/exceles/" + file;
        vectorDataExcelXLSX = readDataExcelXLSX(excelXLSXFileName);
        String mensaje = displayDataExcelXLSX(vectorDataExcelXLSX, usua, F_Fecha);
        return mensaje;
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

    public String displayDataExcelXLSX(Vector vectorData, String usua, String F_Fecha) {
        String mensaje = "Se cargó correctamente";
        // Looping every row data in vector

        String F_IndIsem = "";
        String F_ClaProveeAnt = "";
        for (int i = 0; i < vectorData.size(); i++) {
            String F_ClaProvee = "";
            Vector vectorCellEachRowData = (Vector) vectorData.get(i);
            String F_Cant = "";
            String F_ClaPro = "";
            if (!vectorCellEachRowData.get(0).toString().equals("")) {
                String F_NomProvee = (vectorCellEachRowData.get(0).toString() + "").trim();
                try {
                    con.conectar();

                    ResultSet rset = con.consulta("select F_ClaProve from tb_proveedor where F_NomPro='" + F_NomProvee + "'");
                    while (rset.next()) {
                        F_ClaProvee = rset.getString("F_ClaProve");
                    }

                    if (F_ClaProvee.equals("")) {
                        con.insertar("insert into tb_proveedor values (0,'" + F_NomProvee + "','','','','---','---','---','---','---')");
                        rset = con.consulta("select F_ClaProve from tb_proveedor where F_NomPro='" + F_NomProvee + "'");
                        while (rset.next()) {
                            F_ClaProvee = rset.getString("F_ClaProve");
                        }
                    }

                    if (!F_ClaProveeAnt.equals(F_ClaProvee)) {
                        F_IndIsem = IndiceISEM();
                    }

                    F_ClaProveeAnt = F_ClaProvee;
                    con.cierraConexion();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                try {
                    F_ClaPro = ((vectorCellEachRowData.get(1).toString()) + "");
                    DecimalFormat formatter = new DecimalFormat("0000.00");
                    DecimalFormatSymbols custom = new DecimalFormatSymbols();
                    custom.setDecimalSeparator('.');
                    custom.setGroupingSeparator(',');
                    formatter.setDecimalFormatSymbols(custom);
                    try {
                        F_ClaPro = formatter.format(Double.parseDouble(F_ClaPro));
                        String[] punto = F_ClaPro.split("\\.");
                        System.out.println(punto.length);
                        if (punto.length > 1) {
                            System.out.println(F_ClaPro + "***" + punto[0] + "////" + punto[1]);
                            if (punto[1].equals("01")) {
                                F_ClaPro = agrega(punto[0]) + ".01";
                            } else if (punto[1].equals("02")) {
                                F_ClaPro = agrega(punto[0]) + ".02";
                            } else if (punto[1].equals("00")) {
                                F_ClaPro = agrega(punto[0]);
                            } else {
                                F_ClaPro = agrega(punto[0]);
                            }
                            System.out.println(F_ClaPro);
                        }
                    } catch (Exception e) {
                    }

                    //qry = qry + "'" + agrega(ClaPro) + "' , ";
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                F_Cant = (vectorCellEachRowData.get(2).toString() + "").trim();
                //-------------------------------------------------------------

                String qry = "insert into tb_pedidoisem values (0,'" + F_IndIsem + "','" + F_ClaProvee + "','" + F_ClaPro + "','-','-','-','0000-00-00','" + F_Cant + "','',NOW(),'" + F_Fecha + "',CURTIME(),'" + usua + "','1','0')";

                try {
                    con.conectar();
                    try {
                        con.insertar(qry);
                    } catch (Exception e) {
                        mensaje = e.getMessage();
                        System.out.println(e.getMessage());
                    }
                    con.cierraConexion();
                } catch (Exception e) {
                    mensaje = e.getMessage();
                    System.out.println(e.getMessage());
                }
            }
        }
        return mensaje;
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

    public String IndiceISEM() {
        int F_IndIsem = 0;
        try {
            con.conectar();
            ResultSet rset = con.consulta("select F_IndIsem from tb_indice");
            while (rset.next()) {
                F_IndIsem = rset.getInt("F_IndIsem");
            }
            con.insertar("update tb_indice set F_IndIsem = '" + (F_IndIsem + 1) + "' ");
            con.cierraConexion();
        } catch (Exception e) {
        }
        return F_IndIsem + "";
    }

}
