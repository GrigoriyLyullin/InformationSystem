package com.railwaycompany.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityTestDrive {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("RailwayInformationSystem");
        EntityManager entityManager = factory.createEntityManager();

        User user = entityManager.find(User.class, 1);
        System.out.println("User: " + user.getName() + " " + user.getSurname());

        Station station = entityManager.find(Station.class, 1);
        System.out.println("Station: " + station.getId() + " " + station.getName());

        Train train = entityManager.find(Train.class, 1);
        System.out.println("Train: " + train.getId() + " " + train.getNumber() + " " + train.getSeats());

        Ticket ticket = entityManager.find(Ticket.class, 1);
        System.out.println("Ticket: " + ticket.getId());
        Train trainFromTicket = ticket.getTrain();
        if (trainFromTicket != null) {
            System.out.println("\t trainFromTicket: " + trainFromTicket.getId() + " " + trainFromTicket.getNumber() +
                    " " + trainFromTicket.getSeats());
        } else {
            System.out.println("\ttrainFromTicket is null");
        }

        Schedule schedule = entityManager.find(Schedule.class, 1);
        System.out.println("Schedule: " + schedule.getId() + " " + schedule.getTimeArrival() + " " + schedule
                .getTimeDeparture());

        Train trainFromSchedule = schedule.getTrain();
        Station stationFromSchedule = schedule.getStation();

        if (trainFromSchedule != null) {
            System.out.println("\tTrain: " + trainFromSchedule.getId() + " " + trainFromSchedule.getNumber() + " " +
                    trainFromSchedule.getSeats());
        } else {
            System.out.println("\ttrainFromSchedule is null");
        }

        if (stationFromSchedule != null) {
            System.out.println("\tStation: " + stationFromSchedule.getId() + " " + stationFromSchedule.getName());
        } else {
            System.out.println("\tstationFromSchedule is null");
        }

        entityManager.close();
        factory.close();
    }
}
