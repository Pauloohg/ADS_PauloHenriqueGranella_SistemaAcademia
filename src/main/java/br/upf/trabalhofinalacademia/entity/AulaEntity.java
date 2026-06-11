/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.trabalhofinalacademia.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "aula")
public class AulaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private String tipo;
    
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "data_aula")
    private Date dataAula;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "horario")
    private String horario;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_personal", referencedColumnName = "id")
    private PersonalEntity idPersonal;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    private AlunoEntity idAluno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataAula() {
        return dataAula;
    }

    public void setDataAula(Date dataAula) {
        this.dataAula = dataAula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public PersonalEntity getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(PersonalEntity idPersonal) {
        this.idPersonal = idPersonal;
    }

    public AlunoEntity getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(AlunoEntity idAluno) {
        this.idAluno = idAluno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AulaEntity other = (AulaEntity) obj;
        return Objects.equals(this.id, other.id);
    }    
}
