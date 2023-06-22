package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleReportDTO> findByReport(String minDate, String maxDate,
											String name) {
		if (minDate.length() == 0 && maxDate.length() == 0) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate oneYearAgo = LocalDate.now(ZoneId.systemDefault()).minusYears(1);
			List<SaleReportDTO> result = repository.search2(oneYearAgo,
					today, name);
			return result;
		} else if (maxDate.length() == 0) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			List<SaleReportDTO> result = repository.search2(LocalDate.parse(minDate),
					today, name);
			return result;
		} else {
			List<SaleReportDTO> result = repository.search2(LocalDate.parse(minDate),
					LocalDate.parse(maxDate), name);
			return result;
		}
	}

	public List<SaleSummaryDTO> findBySummary(String minDate, String maxDate) {
		if (minDate.length() == 0 && maxDate.length() == 0) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate oneYearAgo = LocalDate.now(ZoneId.systemDefault()).minusYears(1);
			List<SaleSummaryDTO> result = repository.search1(oneYearAgo,
					today);
			return result;
		} else if (maxDate.length() == 0) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			List<SaleSummaryDTO> result = repository.search1(LocalDate.parse(minDate),
					today);
			return result;
		} else {
			List<SaleSummaryDTO> result = repository.search1(LocalDate.parse(minDate),
					LocalDate.parse(maxDate));
			return result;
		}
	}
}