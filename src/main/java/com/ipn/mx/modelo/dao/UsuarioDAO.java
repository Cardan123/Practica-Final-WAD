/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.entidades.Producto;
import com.ipn.mx.modelo.entidades.Usuario;
import com.ipn.mx.utilerias.EnviarMail;
import com.ipn.mx.utilerias.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author cardan_mac
 */
public class UsuarioDAO implements Serializable {
    EnviarMail mail = new EnviarMail();
    
    public void create(Usuario dto) {
        
        mail.enviarCorreo(dto.getEmail(), "Usuario Registrado", "Usuario registrado exitosamente");
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.save(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    public void update(Usuario dto) {
        
        mail.enviarCorreo(dto.getEmail(), "Usuario Actualizado", "Usuario Actualizado Correctamente");
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.update(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    public void delete(Usuario dto) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        try {
            t.begin();
            s.delete(dto);
            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
    }

    public Usuario read(Usuario dto) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            dto = session.get(dto.getClass(), dto.getIdUsuario());
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return dto;
    }

    public List<Usuario> readAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.getTransaction();
        List<Usuario> lista = new ArrayList();
        try {
            t.begin();
            Query q = s.createQuery("from Usuario u order by u.idUsuario");
            lista = q.list();
//            for (Categoria c: (List<Categoria>) q.list()) {
//                CategoriaDTO dto = new CategoriaDTO();
//                dto.setEntidad(c);
//                lista.add(dto);
//            }

            t.commit();
        } catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }

        return lista;
    }
//    public List<GraficaDTO> readAll2() {
//        Session s = HibernateUtil.getSessionFactory().openSession();
//        Transaction t = s.getTransaction();
//        List<GraficaDTO> lista = new ArrayList();
//        try {
//            t.begin();
//            //SELECT DISTINCT e FROM Employee e INNER JOIN e.tasks t where t.supervisor='Denise'
//            Query q = s.createQuery("select c.nombreCategoria, count(p) from Producto p INNER JOIN Categoria c ON p.idCategoria = c.idCategoria group by c.nombreCategoria");
//            
//            //SELECT d.name, e.name, e.email, e.address FROM department d INNER JOIN employee e ON d.id = e.dept_id;
//            //Query q = s.createQuery("select c.nombreCategoria, p.nombreProducto from Producto p INNER JOIN Categoria c ON p.idCategoria = c.idCategoria");
//            
//            /*
//            SELECT p.nombreproducto, count(*)
//FROM producto p
//	INNER JOIN categoria c ON 
//	 p.idcategoria = c.idcategoria
//            */
//            //lista = q.list();
//            /*
//             GraficaDTO dto = new GraficaDTO();
//                 dto.setNombreCategoria(rs.getString("nombreCategoria"));
//                 dto.setCantidad(rs.getInt("cantidad"));
//                 lista.add(dto);
//            */
//            
//            for (GraficaDTO c: (List<GraficaDTO>) q.list()) {
//                GraficaDTO dto = new GraficaDTO();
//                dto.setName(c.getName());
//                dto.setValue(c.getValue());
//                lista.add(dto);
//            }
//
//            t.commit();
//        } catch (HibernateException he) {
//            if (t != null && t.isActive()) {
//                t.rollback();
//            }
//        }
//
//        return lista;
//    }
    
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        System.out.println(dao.readAll());
    }
}
