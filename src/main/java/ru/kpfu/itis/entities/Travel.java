package ru.kpfu.itis.entities;

import java.sql.Date;

public class Travel {
    private int travel_id;
    private int user_id;
    private String name_travel;
    private String description;
    private Date start_date;
    private Date end_date;
    private String transport;
    private String list_of_things;
    private String notes; //доп информация
    private String travel_url;

    public Travel(int travel_id, int user_id, String name_travel, String description, Date start_date, Date end_date, String transport, String list_of_things, String notes, String travel_url) {
        this.travel_id = travel_id;
        this.user_id = user_id;
        this.name_travel = name_travel;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.transport = transport;
        this.list_of_things = list_of_things;
        this.notes = notes;
        this.travel_url = travel_url;
    }

    public Travel(int user_id, String name_travel, String description, Date start_date, Date end_date, String transport, String list_of_things, String notes, String travel_url) {
        this.user_id = user_id;
        this.name_travel = name_travel;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.transport = transport;
        this.list_of_things = list_of_things;
        this.notes = notes;
        this.travel_url = travel_url;
    }

    public Travel(int travel_id, int user_id, String name_travel, String description, Date start_date, Date end_date, String transport, String list_of_things, String notes) {
        this.travel_id = travel_id;
        this.user_id = user_id;
        this.name_travel = name_travel;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.transport = transport;
        this.list_of_things = list_of_things;
        this.notes = notes;
    }

    public Travel(int user_id, String name_travel, String description, Date start_date, Date end_date, String transport, String list_of_things, String notes) {
        this.user_id = user_id;
        this.name_travel = name_travel;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.transport = transport;
        this.list_of_things = list_of_things;
        this.notes = notes;
    }

    public int getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(int travel_id) {
        this.travel_id = travel_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName_travel() {
        return name_travel;
    }

    public void setName_travel(String name_travel) {
        this.name_travel = name_travel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getList_of_things() {
        return list_of_things;
    }

    public void setList_of_things(String list_of_things) {
        this.list_of_things = list_of_things;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTravel_url() {
        return travel_url;
    }

    public void setTravel_url(String travel_url) {
        this.travel_url = travel_url;
    }
}
