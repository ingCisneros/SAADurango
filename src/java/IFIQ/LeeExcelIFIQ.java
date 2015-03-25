package IFIQ;

import conn.ConectionDB;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
public class LeeExcelIFIQ {

    private Vector vectorDataExcelXLSX = new Vector();
    ConectionDB con = new ConectionDB();

    public boolean obtieneArchivo(String path, String file) {
        String excelXLSXFileName = path + "/IFIQ/" + file;
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

        for (int i = 0; i < vectorData.size(); i++) {
            Vector vectorCellEachRowData = (Vector) vectorData.get(i);

            DecimalFormat formatter = new DecimalFormat("0000");
            String F_ClaCli = (vectorCellEachRowData.get(0).toString() + "").trim();
            F_ClaCli = formatter.format(Double.parseDouble(F_ClaCli));

            String ClaPro = ((vectorCellEachRowData.get(1).toString()) + "");
            formatter = new DecimalFormat("0000.00");
            DecimalFormatSymbols custom = new DecimalFormatSymbols();
            custom.setDecimalSeparator('.');
            custom.setGroupingSeparator(',');
            formatter.setDecimalFormatSymbols(custom);
            ClaPro = formatter.format(Double.parseDouble(ClaPro));
            String[] punto = ClaPro.split("\\.");
            System.out.println(punto.length);
            if (punto.length > 1) {
                if (punto[1].equals("01")) {
                    ClaPro = agrega(punto[0]) + ".01";
                } else if (punto[1].equals("02")) {
                    ClaPro = agrega(punto[0]) + ".02";
                } else if (punto[1].equals("00")) {
                    ClaPro = agrega(punto[0]);
                } else {
                    ClaPro = agrega(punto[0]);
                }
            }
            //qry = qry + "'" + agrega(ClaPro) + "' , ";

            String Cant = ((int) Double.parseDouble(vectorCellEachRowData.get(2).toString()) + "");
            //qry = qry + "'" + Clave.trim() + "' ";

            String qry = "insert into tb_ifiq values ('" + F_ClaCli + "','" + agrega(ClaPro) + "','" + Cant.trim() + "')";
            String qryUpdate = "update tb_ifiq set F_Cant = '" + Cant.trim() + "' where F_ClaPro = '" + agrega(ClaPro) + "' and F_ClaCli = '" + F_ClaCli + "'";
            // looping every cell in each row

            try {
                System.out.println(qry);
                con.conectar();
                try {
                    con.insertar(qry);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    con.insertar(qryUpdate);
                } catch (SQLException e) {
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
