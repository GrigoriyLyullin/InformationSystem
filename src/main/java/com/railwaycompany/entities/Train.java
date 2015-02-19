package com.railwaycompany.entities;

import javax.persistence.*;

@Entity
@Table(name = "train")
public class Train {

    @Id
    @Column(name = "id_train")
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "seats")
    private int seats;

//    @ManyToOne
//    private Train train;
//
//    public Train getTrain() {
//        return train;
//    }
//
//    public void setTrain(Train train) {
//        this.train = train;
//    }

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
}
