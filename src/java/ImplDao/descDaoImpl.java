/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplDao;

import Dao.descDao;
import Modelos.med;
import conn.ConectionDB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anonimus
 */
public class descDaoImpl implements descDao{

    ConectionDB con = new ConectionDB();
    @Override
    public List<med> byDes(String desc) {
       
        List<med> m = new ArrayList<med>();
        
        try 
        {
            con.conectar();
            String qry="SELECT F_DesPro FROM tb_medica WHERE F_DesPro LIKE '%"+desc+"%' GROUP BY F_ClaPro   ORDER BY F_ClaPro LIMIT 15; ";
            
            ResultSet rs= con.consulta(qry);
            
            while(rs.next())
            {
                med Med = new med();
                Med.setDesc(rs.getString("F_DesPro"));
                m.add(Med);
            }
            
            
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
        finally
        {
            try 
            {
                con.cierraConexion();
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        
        return m;
    }

    @Override
    public List<med> bydDescClave(String desc) {
       List<med> m = new ArrayList<med>();
       
        try 
        {
            con.conectar();
            
            String qry="SELECT F_ClaPro, F_DesPro,F_StsPro,F_TipMed,F_Costo,F_PrePro FROM tb_medica WHERE F_DesPro='"+desc+"' ";
            
            ResultSet rs = con.consulta(qry);
            
            while(rs.next())
            {
                med Med = new med();
                Med.setClave(rs.getString("F_ClaPro"));
                Med.setDesc(rs.getString("F_DesPro"));
                Med.setEstatus(rs.getString("F_StsPro"));
                m.add(Med);
            }
            
            
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try 
            {
                con.cierraConexion();
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
       
       return m;
    }
    
}
