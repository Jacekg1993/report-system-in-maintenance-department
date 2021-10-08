package com.jacekg.reportSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jacekg.reportSystem.dao.FailTypeDao;
import com.jacekg.reportSystem.dao.ProductionLineDao;
import com.jacekg.reportSystem.dao.ProductionMachineDao;
import com.jacekg.reportSystem.dao.ReportDao;
import com.jacekg.reportSystem.dao.UserDao;
import com.jacekg.reportSystem.dao.UserDaoImpl;
import com.jacekg.reportSystem.dto.ReportDto;
import com.jacekg.reportSystem.entity.FailType;
import com.jacekg.reportSystem.entity.ProductionLine;
import com.jacekg.reportSystem.entity.ProductionMachine;
import com.jacekg.reportSystem.entity.Report;
import com.jacekg.reportSystem.entity.User;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private FailTypeDao failTypeDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductionMachineDao productionMachineDao;
	
	@Autowired
	private ProductionLineDao productionLineDao;
	
	@Override
	@Transactional
	public List<FailType> getFailTypes() {
		return failTypeDao.getFailTypes();
	}

	@Override
	@Transactional
	public void saveReport(ReportDto reportDto) {
		
		Report report = new Report();
		User user = userDao.getUser(reportDto.getUserId());
		ProductionMachine productionMachine = productionMachineDao.getProdMachine(reportDto.getProductionMachineId());
		ProductionLine productionLine = productionMachine.getProductionLine();
		
		//TODO saving images and add to report
		report.setUser(user);
		report.setDate(reportDto.getDate());
		report.setDuration(reportDto.getDuration());
		report.setDescription(reportDto.getDescription());
		report.setProductionLine(productionLine);
		report.setProductionMachine(productionMachine);
		
		
		
		reportDao.saveReport(reportDto);
	}
	
	

}
