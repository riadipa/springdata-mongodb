package aritra.edu.springmongo.controller;

import aritra.edu.springmongo.service.Report;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class ReportController {

    Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private Report report;

    /**
     * @return the report generated after processing 'students' collection and 'courses' collection by map-reduce method
     */
    @RequestMapping(method = RequestMethod.GET, value = "/report")
    public ResponseEntity<List<Document>> getReport() {

        logger.info("in report controller : /report");

        List<Document> list = report.getCourseReport();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
