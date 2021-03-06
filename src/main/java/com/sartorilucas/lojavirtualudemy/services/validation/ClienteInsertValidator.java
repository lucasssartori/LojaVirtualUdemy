package com.sartorilucas.lojavirtualudemy.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sartorilucas.lojavirtualudemy.domain.Cliente;
import com.sartorilucas.lojavirtualudemy.domain.enuns.TipoCliente;
import com.sartorilucas.lojavirtualudemy.dto.ClienteNewDTO;
import com.sartorilucas.lojavirtualudemy.repositories.ClienteRepository;
import com.sartorilucas.lojavirtualudemy.resources.excepitons.FieldMessage;
import com.sartorilucas.lojavirtualudemy.services.validation.utils.BR; 
 
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { 
	
	@Autowired
	private ClienteRepository repo;
	
    @Override
    public void initialize(ClienteInsert ann) {}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { 
        List<FieldMessage> list = new ArrayList<>();
        
        
        
        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
        	list.add(new FieldMessage("CpfOuCnpj", "CPF Inválido"));
        }
        
        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
        	list.add(new FieldMessage("CpfOuCnpj", "CNPJ Inválido"));
        }
        
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null) {
        	list.add(new FieldMessage("Email", "Email já existente!"));
        }
        
		
		// inclua os testes aqui, inserindo erros na lista               
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	} 
}