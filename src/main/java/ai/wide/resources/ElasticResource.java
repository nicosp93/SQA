package ai.wide.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ai.wide.utils.HTMLHandler;

@Path("elastic")
public class ElasticResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        

        String r = HTMLHandler.restCall("POST", "http://api.wide-eyes.it/SearchByAttributes", 
                "{\"page\":0,\"attributes\":{\"included\":[],\"excluded\":[]},\"filters\":{},\"ranges\":{\"price\":{\"min\":0,\"max\":1000},\"discount\":{\"min\":0,\"max\":100}},\"maxNumResults\":50}", 
                "application/json", null, "28ecd9090551dbe8ca7614ada843a15d7fc9f751");
        return Response.status(200).entity(r).build();
    
    }
}


/*
 * curl -X POST -H "apikey: 28ecd9090551dbe8ca7614ada843a15d7fc9f751" 
 * -H "Content-Type: application/json" 
 * -d '{"page":0,"attributes":{"included":[],"excluded":[]},"filters":{},
 * "ranges":{"price":{"min":0,"max":1000},"discount":{"min":0,"max":100}},"maxNumResults":50}' 
 * http://api.wide-eyes.it/SearchByAttributes
 */