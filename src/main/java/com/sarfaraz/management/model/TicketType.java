package com.sarfaraz.management.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


//@Table(name = "ticket_type",
//        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class TicketType {
    @Id
    @GeneratedValue(generator = "id_sec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_sec", sequenceName = "jpa_sequence", allocationSize = 5)
    @Column(nullable = false, precision = 5)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;
    private String details;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private Set<Ticket> tickets = new HashSet<>();

    public TicketType() {
    }

    public TicketType(String name) {
        this.name = name;
    }

    public TicketType(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "TicketType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
