// Main.java
package com.icsd19080_icsd19235;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(PaperService paperService, PaperRepository paperRepository, UserService userService, UserRepository userRepository, UserServiceImpl userServiceImpl, ConferenceService conferenceService, ConferenceRepository conferenceRepository) {
        return (args) -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Log in");
            System.out.println("2. Create an account");

            int loginChoice = scanner.nextInt();

            User loggedInUser = null;

            switch (loginChoice) {
                case 1:
                    loggedInUser = userServiceImpl.login();
                    break;
                case 2:
                    User newUser = new User();
                    userServiceImpl.createUser(newUser);
                    loggedInUser = userServiceImpl.login();
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
                    scanner.close();
                    return;
            }

    if (loggedInUser != null) {


            System.out.println("Choose an option:");
            System.out.println("1. Access Paper");
            System.out.println("2. Access Conference");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Access Paper Menu
                    System.out.println("Choose an option:");
                    System.out.println("1. Create Paper");
                    System.out.println("2. Update Paper");
                    System.out.println("3. Add co-Author --den leitourgei");
                    System.out.println("4. Submit Paper");
                    System.out.println("5. Review Paper");
                    System.out.println("6. Submit Reviewed Paper");
                    System.out.println("7. Approved Paper");
                    System.out.println("8. Rejected Paper");
                    System.out.println("9. Final Submit Paper");
                    System.out.println("10. Accept Paper");
                    System.out.println("11. Search Paper");
                    System.out.println("12. View Paper");
                    System.out.println("13. Withdraw Paper");

                    int paperChoice = scanner.nextInt();

                    switch (paperChoice) {
                        case 1:
                            Paper newPaper= new Paper();
                            paperService.createPaper(newPaper,scanner);
                            break;

                        case 2:
                            Paper updatePaper= new Paper();
                            paperService.updatePaper(updatePaper,scanner);
                            break;

                        case 3:
                            // DEN LEITOURGEI SWSTA
                            //Paper coAuthorPaper= new Paper();
                            //paperService.addCoAuthor(coAuthorPaper,scanner);
                            break;

                        case 4:
                            Paper submitPaper= new Paper();
                            Conference submitConference = new Conference();
                            paperService.submitPaper(submitPaper, submitConference);
                            break;

                        case 5:
                            Paper reviewPaper = new Paper();
                            Role reviewer = new Role();
                            paperService.assignReviewer(reviewer, reviewPaper);
                            break;
                        case 6:
                            Paper submitReviewPaper = new Paper();
                            paperService.submitPaperReview(submitReviewPaper);
                            break;
                        case 7:
                            Paper approvePaper = new Paper();
                            paperService.approvePaper(approvePaper);
                            break;
                        case 8:
                            Paper rejectedPaper = new Paper();
                            paperService.rejectPaper(rejectedPaper);
                            break;
                        case 9:
                            Paper fSubmitPaper = new Paper();
                            paperService.finalSubmitPaper(fSubmitPaper);
                            break;
                        case 10:
                            Paper acceptPaper = new Paper();
                            paperService.acceptPaper(acceptPaper);
                            break;
                        case 11:
                            Scanner sc = new Scanner(System.in);
                            System.out.print("Enter paper title (or press Enter to skip): ");
                            String title = sc.nextLine();
                    
                            System.out.print("Enter paper authors (or press Enter to skip): ");
                            String authors = sc.nextLine();
                    
                            System.out.print("Enter paper abstract (or press Enter to skip): ");
                            String abstractText = sc.nextLine();

                            List<Paper> searchResults = paperService.searchPaper(title, authors, abstractText, loggedInUser);
                            System.out.println("Search Results:");
                            for (Paper paper : searchResults) {
                                System.out.println("Paper ID: " + paper.getPaperId() + ", Title: " + paper.getTitle());
                            }
                        break;
                        case 12:
                            System.out.print("Enter paper ID to view: ");
                            long paperId = scanner.nextInt();
                            paperService.getPaperViewById(paperId,loggedInUser);
                            break;
                        case 13:
                            System.out.print("Enter paper ID to delete: ");
                            long paperIdd = scanner.nextInt();
                            paperService.withdrawPaper(paperIdd);
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                    break;

                case 2:
                    // Access Conference Menu
                    System.out.println("Choose an option:");
                    System.out.println("1. Create Conference");
                    System.out.println("2. Update Conference");
                    System.out.println("3. PC Members addition in Conference");
                    System.out.println("4. Search Conference");
                    System.out.println("5. View Conference");
                    System.out.println("6. Delete Conference");
                    System.out.println("7. Update Create to Submission");
                    System.out.println("8. Update Submission to Assignment");
                    System.out.println("9. Update Assignment to Review");
                    System.out.println("10. Update Review to Decision");
                    System.out.println("11. Update Decision to Final Submission");
                    System.out.println("12. Update Final Submissn to Final");

                    int conferenceChoice = scanner.nextInt();

                    switch (conferenceChoice) {
                        case 1:
                            Conference newConference = new Conference();
                            conferenceService.createConference(newConference, scanner);
                            break;

                        case 2:
                            Conference updateConference = new Conference();
                            conferenceService.updateConference(updateConference, scanner);
                            break;
                        case 3:
                            Conference pcMemmberAddConference = new Conference();
                            conferenceService.addPCMembers(pcMemmberAddConference);
                            break;
                        case 4:
                            System.out.print("Enter conference name (or press Enter to skip): ");
                            String conferenceName = scanner.nextLine();
                    
                            System.out.print("Enter conference description (or press Enter to skip): ");
                            String conferenceDescription = scanner.nextLine();
                    
                            List<Conference> searchResults = conferenceService.searchConferences(conferenceName, conferenceDescription);

                            // Display the search results
                            System.out.println("Search Results:");
                            for (Conference conference : searchResults) {
                                System.out.println("Conference ID: " + conference.getConferenceId() + ", Name: " + conference.getName());
                                // Display other relevant information about the conference
                            }
                            break;
                        case 5:
                            System.out.print("Enter conference ID to view: ");
                            long conferenceId = scanner.nextInt();
                            User currentUser5 = new User();
                            conferenceService.getConferenceViewById(conferenceId,currentUser5);
                            break;
                        case 6:
                            System.out.print("Enter conference ID to delete: ");
                            long conferenceIdd = scanner.nextInt();
                            conferenceService.deleteConference(conferenceIdd);
                            break;
                        case 7:
                            System.out.print("Enter conference ID to change from Create to Submit: ");
                            long createToSubmit = scanner.nextInt();
                            conferenceService.startSubmission(createToSubmit);
                            break;
                        case 8:
                            System.out.print("Enter conference ID to start the review assignment: ");
                            long reviewToConference = scanner.nextInt();
                            conferenceService.startReviewerAssignment(reviewToConference);
                            break;
                        case 9:
                            System.out.print("Enter conference ID to start the review: ");
                            long reviewToConference2 = scanner.nextInt();
                            conferenceService.startReview(reviewToConference2);
                            break;

                        case 10:
                            System.out.print("Enter conference ID to make decision: ");
                            long decisionConference = scanner.nextInt();
                            conferenceService.makeDecision(decisionConference);
                            break;

                        case 11:
                            System.out.print("Enter conference ID to make into final submission: ");
                            long finalSubConference = scanner.nextInt();
                            conferenceService.startFinalSubmission(finalSubConference);
                            break;

                        case 12:
                            Conference endConference = new Conference();
                            conferenceService.endConference(endConference);
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

            // Close the scanner
            scanner.close();
        };
    }

    
}
