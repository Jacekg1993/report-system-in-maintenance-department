package com.jacekg.reportSystem.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jacekg.reportSystem.dto.ReportDto;
import com.jacekg.reportSystem.entity.FailType;
import com.jacekg.reportSystem.entity.Report;

@Repository
public class ReportDaoImpl implements ReportDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveReport(Report report) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(report);
	}

	@Override
	public List<Report> getReports() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Report> query = currentSession.createQuery("FROM Report", Report.class);
		
		return query.getResultList();
	}

}
