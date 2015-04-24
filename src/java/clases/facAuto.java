/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import Modelos.ifq;
import Modelos.invUni;
import conn.ConectionDB;
import java.sql.ResultSet;

/**
 *
 * @author anonimus
 */
public class facAuto {

    ConectionDB con = new ConectionDB();

    public void requerimiento(String claveUni) {

        try {

            con.conectar();

            String qry = "SELECT F_IdUni,F_IdCla,F_Cajas,F_Pz,F_Pres,F_Sts FROM tb_invTemp WHERE F_IdUni='" + claveUni + "' ORDER BY F_IdCla+0 ";
            int cajas=0;
            ResultSet rs = con.consulta(qry);

            while (rs.next()) {
                invUni inv = new invUni();
                inv.setCajas(rs.getInt("F_Cajas"));
                inv.setClaMed(rs.getString("F_IdCla"));
                inv.setClaUNi(rs.getInt("F_IdUni"));
                inv.setPres(rs.getInt("F_Pres"));
                inv.setPz(rs.getInt("F_Pz"));
                inv.setStatus(rs.getInt("F_Sts"));

                if(inv.getPres()>1)
                {
                     int resto = inv.getPz() % inv.getPres();
                    
                     double porcentaje= (resto*100)/inv.getPres();
                     
                     if(porcentaje>60)
                     {
                         inv.setCajas(inv.getCajas()+1);
                     }
                }
                
               
                String ifiq="SELECT F_ClaCli,F_ClaPro,F_Cant FROM tb_ifiq WHERE F_ClaCLi='" + inv.getClaUNi() + "' AND F_ClaPro='" + inv.getClaMed() + "' ";
                ResultSet ifq = con.consulta(ifiq);

                while (ifq.next()) {

                    ifq i = new ifq();
                    i.setClaUni(ifq.getInt("F_ClaCli"));
                    i.setCajas(ifq.getInt("F_Cant"));
                    i.setClave(ifq.getString("F_ClaPro"));
                    
                    
                    cajas= i.getCajas()-inv.getCajas();
                    
                    if(cajas<0)
                    {
                    cajas=0;
                    }
                    
                    String insert ="INSERT INTO tb_unireq (F_ClaUni,F_ClaPro,F_CajasReq,F_Status) VALUES ('"+inv.getClaUNi()+"','"+inv.getClaMed()+"',"+cajas+",0)";
                    con.insertar(insert);
                }

            }
            
            String claFalt="SELECT F_ClaPro,F_Cant FROM tb_ifiq WHERE F_ClaCli="+claveUni+" AND F_Cant<>0  AND F_ClaPro NOT IN (SELECT DISTINCT F_ClaPro FROM tb_unireq WHERE F_Status=0 AND F_ClaUni="+claveUni+");";
            
            ResultSet faltan = con.consulta(claFalt);
            
            while(faltan.next())
            
            {
                con.insertar("INSERT INTO tb_unireq (F_ClaUni,F_ClaPro,F_CajasReq,F_Status) VALUES ('"+claveUni+"','"+faltan.getString("F_ClaPro")+"',"+faltan.getInt("F_Cant")+",0)");
            }
            
            
            con.cierraConexion();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
