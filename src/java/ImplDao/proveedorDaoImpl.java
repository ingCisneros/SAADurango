/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplDao;

import Dao.proveedorDao;
import Modelos.Provedor;
import conn.ConectionDB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anonimus
 */
public class proveedorDaoImpl implements proveedorDao{

    ConectionDB con = new ConectionDB();
    @Override
    public List<Provedor> listProve() {
        String query="SELECT F_ClaProve, F_NomPro FROM tb_proveedor ORDER BY F_NomPro ";
        List<Provedor> lp = new ArrayList<Provedor>();
        try 
        {
            con.conectar();
            ResultSet rs = con.consulta(query);
            
            while(rs.next())
            {
                Provedor p = new Provedor();
                p.setFclavePro(rs.getInt("F_ClaProve"));
                p.setNomPro(rs.getString("F_NomPro"));
                lp.add(p);
            }
             con.cierraConexion();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
       
        return lp;
    }
    
}
