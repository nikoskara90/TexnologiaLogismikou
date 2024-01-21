package com.icsd19080_icsd19235;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icsd19080_icsd19235.Conference.ConferenceState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Service
public class ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private PaperRepository paperRepository; 

    @Autowired
    private PaperService paperService; 
    
    @Autowired
    private UserService userService; 
    
    @Autowired
    private UserRepository userRepository;  

    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    public Conference getConference(Long id) {
        return conferenceRepository.findById(id).orElse(null);
    }

    public Conference createConference(Conference conference, Scanner scanner) {
    // Input data using scanner
    System.out.println("Enter name:");
    String name = scanner.next();

    System.out.println("Enter description:");
    String description = scanner.next();

    //Similarly, get other inputs like pcChair, papers, and pcMembers using scanner
    System.out.println("Enter PC Chair ID:");
        Long pcChairId = scanner.nextLong();
        
        Optional<User> pcChairOptional = userRepository.findById(pcChairId);

        if (pcChairOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + pcChairId + " not found");
        }

        User pcChair = pcChairOptional.get();
        Set<Role> userRoles = pcChair.getRoles();

        boolean isPChair = userRoles.stream().anyMatch(role -> role.getState() == Role.RoleName.PC_CHAIR);

        if (!isPChair) {
            throw new IllegalArgumentException("User with ID " + pcChairId + " is not a PC Chair");
        }
    // Assuming papers are identified by their IDs
    System.out.println("Enter Paper ID (separate multiple IDs with commas):");
    String paperIdsInput = scanner.next();
    List<Paper> papersList = convertPaperIdsToPaperList(paperIdsInput);

    // Assuming PC members are identified by their IDs
    System.out.println("Enter PC Member IDs (separate multiple IDs with commas):");
    String pcMemberIdsInput = scanner.next();
    List<User> pcMembersList = convertUserIdsToUserList(pcMemberIdsInput);

    // Set the state property to CREATED by default
    Conference.ConferenceState defaultState = Conference.ConferenceState.CREATED;

    // Save the conference with the gathered information
    Conference newConference = new Conference(name, description, pcChair, papersList, pcMembersList);
    newConference.setState(defaultState);
    // Save the conference to the database or perform any other necessary operations
    Conference createdConference = conferenceRepository.save(newConference);

    System.out.println("Conference created successfully!");

    return createdConference;
}

public List<Paper> convertPaperIdsToPaperList(String paperIdsInput) {
    // Implement logic to convert comma-separated paper IDs to a list of Paper objects
    // You need to fetch papers from the database based on the provided IDs
    // For simplicity, let's assume PaperRepository has a method findById
    List<Paper> papersList = new ArrayList<>();
    String[] paperIdsArray = paperIdsInput.split(",");
    for (String paperId : paperIdsArray) {
        Long id = Long.parseLong(paperId.trim());
        Paper paper = paperRepository.findById(id).orElse(null);
        if (paper != null) {
            papersList.add(paper);
        }
    }
    return papersList;
}

private List<User> convertUserIdsToUserList(String userIdsInput) {
    // Implement logic to convert comma-separated user IDs to a list of User objects
    // You need to fetch users from the database based on the provided IDs
    // For simplicity, let's assume UserRepository has a method findById
    List<User> pcMembersList = new ArrayList<>();
    String[] userIdsArray = userIdsInput.split(",");
    for (String userId : userIdsArray) {
        Long id = Long.parseLong(userId.trim());
        User user = userService.getUserById(id);
        if (user != null) {
            pcMembersList.add(user);
        }
    }
    return pcMembersList;
}

    
    public void updateConference(Conference inputConference, Scanner scanner) {
            System.out.println("Enter the ID of the conference you want to update:");
            Long conferenceId = scanner.nextLong();

            // Check if the paper with the given ID exists
            Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);

         if (optionalConference.isPresent()) {
             Conference conference = optionalConference.get();
 
             System.out.println("Choose what to update:");
             System.out.println("1. Name");
             System.out.println("2. Description");
             System.out.println("3. PC Chair ID");
             System.out.println("4. Papers (comma-separated IDs)");
             System.out.println("5. PC Members (comma-separated IDs)");
             // Add more options as needed
 
             int updateChoice = scanner.nextInt();
 
             switch (updateChoice) {
                 case 1:
                     System.out.println("Enter the new name:");
                     conference.setName(scanner.next());
                     break;
 
                 case 2:
                     System.out.println("Enter the new description:");
                     conference.setDescription(scanner.next());
                     break;
 
                 case 3:
                     System.out.println("Enter the new PC Chair ID:");
                     Long pcChairId = scanner.nextLong();
                     conference.setPcChairId(pcChairId);
                     break;
 
                 case 4:
                    System.out.println("Enter new paper IDs (comma-separated):");
                    String paperIdsInput = scanner.next();
                    conference.setPapers(paperIdsInput);
                    break;

                 case 5:
                    System.out.println("Enter new PC member IDs (comma-separated):");
                    String pcMemberIdsInput = scanner.next();
                    conference.setPcMembers(pcMemberIdsInput);
                    break;

 
                 default:
                     System.out.println("Invalid choice");
             }
 
             // Save the updated conference
             conferenceRepository.save(conference);
 
 
                System.out.println("Conference updated successfully!");
            } else {
            System.out.println("Conference not found with ID: " + conferenceId);
            }
    // Add other conference-related methods if needed
}

public void addPCMembers(Conference inputConference) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Conference ID: ");
            Long conferenceId = scanner.nextLong();

            System.out.print("Enter PC Member ID: ");
            Long userId = scanner.nextLong();

            Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
            Optional<User> optionalUser = userRepository.findById(userId);

            if(optionalConference.isPresent()){
                Conference conference = optionalConference.get();
                
                if (optionalUser.isPresent()){
                User user = optionalUser.get();
                // Fetch the user's roles
                Set<Role> userRoles = user.getRoles();
                boolean isPCMember = userRoles.stream().anyMatch(role -> role.getState() == Role.RoleName.PC_MEMBER);
                    if(isPCMember){
                    conference.setPcMembers(conference.getPcMembers() + userId + ",");
                    conferenceRepository.save(conference);
                    System.out.println("PC member assigned succesfully");
                }else{System.out.println("User is not a PC Member");}
            }else{ System.out.println("Invalid User ID");}
        }else {
                System.out.println("Invalid Conference ID");
            }
scanner.close();
}

public List<Conference> searchConferences(String name, String description) {
    // Implement the search logic here
    List<Conference> searchResults = conferenceRepository.findByNameContainingAndDescriptionContaining(name, description);

    return searchResults;
}

    public Map<String, Object> getConferenceViewById(Long conferenceId, User currentUser) {
        Conference conference = conferenceRepository.findById(conferenceId).orElse(null);
        
        if (conference != null) {
            return createConferenceViewBasedOnRole(conference, currentUser);
        }
        
        return null;
    }

    private Map<String, Object> createConferenceViewBasedOnRole(Conference conference, User currentUser) {
        Map<String, Object> conferenceView = new HashMap<>();
    
        conferenceView.put("Conference ID", conference.getConferenceId());
        conferenceView.put("Name", conference.getName());
        conferenceView.put("Description", conference.getDescription());


            // Print the HashMap content
        for (Map.Entry<String, Object> entry : conferenceView.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        return conferenceView;
    }

    public void deleteConference(Long conferenceId) {
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalConference.isPresent()) {
            conferenceRepository.deleteById(conferenceId);
            System.out.println("Conference deleted successfully.");
        } else {
            System.out.println("Conference not found with this ID: " + conferenceId);
        }
    }

     public void startSubmission(Long conferenceId) {
        // Retrieve the conference by ID
        Conference conference = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new IllegalArgumentException("Conference not found"));

        // Check if the current state allows the transition to SUBMISSION
        if (conference.getState() != ConferenceState.CREATED) {
            throw new IllegalStateException("Cannot start submission for a conference that is not in CREATED state");
        }

        // Update the state to SUBMISSION
        conference.setState(ConferenceState.SUBMISSION);

        // Save the updated conference
        conferenceRepository.save(conference);

        // You can log or print a message indicating the transition
        System.out.println("Submission for conference " + conferenceId + " has started.");
    }

    public void startReviewerAssignment(Long conferenceId) {
        // Retrieve the conference by ID
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalConference.isPresent()) {
            Conference conference = optionalConference.get();
    
            // Check if the conference state is SUBMISSION
            if (conference.getState() == ConferenceState.SUBMISSION) {
                // Update the conference state to ASSIGNMENT
                conference.setState(ConferenceState.ASSIGNMENT);
                conferenceRepository.save(conference);
    
                System.out.println("Reviewer assignment started for Conference ID: " + conferenceId);
            } else {
                // If the conference state is not SUBMISSION, throw an exception or handle the error appropriately
                throw new IllegalStateException("Reviewer assignment can only start in the SUBMISSION state");
            }
        } else {
            // If the conference with the given ID is not found, throw an exception or handle the error appropriately
            throw new IllegalArgumentException("Conference with ID " + conferenceId + " not found");
        }
    }

    public void startReview(Long conferenceId) {
        // Retrieve the conference by ID
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalConference.isPresent()) {
            Conference conference = optionalConference.get();
    
            // Check if the conference state is ASSIGNMENT
            if (conference.getState() == ConferenceState.ASSIGNMENT) {
                // Update the conference state to REVIEW
                conference.setState(ConferenceState.REVIEW);
                conferenceRepository.save(conference);
    
                // Now, the review of submitted conference papers is allowed
                // You can add additional logic for the review process here if needed
    
                System.out.println("Review started for Conference ID: " + conferenceId);
            } else {
                // If the conference state is not ASSIGNMENT, throw an exception or handle the error appropriately
                throw new IllegalStateException("Review can only start in the ASSIGNMENT state");
            }
        } else {
            // If the conference with the given ID is not found, throw an exception or handle the error appropriately
            throw new IllegalArgumentException("Conference with ID " + conferenceId + " not found");
        }
    }
    
    public void makeDecision(Long conferenceId) {
        // Retrieve the conference by ID
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalConference.isPresent()) {
            Conference conference = optionalConference.get();
    
            // Check if the conference state is REVIEW
            if (conference.getState() == ConferenceState.REVIEW) {
                // Update the conference state to DECISION
                conference.setState(ConferenceState.DECISION);
                conferenceRepository.save(conference);
    
                // Now, the approval or rejection of submitted papers is allowed
                // You can add additional logic for decision-making here if needed
    
                System.out.println("Decision made for Conference ID: " + conferenceId);
            } else {
                // If the conference state is not REVIEW, throw an exception or handle the error appropriately
                throw new IllegalStateException("Decision making can only happen in the REVIEW state");
            }
        } else {
            // If the conference with the given ID is not found, throw an exception or handle the error appropriately
            throw new IllegalArgumentException("Conference with ID " + conferenceId + " not found");
        }
    }
    
    public void startFinalSubmission(Long conferenceId) {
        // Retrieve the conference by ID
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
    
        if (optionalConference.isPresent()) {
            Conference conference = optionalConference.get();
    
            // Check if the conference state is DECISION
            if (conference.getState() == ConferenceState.DECISION) {
                // Update the conference state to FINAL_SUBMISSION
                conference.setState(ConferenceState.FINAL_SUBMISSION);
                conferenceRepository.save(conference);
    
                // Now, the final submission of approved papers is allowed
                // You can add additional logic for final submission here if needed
    
                System.out.println("Final submission started for Conference ID: " + conferenceId);
            } else {
                // If the conference state is not DECISION, throw an exception or handle the error appropriately
                throw new IllegalStateException("Final submission can only start in the DECISION state");
            }
        } else {
            // If the conference with the given ID is not found, throw an exception or handle the error appropriately
            throw new IllegalArgumentException("Conference with ID " + conferenceId + " not found");
        }
    }
    
    public void endConference(Conference inputConference) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter conference ID to make end conference: ");
        long conferenceId = scanner.nextInt();
        
        Optional<Conference> optionalConference = conferenceRepository.findById(conferenceId);
        
        if (optionalConference.isPresent()) {
            Conference conference = optionalConference.get();
        if (conference.getState() != ConferenceState.FINAL_SUBMISSION) {
            scanner.close();
            return;
        }
 
        // Update the state of the conference to FINAL
        conference.setState(ConferenceState.FINAL);
        // Save the updated conference state
        conferenceRepository.save(conference);
        // Mark approved papers as ACCEPTED and reject others
        markPapersAccordingToFinalSubmission(conference);

        System.out.println("Conference ended successfully.");
        scanner.close();
    } else {
        System.out.println("Conference not found.");
    }
        
} 

private void markPapersAccordingToFinalSubmission(Conference conference) {
    List<Paper> approvedPapers = paperRepository.findByConferenceAndState(conference, Paper.PaperState.APPROVED);

    for (Paper paper : approvedPapers) {
        if (paperService.finalSubmitPaper(paper)) {
            paper.setState(Paper.PaperState.ACCEPTED);
        } else {
            paper.setState(Paper.PaperState.REJECTED);
        }
    }

    // Save the updated states of papers
    paperRepository.saveAll(approvedPapers);
}

}