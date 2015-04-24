/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplDao;

import Dao.consDao;
import Modelos.cons;
import conn.ConectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anonimus
 */
public class consDaoImpl implements consDao{

    ConectionDB con = new ConectionDB();
    
    @Override
    public List<cons> diario() {
        List<cons> facTemp = new ArrayList<cons>();
            
        try 
        {
            con.conectar();
            
            String qry="SELECT f.F_IdFact, u.F_NomCli, f.F_FecEnt FROM tb_facttemp f, tb_uniatn u WHERE u.F_ClaCli=f.F_ClaCli AND f.F_TipUni='NORMAL' AND f.F_StsFact=0 GROUP BY f.F_IdFact ;";
            
            ResultSet rs = con.consulta(qry);
            
            while(rs.next())
            {
               cons c = new cons(); 
               c.setNomCli(rs.getString("u.F_NomCli"));
               c.setIdFac(rs.getInt("f.F_IdFact"));
               c.setDate(rs.getString("f.F_FecEnt"));
               facTemp.add(c);
            }
            
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                con.cierraConexion();
            } catch (SQLException ex) {
                Logger.getLogger(consDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        
        return facTemp;
    }

    @Override
    public List<cons> listFec(String date) {
         List<cons> facTemp = new ArrayList<cons>();
            
        try 
        {
            con.conectar();
            
            String qry="SELECT f.F_IdFact, u.F_NomCli, f.F_FecEnt FROM tb_facttemp f, tb_uniatn u WHERE u.F_ClaCli=f.F_ClaCli AND f.F_TipUni='NORMAL' AND f.F_FecEnt='"+date+"'   AND f.F_StsFact=0 GROUP BY f.F_IdFact ;";
            
            ResultSet rs = con.consulta(qry);
            
            while(rs.next())
            {
               cons c = new cons(); 
               c.setNomCli(rs.getString("u.F_NomCli"));
               c.setIdFac(rs.getInt("f.F_IdFact"));
               c.setDate(rs.getString("f.F_FecEnt"));
               facTemp.add(c);
            }
            
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                con.cierraConexion();
            } catch (SQLException ex) {
                Logger.getLogger(consDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        
        return facTemp;
    }

    @Override
    public boolean delete(String fol, String nombre) {
        boolean deleted = false;
        
        try 
        {
            con.conectar();
            
            String qry="select * from tb_facttemp where F_IdFact = '" +fol+ "'";
            ResultSet rs = con.consulta(qry);
            while(rs.next())
            {
              
                con.insertar("insert into tb_facttemp_elim values ('" + rs.getString(1) + "','" + rs.getString(2) + "','" + rs.getString(3) + "','" + rs.getString(4) + "','" + rs.getString(5) + "','" + rs.getString(6) + "',0, '" + nombre + "', NOW())");
                    
            }
            
            con.insertar("DELETE FROM tb_facttemp WHERE F_IdFact='"+fol+"'");
            deleted=true;
            
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
                System.out.println(e.getMessage());
            }
        }
        
        
        
        return deleted;
    }

    @Override
    public List<cons> ByUni(int uni) {
          List<cons> facTemp = new ArrayList<cons>();
            
        try 
        {
            con.conectar();
            
            String qry="SELECT f.F_IdFact, u.F_NomCli, f.F_FecEnt FROM tb_facttemp f, tb_uniatn u WHERE u.F_ClaCli=f.F_ClaCli AND f.F_TipUni='NORMAL' AND f.F_ClaCli="+uni+"   AND f.F_StsFact=0 GROUP BY f.F_IdFact ;";
            
            ResultSet rs = con.consulta(qry);
            
            while(rs.next())
            {
               cons c = new cons(); 
               c.setNomCli(rs.getString("u.F_NomCli"));
               c.setIdFac(rs.getInt("f.F_IdFact"));
               c.setDate(rs.getString("f.F_FecEnt"));
               facTemp.add(c);
            }
            
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                con.cierraConexion();
            } catch (SQLException ex) {
                Logger.getLogger(consDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        
        return facTemp;
    }
    
}
