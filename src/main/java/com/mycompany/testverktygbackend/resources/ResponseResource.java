package com.mycompany.testverktygbackend.resources;

import com.mycompany.testverktygbackend.models.Response;
import com.mycompany.testverktygbackend.repositories.ResponseRepository;
import com.mycompany.testverktygbackend.services.ResponseService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rille
 */

@Path("/responses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)  

public class ResponseResource {
    
    ResponseService rr = new ResponseService();

@GET    
public List<Response> getAllResponse() {
    return rr.getAllResponses();
}    
}
