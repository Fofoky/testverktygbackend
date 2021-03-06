package com.mycompany.testverktygbackend.resources;

import com.mycompany.testverktygbackend.models.Question;
import com.mycompany.testverktygbackend.models.Response;
import com.mycompany.testverktygbackend.services.QuestionService;
import com.mycompany.testverktygbackend.services.ResponseService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
public List<Response> getAllResponse(@PathParam("questionId") int questionId) {
    return rr.getAllResponses(questionId);
}

@POST 
public Response addResponse(@PathParam("testId") int testId, @PathParam("questionId") int questionId, Response response){
    System.out.println("Kommer in i addResponse i resource.");
    QuestionService qs = new QuestionService(); // adding lines so it works, once paths is fixed I think we can remove these lines;
    List<Question> questions = qs.getQuestions(testId);
        for(Question q : questions){
            if(q.getQuestionId() == questionId){
                response.setQuestion(q);
            }
        }
    
    return rr.addResponse(response);
}
}
