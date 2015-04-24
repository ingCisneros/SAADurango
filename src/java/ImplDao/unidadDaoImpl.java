/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplDao;

import Dao.unidadDao;
import Modelos.Uni;
import conn.ConectionDB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anonimus
 */
public class unidadDaoImpl implements unidadDao{

    ConectionDB con = new ConectionDB();
    
    @Override
    public List<Uni> rutaDiaria() {
        List<Uni> r = new ArrayList<Uni>();
        
        String qry= "SELECT F_ClaCli,F_NomCli FROM tb_uniatn WHERE F_Cons='NORMAL' ORDER BY F_ClaCli;";
        
        try
        {
            con.conectar();
            ResultSet rs = con.consulta(qry);
            while(rs.next())
            {
                Uni u = new Uni();
                u.setClaCLi(rs.getInt("F_ClaCli"));
                u.setNomCli(rs.getString("F_NomCli"));
                r.add(u);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
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
        
        
        return r;
    }

    @Override
    public List<Uni> rutaDiariaDesc(String desc) {
        List<Uni> r = new ArrayList<Uni>();
        
        String qry= "SELECT F_ClaCli,F_NomCli FROM tb_uniatn WHERE F_Cons='NORMAL' AND F_NomCli LIKE '%"+desc+"%' GROUP BY F_ClaCli   ORDER BY F_ClaCli LIMIT 15 ;";
        try
        {
            con.conectar();
            ResultSet rs = con.consulta(qry);
            while(rs.next())
            {
                Uni u = new Uni();
                u.setClaCLi(rs.getInt("F_ClaCli"));
                u.setNomCli(rs.getString("F_NomCli"));
                r.add(u);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return r;
    }

    @Override
    public String tipUni(int uni) {
        String r = "";
        
        String qry= "SELECT F_Cons FROM tb_uniatn WHERE F_ClaCli="+uni+" ";
        try
        {
            con.conectar();
            ResultSet rs = con.consulta(qry);
            while(rs.next())
            {
              r= rs.getString("F_Cons");
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return r;
    }
    
}
