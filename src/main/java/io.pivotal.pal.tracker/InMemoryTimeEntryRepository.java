package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    List<TimeEntry> timeEntryList=new ArrayList<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry, int time){
            timeEntry.setId(timeEntryList.size()+1);
        timeEntryList.add(timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
            timeEntry.setId(timeEntryList.size()+1);
        timeEntryList.add(timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        try{
            return timeEntryList.get((int) id-1);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntryList;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        try {
            timeEntry.setId(id);
            timeEntryList.set((int) id - 1, timeEntry);
            return timeEntryList.get((int) id - 1);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public void delete(long id) {
        try {
            timeEntryList.remove((int) id - 1);
        }catch (Exception e){
            System.out.print("Item to delete not found");
        }
    }



}
