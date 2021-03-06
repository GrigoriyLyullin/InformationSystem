package com.railwaycompany.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "train")
public class Train implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_train")
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "seats")
    private int seats;
    @Column(name = "ticket_cost")
    private float ticketCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public float getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(float ticketCost) {
        this.ticketCost = ticketCost;
    }
}
