package com.railwaycompany.entities;

import com.railwaycompany.dao.GenericDao;
import com.railwaycompany.dao.HibernateDao;

import java.util.Date;

public class EntityTestDrive {

    public static void main(String[] args) {

        GenericDao<User> userDao = new HibernateDao<>(User.class);

        User user = new User();

        user.setLogin("new_login");
        user.setPassword("password");
        user.setName("name");
        user.setSurname("surname");
        user.setEmployee(false);
        user.setBirthdate(new Date());

        userDao.create(user);

        User user2 = userDao.read(user.getId());

        if (user2 != null) {
            System.out.println("user: " + user2.getId() + " " + user2.getName() + " " + user2.getBirthdate());
        } else {
            System.out.println("User not found");
        }
/*
        EntityManager entityManager = EntityManagerSingleton.getInstance();

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
    */
    }
}
