package com.fri.rso.fririders.accommodations.data;

import java.util.Date;

public class Booking {
    private int id;
    private int idAccommodation;
    private String idUser;
    private Date fromDate;
    private Date toDate;

    public Booking(){
        this.id = 0;
        this.idAccommodation = 0;
        this.idUser = null;
        this.fromDate = null;
        this.toDate = null;
    }

    public Booking(int id, int idAccommodation, String idUser, Date fromDate, Date toDate) {
        this.id = id;
        this.idAccommodation = idAccommodation;
        this.idUser = idUser;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAccommodation() {
        return idAccommodation;
    }

    public void setIdAccommodation(int idAcommodation) {
        this.idAccommodation = idAcommodation;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
