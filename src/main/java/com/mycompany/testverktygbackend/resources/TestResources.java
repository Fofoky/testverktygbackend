package com.mycompany.testverktygbackend.resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.mycompany.testverktygbackend.models.Course;
import com.mycompany.testverktygbackend.models.Test;
import com.mycompany.testverktygbackend.repositories.CourseRepository;
import com.mycompany.testverktygbackend.services.TestServices;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)    
public class TestResources {
    
        TestServices ts = new TestServices();
        
@GET
public List<Test> getAllTest() {
    return ts.getAllTests();

    @POST
    public Test addTest(@PathParam("courseId") int courseId, Test test) {
        CourseRepository cs = new CourseRepository();
        Course course = cs.getCourse(courseId);
        test.setCourse(course);
        TestServices ts = new TestServices();
        return ts.addTest(test);
    }

    @Path("/{testId}/questions")
    public QuestionResources question() {
        return new QuestionResources();
    }
}



}