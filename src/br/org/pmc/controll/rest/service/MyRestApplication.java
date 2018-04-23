package br.org.pmc.controll.rest.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.org.pmc.controll.rest.service.impl.FichaProcessoRestService;
import br.org.pmc.controll.rest.service.impl.FolhaRequerimentoRestService;
import br.org.pmc.controll.rest.service.impl.GenerateDataBaseRestService;
import br.org.pmc.controll.rest.service.impl.LogDeAtividadeRestService;
import br.org.pmc.controll.rest.service.impl.LoginRestService;
import br.org.pmc.controll.rest.service.impl.OrgaoMovimentacaoRestService;
import br.org.pmc.controll.rest.service.impl.RequerenteRestService;
import br.org.pmc.controll.rest.service.impl.SecretariaRestService;
import br.org.pmc.controll.rest.service.impl.ServicoRestService;
import br.org.pmc.controll.rest.service.impl.UserProfileRestService;
import br.org.pmc.controll.rest.service.impl.UserRestService;

public class MyRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public MyRestApplication() {

		// Serviços REST
		singletons.add(new GenerateDataBaseRestService());
		singletons.add(new LoginRestService());
		singletons.add(new UserRestService());
		singletons.add(new SecretariaRestService());
		singletons.add(new LogDeAtividadeRestService());
		singletons.add(new UserProfileRestService());
		singletons.add(new FichaProcessoRestService());
		singletons.add(new FolhaRequerimentoRestService());
		singletons.add(new ServicoRestService());
		singletons.add(new RequerenteRestService());
		singletons.add(new OrgaoMovimentacaoRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}