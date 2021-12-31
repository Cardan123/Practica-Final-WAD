/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.controlador.web;

import static com.ipn.mx.controlador.web.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.controlador.web.BaseBean.ACC_CREAR;
import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dao.GraficaDAO;
import com.ipn.mx.modelo.dto.GraficaDTO;
import com.ipn.mx.modelo.entidades.Categoria;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author Carlos Villena
 */
@ManagedBean(name = "categoriaMB")
@SessionScoped
public class CategoriaMB extends BaseBean implements Serializable {

    private final CategoriaDAO dao = new CategoriaDAO();

    private Categoria dto;
    private List<Categoria> listaCategorias;

    /**
     * Creates a new instance of CategoriaMB
     */
    public CategoriaMB() {
    }

    public Categoria getDto() {
        return dto;
    }

    public void setDto(Categoria dto) {
        this.dto = dto;
    }

    public List<Categoria> getListaCategorias() {
        
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaProductos) {
        this.listaCategorias = listaProductos;
    }

    @PostConstruct
    public void init() {
        listaCategorias = new ArrayList<>();
        listaCategorias = dao.readAll();
    }

    public String prepareAdd() {
        dto = new Categoria();
        setAccion(ACC_CREAR);
        return "/categoria/categoriaForm?faces-redirect=true";
    }

    public String prepareUpdate() {
        setAccion(ACC_ACTUALIZAR);
        return "/categoria/categoriaForm?faces-redirect=true";
    }

    public String prepareIndex() {
        init();
        return "/categoria/listadoCategorias?faces-redirect=true";
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

    public void seleccionarCategoria(ActionEvent event) {
        String claveSeleccionada = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("claveSel");
        dto = new Categoria();
        dto.setIdCategoria(Long.parseLong(claveSeleccionada));
        try {
            dto = dao.read(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporteCategoria() {
        String user = "dpifiuofzznrqg";
        String password = "db19a127a6818ed8d62744c4628df77dfa48f6c2045cb0fe19b00cecdc807275";
        String url = "jdbc:postgresql://ec2-52-70-205-234.compute-1.amazonaws.com/dd7779501rsogd";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteV4.jasper");

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

    public void reporteCategoriaOne() {
        String user = "dpifiuofzznrqg";
        String password = "db19a127a6818ed8d62744c4628df77dfa48f6c2045cb0fe19b00cecdc807275";
        String url = "jdbc:postgresql://ec2-52-70-205-234.compute-1.amazonaws.com/dd7779501rsogd";
        String driverPostgreSql = "org.postgresql.Driver";

        String jasperPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/reporteCategoriaV4.jasper");

        try {
            Class.forName(driverPostgreSql);
            Connection conexion = DriverManager.getConnection(url, user, password);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream sos = response.getOutputStream();
            File reporte = new File(jasperPath);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", dto.getIdCategoria().intValue());
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

    public void graficar() {
        JFreeChart grafica = ChartFactory.createPieChart("Productos por Categoria", obtenerDatosGraficaProductosPorCategoria(), true, true, Locale.getDefault());
        String archivo = FacesContext.getCurrentInstance().getExternalContext().getRealPath(".grafica.png");
//        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRealPath("./grafica.png"));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            ChartUtils.saveChartAsPNG(new File(archivo), grafica, 500, 500);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/grafica.xhtml");

//            RequestDispatcher vista = request.getRequestDispatcher("../grafica.xhtml");
//            vista.forward(request, response);
//            RequestDispatcher vista = ServletActionContext.getRequest().getRequestDispatcher("grafica.jsp");
//            vista.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
        } catch (IOException ex) {
            Logger.getLogger(CategoriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private PieDataset obtenerDatosGraficaProductosPorCategoria() {
        DefaultPieDataset dsPie = new DefaultPieDataset();

        GraficaDAO dao = new GraficaDAO();
        try {
            List datos = dao.obtenerDatosGrafica();
            for (int i = 0; i < datos.size(); i++) {
                GraficaDTO dto = (GraficaDTO) datos.get(i);
                dsPie.setValue(dto.getNombreCategoriaString(), dto.getCantidad());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dsPie;
    }

}
