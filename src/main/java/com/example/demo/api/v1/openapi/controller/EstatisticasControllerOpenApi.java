package com.example.demo.api.v1.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.api.v1.controller.EstatisticasController.EstatisticasModel;
import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Estatisticas")
public interface EstatisticasControllerOpenApi {
	
	@ApiOperation(value = "Estatisticas", hidden = true)
	EstatisticasModel estatisticas();

	@ApiOperation("Consultas estatisticas de vendas diárias")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", dataType = "int"),
			@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora final da criação do pedido", example = "2019-12-01T00:00:00Z", dataType = "date-time"),
			@ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido", example = "2019-12-02T23:59:59Z", dataType = "date-time") })
	List<VendaDiaria> consultarVendaDiarias(

			VendaDiariaFilter filtro,

			@ApiParam(value = "Deslocamento de horario a ser considerado na consulta em relação ao UTC", defaultValue = "+00:00") String timeOffset);

	ResponseEntity<byte[]> consultarVendaDiariasPdf(VendaDiariaFilter filtro,

			String timeOffset);
	
	

}
