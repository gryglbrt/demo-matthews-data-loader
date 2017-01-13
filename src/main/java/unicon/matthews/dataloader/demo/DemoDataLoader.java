package unicon.matthews.dataloader.demo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unicon.matthews.caliper.Agent;
import unicon.matthews.caliper.Entity;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.Group;
import unicon.matthews.dataloader.DataLoader;
import unicon.matthews.dataloader.MatthewsClient;
import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.Enrollment;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.LineItemCategory;
import unicon.matthews.oneroster.Role;
import unicon.matthews.oneroster.Status;
import unicon.matthews.oneroster.User;

@Component
public class DemoDataLoader implements DataLoader {
  
  @Autowired
  private MatthewsClient matthewsClient;
  
  private Map<String, Map<String,String>> classMetadataMap = new HashMap<>();
  private Map<String, Class> classMap = new HashMap<>();
  private Map<String, Enrollment> teacherEnrollmentMap = new HashMap<>();
  private Map<String, User> studentMap = new HashMap<>();
  private Map<String, Enrollment> studentEnrollmentMap = new HashMap<>();
  private Map<String, LineItem> lineItemMap = new HashMap<>();
  private List<Event> events = new ArrayList<>();
  
  @PostConstruct
  public void init() {
    
    // Class Metadata
    
    Map<String, String> metadata1 = new HashMap<>();   
    metadata1.put(Vocabulary.CLASS_START_DATE, LocalDate.of(2016, 8, 30).toString());
    metadata1.put(Vocabulary.CLASS_END_DATE, LocalDate.of(2016, 12, 11).toString());
    metadata1.put(Vocabulary.SOURCE_SYSTEM, "DEMO");
    
    Map<String, String> metadata2 = new HashMap<>();
    metadata2.put(Vocabulary.CLASS_START_DATE, LocalDate.of(2016, 9, 1).toString());
    metadata2.put(Vocabulary.CLASS_END_DATE, LocalDate.of(2016, 12, 10).toString());
    metadata2.put(Vocabulary.SOURCE_SYSTEM, "DEMO");

    Map<String, String> metadata3 = new HashMap<>();
    metadata3.put(Vocabulary.CLASS_START_DATE, LocalDate.of(2016, 9, 8).toString());
    metadata3.put(Vocabulary.CLASS_END_DATE, LocalDate.of(2016, 12, 13).toString());
    metadata3.put(Vocabulary.SOURCE_SYSTEM, "DEMO");
    
    classMetadataMap.put("metadata1", metadata1);
    classMetadataMap.put("metadata2", metadata2);
    classMetadataMap.put("metadata3", metadata3);

    // Classes
    
    Class class1
    = new Class.Builder()
        .withSourcedId("demo-class-1")
        .withTitle("Introduction to Organic Chemistry")
        .withMetadata(metadata1)
        .withStatus(Status.active)
        .build();
  
    Class class2
      = new Class.Builder()
        .withSourcedId("demo-class-2")
        .withTitle("Advanced Chemistry 303")
        .withMetadata(metadata2)
        .withStatus(Status.active)
        .build();
  
    Class class3
      = new Class.Builder()
        .withSourcedId("demo-class-3")
        .withTitle("MicroBiology 201")
        .withMetadata(metadata3)
        .withStatus(Status.active)
        .build();
  
    classMap.put("class1", class1);
    classMap.put("class2", class2);
    classMap.put("class3", class3);
    
    // Teacher Enrollments
    
    User teacher 
    = new User.Builder()
      .withSourcedId("teacher-sourcedId-1")
      .withRole(Role.teacher)
      .withFamilyName("Wooden")
      .withGivenName("John")
      .withUserId("teacher-userid-1")
      .withStatus(Status.active)
      .build();
  
    Enrollment teacherEnrollment1
      = new Enrollment.Builder()
          .withKlass(class1)
          .withUser(teacher)
          .withPrimary(true)
          .withSourcedId("teacher-enrollment-1")
          .withRole(Role.teacher)
          .withStatus(Status.active)
          .build();

    Enrollment teacherEnrollment2
    = new Enrollment.Builder()
        .withKlass(class2)
        .withUser(teacher)
        .withPrimary(true)
        .withSourcedId("teacher-enrollment-2")
        .withRole(Role.teacher)
        .withStatus(Status.active)
        .build();
    
    Enrollment teacherEnrollment3
    = new Enrollment.Builder()
        .withKlass(class3)
        .withUser(teacher)
        .withPrimary(true)
        .withSourcedId("teacher-enrollment-3")
        .withRole(Role.teacher)
        .withStatus(Status.active)
        .build();
    
    teacherEnrollmentMap.put("teacherEnrollment1", teacherEnrollment1);
    teacherEnrollmentMap.put("teacherEnrollment2", teacherEnrollment2);
    teacherEnrollmentMap.put("teacherEnrollment3", teacherEnrollment3);
    
    // Student Enrollments
    
    String [] fn = {"Mark", "Kate", "Gary", "Steve", "Lucas", "Wyatt", "Ali", "Jessica", "Catherine",
        "Philip", "Pedro", "P.J.", "Nicole", "Eliza", "James", "Kristen", "Xander", "Mookie", "Eddie", "Kara", "Ella", "Ruth",
        "Josh", "Emma", "Matthew", "David", "Jean", "Tom", "Raymond"};

    String [] ln = {"Gilbert", "Ficus", "Smith", "Wesson", "Johnstone", "Ortiz", "Jones", "LaMarche",
        "Gauvin", "Betts", "Brady", "Ciruso", "Elliot", "Bird", "Garciaparra", "Thomas", "Donnelly", "Donovan"};

    
    for (int s = 0; s < 60; s++) {
      String studentSourcedId = "demo-student-".concat(String.valueOf(s));
      
      User student 
      = new User.Builder()
        .withSourcedId(studentSourcedId)
        .withRole(Role.student)
        .withFamilyName(ln[ThreadLocalRandom.current().nextInt(0, ln.length)])
        .withGivenName(fn[ThreadLocalRandom.current().nextInt(0, fn.length)])
        .withUserId(studentSourcedId)
        .withStatus(Status.active)
        .build();
      
      studentMap.put(student.getSourcedId(), student);
      
      Enrollment studentEnrollment1
      = new Enrollment.Builder()
          .withKlass(class1)
          .withUser(student)
          .withPrimary(false)
          .withSourcedId("student-enrollment-c1-"+s)
          .withRole(Role.student)
          .withStatus(Status.active)
          .build();
      
      Enrollment studentEnrollment2
      = new Enrollment.Builder()
          .withKlass(class2)
          .withUser(student)
          .withPrimary(false)
          .withSourcedId("student-enrollment-c2-"+s)
          .withRole(Role.student)
          .withStatus(Status.active)
          .build();
      
      Enrollment studentEnrollment3
      = new Enrollment.Builder()
          .withKlass(class3)
          .withUser(student)
          .withPrimary(false)
          .withSourcedId("student-enrollment-c3-"+s)
          .withRole(Role.student)
          .withStatus(Status.active)
          .build();
      
      studentEnrollmentMap.put(studentEnrollment1.getSourcedId(), studentEnrollment1);
      studentEnrollmentMap.put(studentEnrollment2.getSourcedId(), studentEnrollment2);
      studentEnrollmentMap.put(studentEnrollment3.getSourcedId(), studentEnrollment3);
    }

    // Line Items
    
    LineItemCategory quiz 
    = new LineItemCategory.Builder()
      .withSourcedId(UUID.randomUUID().toString())
      .withStatus(Status.active)
      .withTitle("quiz")
      .build();

    LineItemCategory exam 
    = new LineItemCategory.Builder()
      .withSourcedId(UUID.randomUUID().toString())
      .withStatus(Status.active)
      .withTitle("exam")
      .build();

    LineItemCategory lab 
    = new LineItemCategory.Builder()
      .withSourcedId(UUID.randomUUID().toString())
      .withStatus(Status.active)
      .withTitle("lab")
      .build();

    LineItemCategory report 
    = new LineItemCategory.Builder()
      .withSourcedId(UUID.randomUUID().toString())
      .withStatus(Status.active)
      .withTitle("report")
      .build();
    
    LineItem class1_li0 
      = new LineItem.Builder()
        .withSourcedId("class1_li0")
        .withStatus(Status.active)
        .withTitle("Hands-on Lab")
        .withDescription("Learn how to use the microscope.")
        .withAssignDate(LocalDateTime.of(2016, 9, 7, 0, 0))
        .withDueDate(LocalDateTime.of(2016, 9, 14, 0, 0))
        .withClass(class1)
        .withCategory(lab)
        .build();
    
    LineItem class1_li1 
    = new LineItem.Builder()
      .withSourcedId("class1_li1")
      .withStatus(Status.active)
      .withTitle("Lab Report")
      .withDescription("Report on observations")
      .withAssignDate(LocalDateTime.of(2016, 9, 7,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 1,0,0))
      .withClass(class1)
      .withCategory(report)
      .build();
   
    LineItem class1_li2 
    = new LineItem.Builder()
      .withSourcedId("class1_li2")
      .withStatus(Status.active)
      .withTitle("Midterm Exam")
      .withDescription("Good luck!")
      .withAssignDate(LocalDateTime.of(2016, 9, 1,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 27,0,0))
      .withClass(class1)
      .withCategory(exam)
      .build();
    
    LineItem class1_li3 
    = new LineItem.Builder()
      .withSourcedId("class1_li3")
      .withStatus(Status.active)
      .withTitle("Quiz #1")
      .withAssignDate(LocalDateTime.of(2016, 11, 15,0,0))
      .withDueDate(LocalDateTime.of(2016, 11, 15,0,0))
      .withClass(class1)
      .withCategory(quiz)
      .build();

    LineItem class1_li4 
    = new LineItem.Builder()
      .withSourcedId("class1_li4")
      .withStatus(Status.active)
      .withTitle("Final Exam")
      .withDescription("Enjoy break!")
      .withAssignDate(LocalDateTime.of(2016, 9, 1,0,0))
      .withDueDate(LocalDateTime.of(2016, 12, 10,0,0))
      .withClass(class1)
      .withCategory(exam)
      .build();
       
    LineItem class2_li0 
    = new LineItem.Builder()
      .withSourcedId("class2_li0")
      .withStatus(Status.active)
      .withTitle("Hands-on Lab")
      .withDescription("Compounds and more!")
      .withAssignDate(LocalDateTime.of(2016, 9, 2,0,0))
      .withDueDate(LocalDateTime.of(2016, 9, 19,0,0))
      .withClass(class2)
      .withCategory(lab)
      .build();
  
    LineItem class2_li1 
    = new LineItem.Builder()
      .withSourcedId("class2_li1")
      .withStatus(Status.active)
      .withTitle("Lab Report")
      .withDescription("Report on compounds")
      .withAssignDate(LocalDateTime.of(2016, 9, 2,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 15,0,0))
      .withClass(class2)
      .withCategory(report)
      .build();
    
    LineItem class2_li11 
    = new LineItem.Builder()
      .withSourcedId("class2_li11")
      .withStatus(Status.active)
      .withTitle("Quiz #1")
      .withDescription("Knowledge check")
      .withAssignDate(LocalDateTime.of(2016, 9, 2,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 19,0,0))
      .withClass(class2)
      .withCategory(report)
      .build();
   
    LineItem class2_li2 
    = new LineItem.Builder()
      .withSourcedId("class2_li2")
      .withStatus(Status.active)
      .withTitle("Midterm Exam")
      .withDescription("Good luck!")
      .withAssignDate(LocalDateTime.of(2016, 9, 2,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 30,0,0))
      .withClass(class2)
      .withCategory(exam)
      .build();
    
    LineItem class2_li3 
    = new LineItem.Builder()
      .withSourcedId("class2_li3")
      .withStatus(Status.active)
      .withTitle("Quiz #2")
      .withAssignDate(LocalDateTime.of(2016, 11, 15,0,0))
      .withDueDate(LocalDateTime.of(2016, 11, 19,0,0))
      .withClass(class2)
      .withCategory(quiz)
      .build();
  
    LineItem class2_li4 
    = new LineItem.Builder()
      .withSourcedId("class2_li4")
      .withStatus(Status.active)
      .withTitle("Final Exam")
      .withDescription("Enjoy break!")
      .withAssignDate(LocalDateTime.of(2016, 9, 2,0,0))
      .withDueDate(LocalDateTime.of(2016, 12, 6,0,0))
      .withClass(class2)
      .withCategory(exam)
      .build();
    
    LineItem class3_li0 
    = new LineItem.Builder()
      .withSourcedId("class3_li0")
      .withStatus(Status.active)
      .withTitle("Hands-on Lab")
      .withDescription("Disection! The innards of ficus.")
      .withAssignDate(LocalDateTime.of(2016, 9, 10,0,0))
      .withDueDate(LocalDateTime.of(2016, 9, 15,0,0))
      .withClass(class3)
      .withCategory(lab)
      .build();
    
    LineItem class3_li1 
    = new LineItem.Builder()
      .withSourcedId("class3_li1")
      .withStatus(Status.active)
      .withTitle("Lab Report")
      .withDescription("Report on discection")
      .withAssignDate(LocalDateTime.of(2016, 9, 10,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 1,0,0))
      .withClass(class3)
      .withCategory(report)
      .build();
    
    LineItem class3_li11 
    = new LineItem.Builder()
      .withSourcedId("class3_li11")
      .withStatus(Status.active)
      .withTitle("Quiz #1")
      .withDescription("Knowledge check")
      .withAssignDate(LocalDateTime.of(2016, 9, 19,0,0))
      .withDueDate(LocalDateTime.of(2016, 10, 17,0,0))
      .withClass(class3)
      .withCategory(report)
      .build();

    LineItem class3_li2 
    = new LineItem.Builder()
      .withSourcedId("class3_li2")
      .withStatus(Status.active)
      .withTitle("Midterm Exam")
      .withDescription("Good luck!")
      .withAssignDate(LocalDateTime.of(2016, 9, 17,0,0))
      .withDueDate(LocalDateTime.of(2016, 11, 1,0,0))
      .withClass(class3)
      .withCategory(exam)
      .build();

    LineItem class3_li3 
    = new LineItem.Builder()
      .withSourcedId("class3_li3")
      .withStatus(Status.active)
      .withTitle("Quiz #2")
      .withAssignDate(LocalDateTime.of(2016, 11, 15,0,0))
      .withDueDate(LocalDateTime.of(2016, 11, 15,0,0))
      .withClass(class3)
      .withCategory(quiz)
      .build();
    
    LineItem class3_li4 
    = new LineItem.Builder()
      .withSourcedId("class3_li4")
      .withStatus(Status.active)
      .withTitle("Final Exam")
      .withDescription("Enjoy break!")
      .withAssignDate(LocalDateTime.of(2016, 9, 7, 0, 0))
      .withDueDate(LocalDateTime.of(2016, 12, 9, 0, 0))
      .withClass(class3)
      .withCategory(exam)
      .build();
    
    lineItemMap.put(class1_li0.getSourcedId(), class1_li0);
    lineItemMap.put(class1_li1.getSourcedId(), class1_li1);
    lineItemMap.put(class1_li2.getSourcedId(), class1_li2);
    lineItemMap.put(class1_li3.getSourcedId(), class1_li3);
    lineItemMap.put(class1_li4.getSourcedId(), class1_li4);
    
    lineItemMap.put(class2_li0.getSourcedId(), class2_li0);
    lineItemMap.put(class2_li1.getSourcedId(), class2_li1);
    lineItemMap.put(class2_li11.getSourcedId(), class2_li11);
    lineItemMap.put(class2_li2.getSourcedId(), class2_li2);
    lineItemMap.put(class2_li3.getSourcedId(), class2_li3);
    lineItemMap.put(class2_li4.getSourcedId(), class2_li4);

    lineItemMap.put(class3_li0.getSourcedId(), class3_li0);
    lineItemMap.put(class3_li1.getSourcedId(), class3_li1);
    lineItemMap.put(class3_li11.getSourcedId(), class3_li11);
    lineItemMap.put(class3_li2.getSourcedId(), class3_li2);
    lineItemMap.put(class3_li3.getSourcedId(), class3_li3);
    lineItemMap.put(class3_li4.getSourcedId(), class3_li4);
    
    // Events
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    
    String [] classSourcedIds = {"demo-class-1","demo-class-2","demo-class-3"};
    
    long demoClass1StartTime = Timestamp.valueOf("2016-08-30 00:00:00").getTime();
    long demoClass1EndTime = Timestamp.valueOf("2016-12-11 00:00:00").getTime();
    long demoClass2StartTime = Timestamp.valueOf("2016-09-01 00:00:00").getTime();
    long demoClass2EndTime = Timestamp.valueOf("2016-12-10 00:00:00").getTime();
    long demoClass3StartTime = Timestamp.valueOf("2016-09-08 00:00:00").getTime();
    long demoClass3EndTime = Timestamp.valueOf("2016-12-13 00:00:00").getTime();
    
    List<Verb> verbs = new ArrayList<>();
    verbs.add(new Verb(3.0,"http://purl.imsglobal.org/vocab/caliper/v1/action#LoggedIn"));
    verbs.add(new Verb(1.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#LoggedOut"));
    verbs.add(new Verb(12.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#NavigatedTo"));
    verbs.add(new Verb(12.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#Viewed"));
    verbs.add(new Verb(3.0,"http://purl.imsglobal.org/vocab/caliper/v1/action#Completed"));
    verbs.add(new Verb(5.0,"http://purl.imsglobal.org/vocab/caliper/v1/action#Submitted"));
    verbs.add(new Verb(11.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#Searched"));
    verbs.add(new Verb(4.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#Recommended"));
    verbs.add(new Verb(6.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#Commented"));
    verbs.add(new Verb(2.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#Bookmarked"));
    verbs.add(new Verb(7.5,"http://purl.imsglobal.org/vocab/caliper/v1/action#Shared"));
    
    Verb [] verbArray = verbs.stream().toArray(Verb[]::new);
    
    double totalWeights = 70.0;
    
    List<String> studentSourcedIds = new LinkedList<>();
    for (int s = 0; s < 60; s++) {
      String studentSourcedId = "demo-student-".concat(String.valueOf(s));
      studentSourcedIds.add(studentSourcedId);
    }
    
    for(int e = 0; e < 60000; e++) {
      
      String context = "http://purl.imsglobal.org/ctx/caliper/v1/Context";
      
      Agent agent
        = new Agent.Builder()
            .withContext(context)
            .withId(studentSourcedIds.get(ThreadLocalRandom.current().nextInt(0, 60)))
            .withType("http://purl.imsglobal.org/caliper/v1/lis/Person")
            .build();
      
      Entity object
        = new Entity.Builder()
            .withContext(context)
            .withId("http://edapp.com/object")
            .withType("http://purl.imsglobal.org/caliper/v1/SoftwareApplication")
            .build();
      
      Group group
        = new Group.Builder()
            .withContext(context)
            .withId(classSourcedIds[ThreadLocalRandom.current().nextInt(0, 3)])
            .withType("http://purl.imsglobal.org/caliper/v1/lis/CourseSection")
            .build();
      
                  
      Instant timestamp = null;
      
      if ("demo-class-1".equals(group.getId())) {       
        
        long rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
        Calendar c = Calendar.getInstance();
        Date randomDate = new Date(rnd);
        c.setTime(randomDate);
        
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && e % 2 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && e % 3 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && e % 4 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }       
        
        timestamp = randomDate.toInstant();
      }
      else if ("demo-class-2".equals(group.getId())) {
        
        long rnd = getRandomTimeBetweenTwoDates(demoClass2StartTime, demoClass2EndTime);
        Calendar c = Calendar.getInstance();
        Date randomDate = new Date(rnd);
        c.setTime(randomDate);
        
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && e % 2 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && e % 3 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && e % 4 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }       
        
        timestamp = randomDate.toInstant();
      }
      else {
        
        long rnd = getRandomTimeBetweenTwoDates(demoClass3StartTime, demoClass3EndTime);
        Calendar c = Calendar.getInstance();
        Date randomDate = new Date(rnd);
        c.setTime(randomDate);
        
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && e % 2 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && e % 3 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && e % 4 == 0) {
          rnd = getRandomTimeBetweenTwoDates(demoClass1StartTime, demoClass1EndTime);
          randomDate = new Date(rnd);
        }       
        
        timestamp = randomDate.toInstant();
      }
      
      int randomIndex = -1;
      double random = Math.random() * totalWeights;
      for (int i = 0; i < verbArray.length; ++i)
      {
          random -= verbArray[i].getWeight();
          if (random <= 0.0d)
          {
              randomIndex = i;
              break;
          }
      }
      
      Event event
      = new Event.Builder()
          .withAgent(agent)
          .withContext(context)
          .withObject(object)
          .withGroup(group)
          .withEventTime(timestamp.atZone(ZoneId.systemDefault()).toLocalDateTime())
          .withAction(verbArray[randomIndex].getVerb())
          .build();

      events.add(event);
    }


  }

  @Override
  public void run() {
    Collection<Enrollment> teacherEnrollments = teacherEnrollmentMap.values();
    for (Enrollment enrollment : teacherEnrollments) {
      matthewsClient.postEnrollment(enrollment);
    }
    
    Collection<User> students = studentMap.values();
    for (User user : students) {
      matthewsClient.postUser(user);
    }
    
    Collection<Enrollment> studentEnrollments = studentEnrollmentMap.values();
    for (Enrollment enrollment : studentEnrollments) {
      matthewsClient.postEnrollment(enrollment);
    }
    
    Collection<LineItem> lineItems = lineItemMap.values();
    for (LineItem lineItem : lineItems) {
      matthewsClient.postLineItem(lineItem);
    }
    
    for (Event event : events) {
      matthewsClient.postEvent(event, "DemoDataLoader");
    }
  }
  
  private long getRandomTimeBetweenTwoDates (long beginTime, long endTime) {
    long diff = endTime - beginTime + 1;
    return beginTime + (long) (Math.random() * diff);
  }
  
  class Verb {
    private String verb;
    private double weight;
    public Verb(double weight, String verb) {
      this.weight = weight;
      this.verb = verb;
    }
    public String getVerb() {
      return verb;
    }
    public double getWeight() {
      return weight;
    }
  }


}
