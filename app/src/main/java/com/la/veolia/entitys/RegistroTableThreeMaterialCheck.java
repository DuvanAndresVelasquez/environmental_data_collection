package com.la.veolia.entitys;

public class RegistroTableThreeMaterialCheck {
    private int id;
    private String item;
    private String se_requiere;
    private String revision_salida;
    private String revision_campo;
    private String revision_llegada;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSe_requiere() {
        return se_requiere;
    }

    public void setSe_requiere(String se_requiere) {
        this.se_requiere = se_requiere;
    }

    public String getRevision_salida() {
        return revision_salida;
    }

    public void setRevision_salida(String revision_salida) {
        this.revision_salida = revision_salida;
    }

    public String getRevision_campo() {
        return revision_campo;
    }

    public void setRevision_campo(String revision_campo) {
        this.revision_campo = revision_campo;
    }

    public String getRevision_llegada() {
        return revision_llegada;
    }

    public void setRevision_llegada(String revision_llegada) {
        this.revision_llegada = revision_llegada;
    }
}
