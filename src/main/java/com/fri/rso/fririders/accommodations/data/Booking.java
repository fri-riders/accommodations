package com.fri.rso.fririders.accommodations.data;

import java.util.Date;

public class Booking {
    private int id;
    private int idAccommodation;
    private int idUser;
    private Date fromDate;
    private Date toDate;

    public Booking(){
        this.id = 0;
        this.idAccommodation = 0;
        this.idUser = 0;
        this.fromDate = null;
        this.toDate = null;
    }

    public Booking(int id, int idAccommodation, int idUser, Date fromDate, Date toDate) {
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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
