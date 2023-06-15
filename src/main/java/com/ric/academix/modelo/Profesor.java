/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ric.academix.modelo;

import com.ric.academix.modelo.Grupo;
import com.ric.academix.modelo.TipoUsuario;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author ricar
 */
@Entity
@Table(name = "profesor")
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Column(name = "contrasegna")
    private String contrasegna;
    @Column(name = "email")
    private String email;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tutorId")
    private Collection<Grupo> grupoCollection;
    
     //bi-directional many-to-one association to ProfesorAsignatura
    @OneToMany(mappedBy = "profesor")
    private List<ProfesorAsignatura> profesorAsignaturas;
    
    
    @JoinColumn(name = "tipo_usuario", insertable = false, updatable = false, referencedColumnName = "codigo")
    @ManyToOne
    private TipoUsuario tipoUsuario;
    @Column(name = "tipo_usuario")
    private int codigoTipoUsuario;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;

    public Profesor() {
    }

    public Profesor(Integer id, String nombre, String primerApellido, String segundoApellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.email = email;
    }

    public Profesor(String nombre, String primerApellido, String segundoApellido, String contrasegna, String email, int codigoTipoUsuario) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.contrasegna = contrasegna;
        this.email = email;
        this.codigoTipoUsuario = codigoTipoUsuario;
    }

    public String getNombreCompleto() {
        String nombreCompleto = this.nombre + " " + this.primerApellido + " " + this.segundoApellido;
        return nombreCompleto;
    }

    public Profesor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getContrasegna() {
        return contrasegna;
    }

    public void setContrasegna(String contrasegna) {
        this.contrasegna = contrasegna;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getCodigoTipoUsuario() {
        return codigoTipoUsuario;
    }

    public void setCodigoTipoUsuario(int codigoTipoUsuario) {
        this.codigoTipoUsuario = codigoTipoUsuario;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Profesor{" + "id=" + id
                + ", nombre=" + nombre
                + ", primerApellido=" + primerApellido
                + ", segundoApellido=" + segundoApellido
                + ", contrasegna=" + contrasegna
                + ", email=" + email
                + ", tipoUsuario=" + tipoUsuario
                + ", codigoTipoUsuario=" + codigoTipoUsuario
                + ", fechaNacimiento=" + fechaNacimiento
                + ", telefono=" + telefono
                + ", direccion=" + direccion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
