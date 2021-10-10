package com.jacekg.reportSystem.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jacekg.reportSystem.dto.ReportDto;
import com.jacekg.reportSystem.entity.FailType;
import com.jacekg.reportSystem.entity.ProductionMachine;
import com.jacekg.reportSystem.entity.Report;
import com.jacekg.reportSystem.service.ProductionService;
import com.jacekg.reportSystem.service.ReportService;
import com.jacekg.reportSystem.service.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserService userService;

	private Map<Integer, String> prodMachines;
	private Map<Integer, String> failTypes;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);	
		
//		dataBinder.registerCustomEditor(LocalDate.class,     
//                 new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10)); 
	}

	@GetMapping("/showReportForm")
	public String showReportForm(Model model, Principal principal) {
		
		String userName = principal.getName();
		Long userId = userService.getUserId(userName);
		System.out.println(userId);
		
		prodMachines = new LinkedHashMap<Integer, String>();
		prodMachines = loadProdMachines();
		
		failTypes = new LinkedHashMap<Integer, String>();
		failTypes = loadFailTypes();
		
		ReportDto reportDto = new ReportDto();
		reportDto.setDate(LocalDate.now());
		reportDto.setUserId(userId);
			
		model.addAttribute("prodMachines", prodMachines);
		model.addAttribute("failTypes", failTypes);
		model.addAttribute("reportDto", reportDto);

		return "report-form";
	}
	
	@PostMapping("/processReportForm")
	public String processReportForm(@Valid @ModelAttribute("reportDto") ReportDto reportDto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			
			prodMachines = new LinkedHashMap<Integer, String>();
			prodMachines = loadProdMachines();
			
			failTypes = new LinkedHashMap<Integer, String>();
			failTypes = loadFailTypes();
			
			model.addAttribute("prodMachines", prodMachines);
			model.addAttribute("failTypes", failTypes);
			
			return "report-form";
		}
		
		System.out.println("My log process report form");
		System.out.println(reportDto.toString());
		
		try {
			reportService.saveReport(reportDto);
		} catch (Exception e) {
			return "redirect:/report/showReportForm";
		}
		
		return "redirect:/report/showReportForm";
	}
	
	@GetMapping("/showReportList")
	public String showReportList(Model model) {
		
		List<Report> reports = reportService.getReports();
		System.out.println(reports.get(0).toString());
		model.addAttribute("reports", reports);
		
		return "report-list";
	}
	
	
	private Map<Integer, String> loadProdMachines() {
		
		List<ProductionMachine> prodMachineList = productionService.getProdMachinesWithLines();

		Map<Integer, String> prodMachines = new LinkedHashMap<Integer, String>();

		prodMachines = getProdMachineNames(prodMachineList);

		return prodMachines;
	}

	private Map<Integer, String> getProdMachineNames(List<ProductionMachine> prodMachineList) {
		
		Map<Integer, String> prodMachineNames = new LinkedHashMap<Integer, String>();

		for (ProductionMachine productionMachine : prodMachineList) {

			prodMachineNames.put(
					productionMachine.getId(), 
					productionMachine.getProductionLine().getName() + "-" + productionMachine.getName());
		}

		return prodMachineNames;
	}
	

//	private Map<Integer, String> loadProdLines() {
//
//		List<ProductionLine> prodLineList = productionService.getProdLines();
//
//		Map<Integer, String> prodLines = new LinkedHashMap<Integer, String>();
//
//		prodLines = getProdLineNames(prodLineList);
//
//		return prodLines;
//	}

//	private Map<Integer, String> getProdLineNames(List<ProductionLine> prodLineList) {
//
//		Map<Integer, String> prodLineNames = new LinkedHashMap<Integer, String>();
//
//		for (ProductionLine productionLine : prodLineList) {
//
//			prodLineNames.put(productionLine.getId(), productionLine.getName());
//		}
//
//		return prodLineNames;
//	}

	private Map<Integer, String> loadFailTypes() {

		List<FailType> failTypeList = reportService.getFailTypes();

		Map<Integer, String> failTypes = new LinkedHashMap<Integer, String>();

		failTypes = getFailTypeNames(failTypeList);

		return failTypes;
	}

	private Map<Integer, String> getFailTypeNames(List<FailType> failTypeList) {

		LinkedHashMap<Integer, String> failTypeNames = new LinkedHashMap<Integer, String>();

		for (FailType failType : failTypeList) {

			failTypeNames.put(failType.getId(), failType.getName());
		}

		return failTypeNames;

	}



}












