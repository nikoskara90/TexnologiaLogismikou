package com.icsd19080_icsd19235;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paperId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "title")
    private String title;

    @Column(name = "abstract_text")
    private String abstractText;

    @Column(name = "content")
    private String content;

    @Column(name = "author_names")
    private String authorNames;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "reviewer1_id")
    private User reviewer1;

    @ManyToOne
    @JoinColumn(name = "reviewer2_id")
    private User reviewer2;

    @Column(name = "reviewer1_comments")
    private String reviewer1Comments;

    @Column(name = "reviewer2_comments")
    private String reviewer2Comments;

    @Column(name = "reviewer1_score")
    private Integer reviewer1Score;

    @Column(name = "reviewer2_score")
    private Integer reviewer2Score;

    @Enumerated(EnumType.STRING)
    private PaperState state;

    // Enum representing paper states
    public enum PaperState {
        CREATED,
        SUBMITTED,
        REVIEWED,
        REJECTED,
        APPROVED,
        ACCEPTED
    }

    // Constructors, getters, and setters
    public Paper(){
        this.creationDate = new Date();
        this.state = PaperState.CREATED;
    }

    // Updated constructor
    public Paper(String title, String abstractText, String content, Long userId, UserService userService) {
        this(); // Assuming this is a call to a no-argument constructor
        this.title = title;
        this.abstractText = abstractText;
        this.content = content; 

        // Fetch the author's name based on the provided userId using the UserService
        User author = userService.getUserById(userId);

        if (author != null) {
            this.authorNames = author.getFullName();
            this.setUser(userId);
        } else {
            throw new IllegalArgumentException("User not found for the provided userId: " + userId);
        }
    }
 
    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }

    public User getUser() {
        return user;
    }

    public void setUser(Long userId) {
        this.user = new User();
        this.user.setUserId(userId);
    }

    public User getReviewer1() {
        return reviewer1;
    }

    public void setReviewer1(User reviewer1) {
        this.reviewer1 = reviewer1;
    }

    public User getReviewer2() {
        return reviewer2;
    }

    public void setReviewer2(User reviewer2) {
        this.reviewer2 = reviewer2;
    }

    public String getReviewer1Comments() {
        return reviewer1Comments;
    }

    public void setReviewer1Comments(String reviewer1Comments) {
        this.reviewer1Comments = reviewer1Comments;
    }

    public String getReviewer2Comments() {
        return reviewer2Comments;
    }

    public void setReviewer2Comments(String reviewer2Comments) {
        this.reviewer2Comments = reviewer2Comments;
    }

    public Integer getReviewer1Score() {
        return reviewer1Score;
    }

    public void setReviewer1Score(Integer reviewer1Score) {
        this.reviewer1Score = reviewer1Score;
    }

    public Integer getReviewer2Score() {
        return reviewer2Score;
    }

    public void setReviewer2Score(Integer reviewer2Score) {
        this.reviewer2Score = reviewer2Score;
    }

    public PaperState getState() {
        return state;
    }

    public void setState(PaperState state) {
        this.state = state;
    }

    // Methods
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> coAuthors = new ArrayList<>();

    // Add a user to the coAuthors list
    public void addCoAuthor(User coAuthor) {
        coAuthors.add(coAuthor);
        // Update author names with the new co-author
        if (authorNames == null) {
            authorNames = coAuthor.getFullName();
        } else {
            authorNames += ", " + coAuthor.getFullName();
        }
    }
    
    // Getter for the coAuthors list
    public List<User> getCoAuthors() {
        return coAuthors;
    }

    // Setter for the coAuthors list
    public void setCoAuthors(ArrayList<User> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public void submitPaper() {
       if (this.state == PaperState.CREATED) {
           this.state = PaperState.SUBMITTED;
       } else {
           // Handle invalid state transition
           System.out.println("Cannot submit paper in the current state: " + this.state);
        }
    }

}


