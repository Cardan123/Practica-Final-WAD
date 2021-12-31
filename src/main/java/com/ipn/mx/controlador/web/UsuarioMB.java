/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.ipn.mx.controlador.web;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.entidades.Categoria;
import com.ipn.mx.modelo.entidades.Usuario;
import com.ipn.mx.utilerias.EnviarMail;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author cardan_mac
 */
@ManagedBean(name = "usuarioMB")
@SessionScoped
public class UsuarioMB extends BaseBean implements Serializable {

    private final UsuarioDAO dao = new UsuarioDAO();

    private Usuario dto;
    private List<Usuario> listaCategorias;

    public UsuarioMB() {
    }

    public Usuario getDto() {
        return dto;
    }

    public void setDto(Usuario dto) {
        this.dto = dto;
    }

    public List<Usuario> getListaUsuarios() {
        return listaCategorias;
    }

    public void setListaUsuarios(List<Usuario> listaProductos) {
        this.listaCategorias = listaProductos;
    }

    @PostConstruct
    public void init() {
        listaCategorias = new ArrayList<>();
        System.out.println(dao.readAll());
        listaCategorias = dao.readAll();
    }

    public String prepareAdd() {
        dto = new Usuario();
        setAccion(ACC_CREAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/usuario/usuarioForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/usuario/listadoUsuarios?faces-redirect=true";
    }

    public boolean validate() {
        boolean valido = true;
        //Aqui les toca a ustedes poner todas las reglas de validacion 

        return valido;
    }

    public String add() {
        boolean valido = validate();
        if (valido) {
            dao.create(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareAdd();
            }
        }
        return prepareAdd();
    }

    public String update() {
        boolean valido = validate();
        EnviarMail mail = new EnviarMail();
        mail.enviarCorreo(dto.getEmail(), "Usuario Actualizado", "Usuario Actualizado Correctamente");
        
        if (valido) {
            dao.update(dto);
            if (valido) {
                return prepareIndex();
            } else {
                return prepareUpdate();
            }
        }
        return prepareUpdate();
    }

    public String delete() {
        dao.delete(dto);
        return prepareIndex();
    }

    public void seleccionarUsuario(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Usuario();
        dto.setIdUsuario(Integer.parseInt(claveSeleccionada));
        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporteUsuario() {
        String user = "dpifiuofzznrqg";
        String password = "db19a127a6818ed8d62744c4628df77dfa48f6c2045cb0fe19b00cecdc807275";
        String url = "jdbc:postgresql://ec2-52-70-205-234.compute-1.amazonaws.com/dd7779501rsogd";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteUsuarios.jasper");

        try {
            Class.forName(driverPostgreSql);
            Connection conexion = DriverManager.getConnection(url, user, password);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(jasperPath);
//            System.out.println(reporte.getPath());
            byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), null, conexion);

            response.setContentType("application/pdf");
            response.setContentLength(b.length);

            sos.write(b, 0, b.length);
            sos.flush();
            sos.close();
            FacesContext.getCurrentInstance().responseComplete();
            conexion.close();
        } catch (IOException | ClassNotFoundException | JRException | SQLException ex) {
            Logger.getLogger(CategoriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void reporteUsuarioOne() {
        String user = "dpifiuofzznrqg";
        String password = "db19a127a6818ed8d62744c4628df77dfa48f6c2045cb0fe19b00cecdc807275";
        String url = "jdbc:postgresql://ec2-52-70-205-234.compute-1.amazonaws.com/dd7779501rsogd";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteUsuarioOne.jasper");

        try {
            Class.forName(driverPostgreSql);
            Connection conexion = DriverManager.getConnection(url, user, password);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(jasperPath);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", dto.getIdUsuario());
            byte[] b = JasperRunManager.runReportToPdf(reporte.getPath(), parameters, conexion);

            response.setContentType("application/pdf");
            response.setContentLength(b.length);

            sos.write(b, 0, b.length);
            sos.flush();
            sos.close();
            FacesContext.getCurrentInstance().responseComplete();
            conexion.close();

        } catch (IOException | ClassNotFoundException | JRException | SQLException ex) {
            Logger.getLogger(CategoriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
