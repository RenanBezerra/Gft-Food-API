package com.example.demo.infrastructure.service.report;

import org.springframework.stereotype.Service;

import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.service.VendaReportService;

@Service
public class PdfVendaReportService implements VendaReportService {

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		return null;
	}

}
