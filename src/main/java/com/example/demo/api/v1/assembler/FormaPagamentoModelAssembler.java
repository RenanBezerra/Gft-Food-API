package com.example.demo.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.demo.api.v1.GftLinks;
import com.example.demo.api.v1.controller.FormaPagamentoController;
import com.example.demo.api.v1.model.FormaPagamentoModel;
import com.example.demo.core.security.GftSecurity;
import com.example.demo.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler
		extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	@Autowired
	private GftLinks gftLinks;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GftSecurity gftSecurity;

	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);

		modelMapper.map(formaPagamento, formaPagamentoModel);

		if (gftSecurity.podeConsultarFormasPagamento()) {

			formaPagamentoModel.add(gftLinks.linkToFormasPagamento("formasPagamento"));
		}

		return formaPagamentoModel;
	}

	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {

		CollectionModel<FormaPagamentoModel> collectionModel = super.toCollectionModel(entities);

		if (gftSecurity.podeConsultarFormasPagamento()) {

			collectionModel.add(gftLinks.linkToFormasPagamento());
		}
		return collectionModel;
	}

}
