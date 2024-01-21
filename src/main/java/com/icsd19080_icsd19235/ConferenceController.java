package com.icsd19080_icsd19235;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/conferences")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    @GetMapping
    public List<Conference> getAllConferences() {
        return conferenceService.getAllConferences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conference> getConference(@PathVariable Long id) {
        Conference conference = conferenceService.getConference(id);
        return ResponseEntity.ok(conference);
    }

    @PostMapping
    public ResponseEntity<Conference> createConference(@RequestBody Conference conference) {
        Scanner scanner = new Scanner(System.in);
        Conference createdConference = conferenceService.createConference(conference,scanner);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConference);
    }

    // Add other conference-related endpoints
}
