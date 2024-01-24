Εργασία για το εργαστήριο απο τους φοιτητές icsd19080 Καραγιάννης Νικόλαος, icsd19235 Σωτήριος Φλασκής

1. Στο PaperRepository.java βάλτε σε σχόλια την τελευταία μέθοδο, γραμμη 28 **List<Paper> findByConferenceAndState(Conference conference, PaperState state);**
   
2. Στο ConferenceService.java βάλτε σε σχόλια την τελευταία μέθοδο **private void markPapersAccordingToFinalSubmission(Conference conference)** απο γραμμή 420 εως τέλος, και στην μέθοδο **public void endConference(Conference inputConference)** σε σχόλια την γραμμή 410 markPapersAccordingToFinalSubmission(conference);
  
3. Στη Main.java αντικαταστήστε την γραμμή 45 από **if (loggedInUser != null)** σε **while (loggedInUser != null)** και στη γραμμή 250 προσθέστε στο default τη γραμμή **System.exit(0);** για να τερματτίσετε τη λειτουργία του κώδικα

