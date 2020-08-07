package com.datex.batch.model;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<SegmentMeasure> report = new ArrayList<>();

    public List<SegmentMeasure> getReport() {
        return report;
    }

    public void setReport(List<SegmentMeasure> report) {
        this.report = report;
    }

}
