package com.alexisboiz.covoitapp.model.API_Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class Fields {

    @SerializedName("type_de_parking")
    @Expose
    private String typeDeParking;
    @SerializedName("nom_dep")
    @Expose
    private String nomDep;
    @SerializedName("couv4gbytel")
    @Expose
    private String couv4gbytel;
    @SerializedName("nom_du_lieu")
    @Expose
    private String nomDuLieu;
    @SerializedName("couv4gorange")
    @Expose
    private String couv4gorange;
    @SerializedName("nom_epci")
    @Expose
    private String nomEpci;
    @SerializedName("places")
    @Expose
    private Integer places;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("code_postal")
    @Expose
    private String codePostal;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("adresse")
    @Expose
    private String adresse;
    @SerializedName("ville")
    @Expose
    private String ville;
    @SerializedName("couv4gfree")
    @Expose
    private String couv4gfree;
    @SerializedName("couv4gsfr")
    @Expose
    private String couv4gsfr;
    @SerializedName("nom_reg")
    @Expose
    private String nomReg;
    @SerializedName("coordonnees")
    @Expose
    private List<Double> coordonnees;
    @SerializedName("transport_public")
    @Expose
    private String transportPublic;
    @SerializedName("proprietaire")
    @Expose
    private String proprietaire;

    public String getTypeDeParking() {
        return typeDeParking;
    }

    public void setTypeDeParking(String typeDeParking) {
        this.typeDeParking = typeDeParking;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getCouv4gbytel() {
        return couv4gbytel;
    }

    public void setCouv4gbytel(String couv4gbytel) {
        this.couv4gbytel = couv4gbytel;
    }

    public String getNomDuLieu() {
        return nomDuLieu;
    }

    public void setNomDuLieu(String nomDuLieu) {
        this.nomDuLieu = nomDuLieu;
    }

    public String getCouv4gorange() {
        return couv4gorange;
    }

    public void setCouv4gorange(String couv4gorange) {
        this.couv4gorange = couv4gorange;
    }

    public String getNomEpci() {
        return nomEpci;
    }

    public void setNomEpci(String nomEpci) {
        this.nomEpci = nomEpci;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCouv4gfree() {
        return couv4gfree;
    }

    public void setCouv4gfree(String couv4gfree) {
        this.couv4gfree = couv4gfree;
    }

    public String getCouv4gsfr() {
        return couv4gsfr;
    }

    public void setCouv4gsfr(String couv4gsfr) {
        this.couv4gsfr = couv4gsfr;
    }

    public String getNomReg() {
        return nomReg;
    }

    public void setNomReg(String nomReg) {
        this.nomReg = nomReg;
    }

    public List<Double> getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(List<Double> coordonnees) {
        this.coordonnees = coordonnees;
    }

    public String getTransportPublic() {
        return transportPublic;
    }

    public void setTransportPublic(String transportPublic) {
        this.transportPublic = transportPublic;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

}