package com.jacekg.reportSystem.service;

import java.util.List;

import com.jacekg.reportSystem.dto.ReportDto;
import com.jacekg.reportSystem.entity.FailType;
import com.jacekg.reportSystem.entity.Report;

public interface ReportService {

	List<FailType> getFailTypes();

	void saveReport(ReportDto reportDto);

	List<Report> getReportsToShowList();

}
