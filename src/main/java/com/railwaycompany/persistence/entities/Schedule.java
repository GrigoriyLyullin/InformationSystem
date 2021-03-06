package com.railwaycompany.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_schedule")
    private int id;
    @Column(name = "time_arrival")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timeArrival;
    @Column(name = "time_departure")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date timeDeparture;
    @OneToOne
    @JoinColumn(name = "train_id")
    private Train train;
    @OneToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(Date timeArrival) {
        this.timeArrival = timeArrival;
    }

    public Date getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(Date timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
