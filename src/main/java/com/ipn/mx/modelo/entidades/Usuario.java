/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author darkdestiny
 */
@Entity
@Table (name = "Usuario")
@Data
@NoArgsConstructor
public class Usuario implements Serializable{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idUsuario;
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "paterno", length = 50)
    private String paterno;
    @Column(name = "materno", length = 50)
    private String materno;
    @Column(name = "email", length = 100, unique = true)
    private String email;
    
//    private String imagen;
    @Size(min = 6, max = 20 ,message = "El valor debe estar entre 6 y 20")
    @Column(name = "nombreUsuario", length = 20)
    private String nombreUsuario;
    @Column(name = "claveUsuario", length = 20)
    private String claveUsuario;
//    @Temporal(TemporalType.DATE)
//    @Column(name = "createAt")
//    private Date createAt;
    
    
//    @PrePersist
//    public void prePersist(){
//        this.createAt = new Date();
//    }
    
    public static void main(String[] args) {
        Usuario u = new Usuario();
//        u.setNombre("jose");
//        u.setPaterno("jose");
//        u.setMaterno("jose");
//        u.setEmail("jose@jose.com");
//        u.setImagen("sinAvatar.png");
//        u.setNombreUsuario("jose");
//        u.setClaveUsuario("jose");

        u.setIdUsuario(2);
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoBasePU");
        EntityManager em =emf.createEntityManager();
        em.getTransaction().begin();
        //em.persist(u);
        System.out.println(em.find(Usuario.class, u.getIdUsuario()));
        u = em.find(Usuario.class, u.getIdUsuario());
        em.remove(u);
        //em.merge(u);
        em.getTransaction().commit();
    }
}
