package com.relurori.hanblocks;


public class Spreadsheet {

    private String title;
    private String worksheetFeed;
    private final SpreadsheetsService service;
    

    public String getTitle() {
        return title;
    }

    Spreadsheet(SpreadsheetsService service, String title, String worksheetFeed) {
        this.title = title;
        this.worksheetFeed = worksheetFeed;
        this.service = service;
    }
    
    
}
