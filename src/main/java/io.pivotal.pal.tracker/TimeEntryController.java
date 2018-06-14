package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TimeEntryController {

    private final CounterService counter;
    private final GaugeService gauge;

    TimeEntryRepository timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntriesRepo, CounterService counter,
                               GaugeService gauge) {
        this.timeEntriesRepo = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
        }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        ResponseEntity responseEntity= new ResponseEntity(timeEntriesRepo.create(timeEntryToCreate),HttpStatus.CREATED);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());
        return responseEntity;
    }

    @RequestMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry= timeEntriesRepo.find(id);
        if(null!=timeEntry) {
            counter.increment("TimeEntry.read");
            return new ResponseEntity(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<List<TimeEntry>>(timeEntriesRepo.list(), HttpStatus.OK);

    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry newTime) {
        TimeEntry timeEntry= timeEntriesRepo.update(id,newTime);
        if(null!=timeEntry) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        timeEntriesRepo.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
