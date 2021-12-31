/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;


import com.ipn.mx.modelo.dto.GraficaDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Villena
 */
public class GraficaDAO {

    private Connection conexion;

    private static final String SQL_GRAFICAR = "select nombreCategoria, count(*) as cantidad from Categoria inner join Producto on Categoria.idcategoria = Producto.idcategoria group by nombrecategoria;";

    private void obtenerConexion() {
        String user = "dpifiuofzznrqg";
        String password = "db19a127a6818ed8d62744c4628df77dfa48f6c2045cb0fe19b00cecdc807275";
        String url = "jdbc:postgresql://ec2-52-70-205-234.compute-1.amazonaws.com/dd7779501rsogd";
        String driverPostgreSql = "org.postgresql.Driver";

        try {
            Class.forName(driverPostgreSql);
            conexion = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public List obtenerDatosGrafica() throws SQLException {
        obtenerConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try {
            ps = conexion.prepareStatement(SQL_GRAFICAR);
            rs = ps.executeQuery();
            while(rs.next()){
                GraficaDTO dto = new GraficaDTO();
                dto.setNombreCategoriaString(rs.getString("nombreCategoria"));
                dto.setCantidad(rs.getInt("cantidad"));
                list.add(dto);
            }
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }

        return list;
    }

    public static void main(String[] args) {
        GraficaDAO dao = new GraficaDAO();
        try {
            System.out.println(dao.obtenerDatosGrafica());
        } catch (SQLException ex) {
            Logger.getLogger(GraficaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
