package com.icsd19080_icsd19235;

import java.util.Date;
import javax.persistence.*;
import java.util.List;

@Entity
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conference_id")
    private Long conferenceId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public enum ConferenceState {
        CREATED,
        SUBMISSION,
        ASSIGNMENT,
        REVIEW,
        DECISION,
        FINAL_SUBMISSION,
        FINAL
    }

    @Column(name = "pc_chair_id")
    private Long pcChairId;

    @Column(name = "papers")
    private String papers;

    @Column(name = "pc_members")
    private String pcMembers;

    public Conference(){
    }

    public Conference(String name, String description, User pcChair, List<Paper> papers, List<User> pcMembers) {
        this.name = name;
        this.description = description;
        this.pcChairId = (pcChair != null) ? pcChair.getUserId() : null;
        this.papers = convertPaperListToString(papers);
        this.pcMembers = convertUserListToString(pcMembers);
        this.creationDate = new Date(); 
    }
    
    // getters and setters

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPcChairId() {
        return pcChairId;
    }

    public void setPcChairId(Long pcChairId) {
        this.pcChairId = pcChairId;
    }

    public String getPapers() {
        return papers;
    }

    public void setPapers(String papers) {
        this.papers = papers;
    }

    public String getPcMembers() {
        return pcMembers;
    }

    public void setPcMembers(String pcMembers) {
        this.pcMembers = pcMembers;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private ConferenceState state;

    public void setState(ConferenceState state) {
        this.state = state;
    }
    
    public ConferenceState getState() {
        return state;
    }

    private String convertPaperListToString(List<Paper> papers) {
        // Convert the list of papers to a String representation, e.g., concatenate paper IDs
        // You can implement this method based on your requirements
        // For simplicity, let's assume papers have IDs as strings
        StringBuilder stringBuilder = new StringBuilder();
        for (Paper paper : papers) {
            stringBuilder.append(paper.getPaperId()).append(",");
        }
        return stringBuilder.toString();
    }

    private String convertUserListToString(List<User> users) {
        // Convert the list of users to a String representation, e.g., concatenate user IDs
        // You can implement this method based on your requirements
        // For simplicity, let's assume users have IDs as strings
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append(user.getUserId()).append(",");
        }
        return stringBuilder.toString();
    }

    public void addPaper(Paper paper) {
        if (papers == null) {
            papers = String.valueOf(paper.getPaperId());
        } else {
            papers += ", " + paper.getPaperId();
    }
}

}