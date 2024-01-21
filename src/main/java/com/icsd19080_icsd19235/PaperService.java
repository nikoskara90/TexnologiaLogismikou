package com.icsd19080_icsd19235;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icsd19080_icsd19235.Conference.ConferenceState;

import java.util.Set;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConferenceRepository conferenceRepository;

    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }

    public Paper getPaperById(Long id) {
        return paperRepository.findById(id).orElse(null);
    }

    public Optional<User> getAuthor(Long paperId) {
        Paper paper = paperRepository.findById(paperId).orElse(null);
    
        if (paper != null) {
            return Optional.ofNullable(paper.getUser());
        } else {
            System.out.println("Paper not found with ID: " + paperId);
            return Optional.empty();
        }
    }

    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(userService.getUserById(userId));
    }

    public Paper createPaper(Paper inputPaper, Scanner scanner) {
        System.out.println("Enter title:");
        String title = scanner.next();
        System.out.println("Enter abstract:");
        String abstractText = scanner.next();
        System.out.println("Enter content:");
        String content = scanner.next();

        Long authorId;
        while (true) {
            try {
                System.out.println("Enter author's user ID:");
                authorId = scanner.nextLong();
                break; // Break the loop if a valid Long is provided
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid numeric value for the author's user ID.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }
        }

        // Create a new paper with the provided details
        Paper newPaper = new Paper(title, abstractText, content, authorId, userService);

        paperRepository.save(newPaper);

        System.out.println("Paper created successfully!");

        return newPaper;
    }

    public Paper updatePaper(Paper inputPaper, Scanner scanner) {
        System.out.println("Enter the ID of the paper you want to update:");
        Long paperId = scanner.nextLong();

        // Check if the paper with the given ID exists
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);

        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();

            System.out.println("Choose what to update:");
            System.out.println("1. Title");
            System.out.println("2. Abstract");
            System.out.println("3. Content");
            // Add more options as needed

            int updateChoice = scanner.nextInt();

            switch (updateChoice) {
                case 1:
                    System.out.println("Enter the new title:");
                    paper.setTitle(scanner.next());
                    break;

                case 2:
                    System.out.println("Enter the new abstract:");
                    paper.setAbstractText(scanner.next());
                    break;

                case 3:
                    System.out.println("Enter the new content:");
                    paper.setContent(scanner.next());
                    break;

                // Add more cases for other fields if needed

                default:
                    System.out.println("Invalid choice");
            }

            // Save the updated paper
            paperRepository.save(paper);

            System.out.println("Paper updated successfully!");

            return paper; // Return the updated paper
        } else {
            System.out.println("Paper not found with ID: " + paperId);
            return null; // Or handle the case where the paper is not found
        }
    }
/* 
    public Paper addCoAuthor(Paper inputPaper, Scanner scanner) {
        System.out.println("Enter the ID of the paper you want to modify:");
        Long paperId = scanner.nextLong();

        System.out.println("Enter the ID of the co-author you want to add:");
        Long coAuthorId = scanner.nextLong();
        // Check if the paper with the given ID exists
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
    
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
    
            // Get the author of the paper
            Optional<User> optionalAuthor = getAuthor(paperId);
    
            if (optionalAuthor.isPresent()) {
                User paperAuthor = optionalAuthor.get();
    
                // Check if the user with the provided ID exists
                Optional<User> optionalCoAuthor = getUserById(coAuthorId);
    
                if (optionalCoAuthor.isPresent()) {
                    User coAuthor = optionalCoAuthor.get();
    
                    // Check if the co-author's name is included in the paper's author names
                    if (paper.getAuthorNames().contains(coAuthor.getFullName())) {
                        // Assuming you have a method like addCoAuthor in the Paper class
                        paper.addCoAuthor(coAuthor);
    
                        System.out.println("Enter the ID of the conference you want to update:");
                        Long conferenceId = scanner.nextLong();
                        Conference conference = conferenceService.getConference(conferenceId);

                        if (conference != null) {
                            // Assign the co-author the role of AUTHOR for the specific conference
                            Role role = new Role();
                            role.assignUserRole(coAuthor.getUserId(), "AUTHOR"); 
    
                            // Save the updated conference
                            conferenceRepository.save(conference);
                        }
    
                        // Save the updated paper
                        paperRepository.save(paper);
    
                        System.out.println("Co-author added successfully!");
    
                        return paper; // Return the updated paper
                    } else {
                        System.out.println("Co-author's name is not included in the paper's author names.");
                    }
                } else {
                    System.out.println("User not found with ID: " + coAuthorId);
                }
            } else {
                System.out.println("Author not found for the paper with ID: " + inputPaper.getPaperId());
            }
        } else {
            System.out.println("Paper not found with ID: " + inputPaper.getPaperId());
        }
    
        return null; // Return null if the co-author addition is not successful
    }
*/
    public boolean submitPaper(Paper inputPaper, Conference inputConference) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();
    
        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();
        scanner.close();
        // Check if the paper with the given ID exists
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);

        // Check if the paper with the given ID exists
        Optional<Conference> optionalConference= conferenceRepository.findById(conferenceId);
    
        // Check if the provided conference ID matches the actual conference ID
        if (optionalConference.isPresent()) {
            Conference conference = optionalConference.get();
            // Check if the provided paper ID matches the actual paper ID
            if (optionalPaper.isPresent()) {
                Paper paper = optionalPaper.get();

                if (conference.getState() == ConferenceState.SUBMISSION && paper.getContent() != null && !paper.getContent().isEmpty()) {
                    // Perform the submission logic here
                    System.out.println("Paper submitted successfully!");
    
                    // Add the paper to the conference's papers
                    conference.addPaper(paper);
    
                    // Update the paper state to SUBMITTED
                    paper.setState(Paper.PaperState.SUBMITTED);
    
                    // Save the updated conference and paper
                    conferenceRepository.save(conference);
                    paperRepository.save(paper);
                    return true;
                } else {
                    System.out.println("Submission failed. Conference is not in SUBMISSION state or paper content is empty.");
                }
            } else {
                System.out.println("Invalid Paper ID. Submission failed.");
            }
        } else {
            System.out.println("Invalid Conference ID. Submission failed.");
        }   
        return false;
    }

    public boolean assignReviewer(Role inputRole, Paper inputPaper) {
        Scanner scanner = new Scanner(System.in);
    
        // Prompt for Paper ID
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();
    
        // Prompt for Reviewer ID
        System.out.print("Enter Reviewer ID: ");
        Long reviewerId = scanner.nextLong();
    
        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();
    
        try {
            // Check if the paper with the given ID exists
            Optional<Paper> optionalPaper = paperRepository.findById(paperId);
            // Check if the User with the given ID exists
            Optional<User> optionalUser = userRepository.findById(reviewerId);
            // Check if the conference with the given ID exists
            Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
            // Validate input and check if the provided conference ID matches the actual conference ID
            if (optionalConference.isPresent()) {
                Conference conference = optionalConference.get();
    
                // Check if the conference is in the ASSIGNMENT state
                if (conference.getState() == ConferenceState.ASSIGNMENT) {
                    // Check if the provided paper ID exists
                    if (optionalPaper.isPresent()) {
                        Paper paper = optionalPaper.get();
                        // Check if the provided reviewer ID exists
                        if (optionalUser.isPresent()) {
                            User user = optionalUser.get();
    
                            // Fetch the user's roles
                            Set<Role> userRoles = user.getRoles();
                            // Check if the user has the PC_MEMBER role
                            boolean isPCMember = userRoles.stream()
                                    .anyMatch(role -> role.getState() == Role.RoleName.PC_MEMBER);
    
                            if (paper.getReviewer1() == null && paper.getReviewer2() == null) {
                                // Check if the reviewer is a member of the Programme Committee (PC)
                                if (isPCMember) {
                                    // Assign the reviewer to the paper
                                    if (paper.getReviewer1() == null) {
                                        paper.setReviewer1(user);
                                    } else if (paper.getReviewer2() == null) {
                                        paper.setReviewer2(user);
                                    }
    
                                    // Update the paper state to REVIEW
                                    paper.setState(Paper.PaperState.REVIEWED);
    
                                    // Save the updated paper
                                    paperRepository.save(paper);
    
                                    // Log success and return true
                                    System.out.println("Reviewer assigned successfully!");
                                    scanner.close();
                                    return true;
                                } else {
                                    System.out.println("Reviewer is not a member of the Programme Committee (PC). Reviewer assignment failed.");
                                }
                            } else {
                                System.out.println("Paper already has assigned reviewers. Reviewer assignment failed.");
                            }
                        } else {
                            System.out.println("Invalid Reviewer ID. Reviewer assignment failed.");
                        }
                    } else {
                        System.out.println("Invalid Paper ID. Reviewer assignment failed.");
                    }
                } else {
                    System.out.println("Conference is not in ASSIGNMENT state. Reviewer assignment failed.");
                }
            } else {
                System.out.println("Invalid Conference ID. Reviewer assignment failed.");
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., DataAccessException, RuntimeException) appropriately
            System.out.println("An error occurred during reviewer assignment: " + e.getMessage());
        }
        scanner.close();
        // Return false if any condition fails
        return false;
    }

    public boolean submitPaperReview(Paper inputPaper) {
        Scanner scanner = new Scanner(System.in);
    
        // Prompt for Paper ID
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();
    
        // Prompt for Reviewer ID
        System.out.print("Enter Reviewer ID: ");
        Long reviewerId = scanner.nextLong();
    
        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();
        
        // Check if the paper with the given ID exists
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
    
        // Check if the User with the given ID exists
        Optional<User> optionalUser = userRepository.findById(reviewerId);

        // Check if the Conference with the given ID exists
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        // Check if the provided paper ID and reviewer ID exist
        if (optionalPaper.isPresent() && optionalUser.isPresent()) {
            Paper paper = optionalPaper.get();
            User reviewer = optionalUser.get();
            Conference conference = optionalConference.get();

            // Check if the conference is in the REVIEW state
            if (conference.getState() == ConferenceState.REVIEW) {
                // Check if the reviewer has the necessary role (PC member or chair)
                // Fetch the user's roles
                Set<Role> userRoles = reviewer.getRoles();
                // Check if the user has the PC_MEMBER role
                boolean isPCMember = userRoles.stream().anyMatch(role -> role.getState() == Role.RoleName.PC_MEMBER);

                boolean isPChair = userRoles.stream().anyMatch(role -> role.getState() == Role.RoleName.PC_CHAIR);

                if (isPCMember || isPChair) {
                    
                    System.out.print("Enter Reviewer Comments: ");
                    String comm = scanner.next();
                    scanner.nextLine();
                    paper.setReviewer1Comments(comm);

                    System.out.print("Enter Review score (1-10): ");
                    int score = scanner.nextInt();
                    paper.setReviewer1Score(score);
    
                    // Save the updated paper
                    paperRepository.save(paper);
    
                    // Log success and return true
                    System.out.println("Paper review submitted successfully!");
                    scanner.close();
                    return true;
                } else {
                    System.out.println("Reviewer does not have the necessary role to perform a review.");
                }
            } else {
                System.out.println("Conference is not in the REVIEW state. Paper review failed.");
            }
        } else {
            System.out.println("Invalid Paper ID or Reviewer ID. Paper review failed.");
        }
    
        // Return false if any condition fails
        scanner.close();
        return false;
    }
    
    public boolean approvePaper(Paper inputpaper) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for Paper ID
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();

        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();

        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
            Conference conference = optionalConference.get();
    
            // Check if the conference is in the DECISION state
            if (conference.getState() == ConferenceState.DECISION) {
                // Check if the paper has been reviewed
                if (paper.getState() == Paper.PaperState.REVIEWED) {
                    // Display reviewer comments and ask for modifications
                    System.out.println("Reviewer 1 Comments: " + paper.getReviewer1Comments());
                    System.out.println("Reviewer 2 Comments: " + paper.getReviewer2Comments());
    
                    // Prompt user for modifications
                    System.out.print("Enter Paper Modifications: ");
                    String modifications = scanner.next();
                    paper.setReviewer1Comments(modifications);
                    
                    paper.setState(Paper.PaperState.APPROVED);
                    // Save the updated paper
                    paperRepository.save(paper);
    
                    // Log success and return true
                    System.out.println("Paper approved with modifications. Please address the reviewer comments.");
                    scanner.close();
                    return true;
                } else {
                    System.out.println("Paper must be in the REVIEWED state to be approved.");
                }
            } else {
                System.out.println("Conference is not in the DECISION state. Paper approval failed.");
            }
        } else {
            System.out.println("Invalid Paper ID. Paper approval failed.");
        }
    
        // Return false if any condition fails
        scanner.close();
        return false;
    }
    
    public boolean rejectPaper(Paper inputPaper) {
        Scanner scanner = new Scanner(System.in);
    
        // Prompt for Paper ID
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();
    
        // Consume the newline character left in the buffer
        scanner.nextLine();
    
        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();
    
        // Consume the newline character left in the buffer
        scanner.nextLine();
    
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
            Conference conference = optionalConference.get();
    
            // Check if the conference is in the DECISION state
            if (conference.getState() == ConferenceState.DECISION) {
                // Reject the paper
                paper.setState(Paper.PaperState.REJECTED);
    
                // Save the updated paper
                paperRepository.save(paper);
    
                // Log success and return true
                System.out.println("Paper rejected from the conference.");
                scanner.close();
                return true;
            } else {
                System.out.println("Conference is not in the DECISION state. Paper rejection failed.");
            }
        } else {
            System.out.println("Invalid Paper ID. Paper rejection failed.");
        }
    
        // Return false if any condition fails
        scanner.close();
        return false;
    }

    public boolean finalSubmitPaper(Paper inputPaper) {
        Scanner scanner = new Scanner(System.in);
    
        // Prompt for Paper ID
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();
    
        // Consume the newline character left in the buffer
        scanner.nextLine();
    
        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();
    
        // Consume the newline character left in the buffer
        scanner.nextLine();
    
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalPaper.isPresent()) {
            Paper paper = optionalPaper.get();
            Conference conference = optionalConference.get();
    
            // Check if the conference is in the FINAL_SUBMISSION state
            if (conference.getState() == ConferenceState.FINAL_SUBMISSION) {
                // Display reviewer comments
                System.out.println("Reviewer 1 Comments: " + paper.getReviewer1Comments());
                System.out.println("Reviewer 2 Comments: " + paper.getReviewer2Comments());
    
                // Prompt user for paper modifications
                System.out.print("Enter Paper Modifications (to address reviewer comments): ");
                String modifications = scanner.next();
                paper.setContent(modifications);
    
                /* 
                // Display author explanation
                System.out.print("Enter Author Explanation (how addressing has been performed): ");
                String explanation = scanner.next();
                paper.setAuthorExplanation(explanation);
                */  
                // Save the updated paper
                paperRepository.save(paper);

                // Add the paper to the conference's list of papers
                String paperIdString = String.valueOf(paperId);
                conference.setPapers(conference.getPapers() + paperIdString + ", " );
                conferenceRepository.save(conference);
    
                // Log success and return true
                System.out.println("Paper finally submitted to the conference.");
                scanner.close();
                return true;
            } else {
                System.out.println("Conference is not in the FINAL_SUBMISSION state. Final submission failed.");
            }
        } else {
            System.out.println("Invalid Paper ID. Final submission failed.");
        }
    
        // Return false if any condition fails
        scanner.close();
        return false;
    }

    public boolean acceptPaper(Paper inputPaper) {
        Scanner scanner = new Scanner(System.in);
        // Prompt for Paper ID
        System.out.print("Enter Paper ID: ");
        Long paperId = scanner.nextLong();
    
        // Consume the newline character left in the buffer
        scanner.nextLine();
    
        System.out.print("Enter Conference ID: ");
        Long conferenceId = scanner.nextLong();

        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalPaper.isPresent() && optionalConference.isPresent()) {
            Paper paper = optionalPaper.get();
            Conference conference = optionalConference.get();
    
            // Check if the conference is in the FINAL state
            if (conference.getState() == ConferenceState.FINAL) {
                // Set the state of the paper to ACCEPTED
                paper.setState(Paper.PaperState.ACCEPTED);
    
                // Save the updated paper
                paperRepository.save(paper);
    
                // Log success and return true
                System.out.println("Paper accepted and can be presented in the conference.");
                scanner.close();
                return true;
            } else {
                System.out.println("Conference is not in the FINAL state. Paper acceptance failed.");
            }
        } else {
            System.out.println("Invalid Paper ID or Conference ID. Paper acceptance failed.");
        }
    
        // Return false if any condition fails
        scanner.close();
        return false;
    }
    
    public List<Paper> searchPaper(String title, String authorNames, String abstractText, User user) {
        // Build the search query based on provided criteria
        List<Paper> papers;
    
        if (title != null && authorNames != null && abstractText != null) {
            papers = paperRepository.findByTitleContainingAndAuthorNamesContainingAndAbstractTextContainingOrderByTitle(title, authorNames, abstractText);
        } else if (title != null && authorNames != null) {
            papers = paperRepository.findByTitleContainingAndAuthorNamesContainingOrderByTitle(title, authorNames);
        } else if (title != null && abstractText != null) {
            papers = paperRepository.findByTitleContainingAndAbstractTextContainingOrderByTitle(title, abstractText);
        } else if (authorNames != null && abstractText != null) {
            papers = paperRepository.findByAuthorNamesContainingAndAbstractTextContainingOrderByTitle(authorNames, abstractText);
        } else if (title != null) {
            papers = paperRepository.findByTitleContainingOrderByTitle(title);
        } else if (authorNames != null) {
            papers = paperRepository.findByAuthorNamesContainingOrderByTitle(authorNames);
        } else if (abstractText != null) {
            papers = paperRepository.findByAbstractTextContainingOrderByTitle(abstractText);
        } else {
            papers = paperRepository.findAllByOrderByTitle();
        }

        List<Paper> filteredPapers = Role.applyRoleBasedFilter(papers, user);

        return filteredPapers;
    }
    
    public Map<String, Object> getPaperViewById(Long paperId, User currentUser) {
        Paper paper = paperRepository.findById(paperId).orElse(null);
        
        if (paper != null) {
            List<Paper> filteredPapers = Role.applyRoleBasedFilter(List.of(paper), currentUser);
        
            if (!filteredPapers.isEmpty()) {
                return createPaperViewBasedOnRole(filteredPapers.get(0), currentUser);
            }else{
                System.out.println("You can not view this paper");
            }
        }   
        return null;
    }

    private Map<String, Object> createPaperViewBasedOnRole(Paper paper, User currentUser) {
        Map<String, Object> paperView = new HashMap<>();
    
        // Populate the map with paper information
        paperView.put("Paper ID", paper.getPaperId());
        paperView.put("Title", paper.getTitle());
        paperView.put("Authors", paper.getAuthorNames());
        paperView.put("Abstract", paper.getAbstractText());


        // Print the HashMap content
        for (Map.Entry<String, Object> entry : paperView.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        return paperView;
    }
    
    public void withdrawPaper(Long paperId) {
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);
    
        if (optionalPaper.isPresent()) {
            // Delete the paper from the database
            paperRepository.deleteById(paperId);
            System.out.println("Paper withdrawn successfully.");
        } else {
            System.out.println("Paper not found with ID: " + paperId);
        }
    }
}


