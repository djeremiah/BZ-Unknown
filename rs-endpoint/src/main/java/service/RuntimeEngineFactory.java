package service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.remote.client.api.RemoteRestRuntimeEngineBuilder;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;

@ApplicationScoped
public class RuntimeEngineFactory {

	private RemoteRestRuntimeEngineBuilder runtimeEngineBuilder = null;
	private Configuration configuration; 
	
	@PostConstruct
	public void defaultConfiguration(){
		configuration = new Configuration("http://localhost:8080/business-central/", "bpmsAdmin", "");
		reloadBuilder();
	}
	

	public RuntimeEngine getRuntimeEngine() {
		if (runtimeEngineBuilder != null) {
			return runtimeEngineBuilder.build();
		} else {
			return null;
		}
	}
	
	public void configure(Configuration configuration){
		this.configuration = configuration;
		reloadBuilder();
	}
	
	public Configuration currentConfiguration(){
		return configuration;
	}
	
	

	private void reloadBuilder() {
		URL instanceUrl;
		try {
			instanceUrl = new URL(configuration.url);
		} catch (MalformedURLException e) {
			runtimeEngineBuilder = null;
			throw new IllegalArgumentException(e);
		}

		runtimeEngineBuilder = RemoteRuntimeEngineFactory.newRestBuilder()
				.addUrl(instanceUrl)
				.addDeploymentId("reproducer:process:1.0")
				.addUserName(configuration.userName)
				.addPassword(configuration.password);

	}

	public static class Configuration {
		public String url;
		public String userName;
		public String password;
		public Configuration() {
		}
		
		public Configuration(String url, String userName, String password) {
			super();
			this.url = url;
			this.userName = userName;
			this.password = password;
		}
		
		


	}

}
