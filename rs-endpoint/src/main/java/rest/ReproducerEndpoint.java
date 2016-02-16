package rest;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import service.HelloService;
import service.RuntimeEngineFactory;
import service.RuntimeEngineFactory.Configuration;


@Path("/")
public class ReproducerEndpoint {
    @Inject
    HelloService helloService;
    
    @Inject
    RuntimeEngineFactory runtimeEngineFactory;

    @GET
    @Path("/test/{timeout}")
    @Produces({ "application/json" })
    public String getHelloWorldJSON(@PathParam("timeout") final int timeout) {
        return "{\"result\":\"" + helloService.createHelloMessage("World", timeout) + "\"}";
    }
    
    @POST
    @Path("/configuration")
    @Consumes({"application/json"})
    public Response configuration(Configuration configuration){
    	runtimeEngineFactory.configure(configuration);
    	return Response.ok().build();
    }
    
    @GET
    @Path("/configuration")
    @Produces(MediaType.APPLICATION_JSON)
    public Configuration configuration(){
    	return runtimeEngineFactory.currentConfiguration();
    }
    
    @POST
    @Path("/run")
    public Response run(Integer timeout){
    	Map<String, Object> parameters = new HashMap<>();
    	parameters.put("timeout", timeout);
    	runtimeEngineFactory.getRuntimeEngine().getKieSession().startProcess("reproducer-process", parameters);
    	return Response.ok().build();
    }
    
    
    
    
    

}
