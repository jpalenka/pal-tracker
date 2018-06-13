package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.certpath.OCSPResponse;

import java.util.List;


@RestController
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository=timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        ResponseEntity responseEntity= new ResponseEntity(timeEntryRepository.create(timeEntryToCreate),HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry=timeEntryRepository.find(id);
        if(null!=timeEntry) {
            return new ResponseEntity(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);

    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry newTime) {
        TimeEntry timeEntry=timeEntryRepository.update(id,newTime);
        if(null!=timeEntry) {
            return new ResponseEntity(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
