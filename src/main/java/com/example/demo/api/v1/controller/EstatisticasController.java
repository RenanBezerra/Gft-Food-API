package com.example.demo.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.model.dto.VendaDiaria;
import com.example.demo.domain.service.VendaQueryService;
import com.example.demo.domain.service.VendaReportService;

import lombok.var;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

	@Autowired
	private VendaQueryService vendaQueryService;

	@Autowired
	private VendaReportService VendaReportService;
	
	@Autowired
	private GftLinks gftLinks;
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
		var estatisticasModel = new EstatisticasModel();
		
		estatisticasModel.add(gftLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
		
		return estatisticasModel;
	}

	@Override
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendaDiarias(filtro, timeOffset);
	}

	@Override
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendaDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		byte[] bytesPdf = VendaReportService.emitirVendasDiarias(filtro, timeOffset);

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
	}


	public class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
		
	}

}