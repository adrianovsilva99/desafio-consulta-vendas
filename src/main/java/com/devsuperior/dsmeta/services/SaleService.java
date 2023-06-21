package com.devsuperior.dsmeta.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> findBySummary(String minDate, String maxDate) {
		if (maxDate == "") {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			maxDate = simpleDateFormat.format(today);
		}
		if (minDate == "") {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			LocalDate oneYear = today.minusYears(1L);
			minDate = simpleDateFormat.format(oneYear);
		}
		List<SaleSummaryDTO> result = repository.search1(minDate, maxDate);
		return result;
	}
}