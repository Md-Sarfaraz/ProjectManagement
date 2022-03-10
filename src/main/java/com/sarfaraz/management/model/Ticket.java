package com.sarfaraz.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


public class Ticket {

    @Id
    @GeneratedValue(generator = "id_sec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_sec", sequenceName = "jpa_sequence", allocationSize = 5)
    @Column(nullable = false, precision = 5)
    private Long id;
    private String name;
    private String detail;
    private String status;
    private String priority;
    private LocalDate created;
    private LocalDate updated;
    private LocalDate lastDate;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    private Set<TicketAttachment> attachment = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fk_project")
    private Project project;

    @ManyToOne

    @JoinColumn(name = "fk_type")
    private TicketType type;

    @ManyToOne
    @JoinColumn(name = "fk_assigneduser")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "fk_submitter")
    private User submitter;

    public Ticket() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public Set<TicketAttachment> getAttachment() {
        return attachment;
    }

    public void addAttachment(TicketAttachment attachment) {
        this.attachment.add(attachment);
    }

    public void setAttachment(Set<TicketAttachment> attachment) {
        this.attachment = attachment;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public User getUser() {
        return assignedUser;
    }

    public void setUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", lastDate=" + lastDate +
                ", attachment=" + attachment +
                ", project=" + project +
                ", type=" + type +
                ", assignedUser=" + assignedUser +
                ", submitter=" + submitter +
                '}';
    }
}
