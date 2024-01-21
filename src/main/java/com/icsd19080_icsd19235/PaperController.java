package com.icsd19080_icsd19235;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Scanner;

@RestController
@RequestMapping("/papers")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Paper> getAllPapers() {
        return paperService.getAllPapers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paper> getPaperById(@PathVariable Long id) {
        Paper paper = paperService.getPaperById(id);
        return ResponseEntity.ok(paper);
    }

    @PostMapping
    public ResponseEntity<Paper> createPaper(@RequestBody Paper paper) {
        Scanner scanner = new Scanner(System.in); // Example: You might want to inject this or handle it differently
        Paper createdPaper = paperService.createPaper(paper, scanner);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaper);
    }
    

    @GetMapping("/view/{paperId}")
    public String viewPaper(@PathVariable Long paperId, Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Map<String, Object> paperView = paperService.getPaperViewById(paperId, currentUser);

        if (paperView != null) {
            model.addAttribute("paper", paperView);
            return "paper/view";
        } else {
            return "redirect:/papers"; // Redirect to paper list or handle accordingly
        }
    }
}

 
