/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Carlos Villena
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraficaDTO implements Serializable{
    private String nombreCategoriaString;
    private int cantidad;
}
