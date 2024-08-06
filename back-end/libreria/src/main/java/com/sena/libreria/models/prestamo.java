package com.sena.libreria.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="prestamo")
public class prestamo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="id_prestamo")
	private String id_prestamo;
	
	@ManyToOne
	@JoinColumn(name="id_libro")
	private libro libro;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private usuario usuario;
	
	@Column(name="fecha_prestamo")
	private String fecha_prestamo;

	@Column(name="fecha_devo")
	private String fecha_devo;
	
	@Column(name="estado_prestamo")
	private String estado_prestamo;

	public prestamo() {
		super();
	}

	public prestamo(String id_prestamo, com.sena.libreria.models.libro libro, com.sena.libreria.models.usuario usuario,
			String fecha_prestamo, String fecha_devo, String estado_prestamo) {
		super();
		this.id_prestamo = id_prestamo;
		this.libro = libro;
		this.usuario = usuario;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_devo = fecha_devo;
		this.estado_prestamo = estado_prestamo;
	}

	public String getId_prestamo() {
		return id_prestamo;
	}

	public void setId_prestamo(String id_prestamo) {
		this.id_prestamo = id_prestamo;
	}

	public libro getLibro() {
		return libro;
	}

	public void setLibro(libro libro) {
		this.libro = libro;
	}

	public usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(usuario usuario) {
		this.usuario = usuario;
	}

	public String getFecha_prestamo() {
		return fecha_prestamo;
	}

	public void setFecha_prestamo(String fecha_prestamo) {
		this.fecha_prestamo = fecha_prestamo;
	}

	public String getFecha_devo() {
		return fecha_devo;
	}

	public void setFecha_devo(String fecha_devo) {
		this.fecha_devo = fecha_devo;
	}

	public String getEstado_prestamo() {
		return estado_prestamo;
	}

	public void setEstado_prestamo(String estado_prestamo) {
		this.estado_prestamo = estado_prestamo;
	}

	

	

}
