/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dao;


import com.ipn.mx.modelo.entidades.Categoria;
import com.ipn.mx.utilerias.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author darkdestiny
 */
public class CategoriaDAO {

    public void create(Categoria dto) {
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
    public void update(Categoria dto) {
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
    public void delete(Categoria dto) {
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

    
    public Categoria read(Categoria dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto = session.get(dto.getClass(), dto.getIdCategoria());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public List readAll() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.getTransaction();
        List lista = new ArrayList();
        try{
            t.begin();
            Query q = s.createQuery("from Categoria c order by c.idCategoria");
            lista = q.list();
            t.commit();
        }catch (HibernateException he) {
            if (t != null && t.isActive()) {
                t.rollback();
            }
        }
        
        return lista;
    }
  
    public static void main(String[] args) {
        Categoria dto = new Categoria();
        //dto.setIdCategoria(3);
       // dto.setNombreCategoria("Categoria ar");
        //dto.setDescripcionCategoria("Eliminar");

        CategoriaDAO dao = new CategoriaDAO();
        // dao.create(dto);
        // dao.update(dto);
         System.out.println(dao.readAll());
        // System.out.println(dao.read(dto));
        //dao.delete(dto);
    }
}
