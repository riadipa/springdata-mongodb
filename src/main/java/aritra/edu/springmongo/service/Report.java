package aritra.edu.springmongo.service;

import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class generates report processing multiple document collections in mongodb
 */
@Component
public class Report {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * This method process the 'students' collection and 'courses' collection by map-reduce method
     * and generates a new collection 'coursereport'
     *
     * @return a new collection 'coursereport'
     */
    public List<Document> getCourseReport() {

		/*
		This step runs map-reduce method on 'courses' collection and creates a new collection 'coursereport' in memory.
		If the collection exists in memory it will replace the data. The replace is done by outputTypeReplace option.
		 */
        mongoTemplate.mapReduce("courses", "classpath:mongo-scripts/map_course_report_number.js",
                "classpath:mongo-scripts/reduce_course_report.js",
                MapReduceOptions.options().outputTypeReplace().outputCollection("coursereport"), DBObject.class);
		
		/*
		This step runs map-reduce method on 'students' collection and is merged into the collection 'coursereport' in memory.
		The merge or reduce with the existing collection is done by the outputTypeReduce option
		 */
        MapReduceResults<DBObject> result = mongoTemplate.mapReduce("students",
                "classpath:mongo-scripts/map_course_report_names.js", "classpath:mongo-scripts/reduce_course_report.js",
                MapReduceOptions.options().outputTypeReduce().outputCollection("coursereport"), DBObject.class);

        Iterator<DBObject> i = result.iterator();

        List<Document> list = new ArrayList<>();
        while (i.hasNext()) {
            Document doc = (Document) i.next();
            list.add(doc);
        }

        return list;
    }

}
