package net.javaguides.sms.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.*;

@Service
public class StudentServiceImpl implements StudentService{

	private static final String db = "Database";
	private static final String host = "172.31.6.3";
	private static final String port = "27017";

	private static final String COLLECTION_PERSONE = "Student";
	private static final String ELEMENT_COGNOME = "cognome";
	private static final String ELEMENT_NOME = "nome";
	private static final String ELEMENT_ORE = "ore";

	private final MongoClientURI connectioString;

	public StudentServiceImpl() {
		this.connectioString = new MongoClientURI("mongodb://"+host+":"+port);
	}

	@Override
	public List<Student> getAllStudents() {
		MongoClient mongoClient = new MongoClient(connectioString);
		MongoDatabase database =
				mongoClient.getDatabase(db);
		MongoCollection<Document> collection = database.getCollection(COLLECTION_PERSONE);

		List<Student> students = new ArrayList<Student>();

		for(Document current : collection.find(exists(ELEMENT_COGNOME,true))){
			Student st = new Student(current.getString(ELEMENT_COGNOME), current.getString(ELEMENT_NOME),
					current.getString(ELEMENT_ORE));
			students.add(st);
		}
		mongoClient.close();

		return students;
	}

	@Override
	public void saveStudent(Student student) {
		MongoClient mongoClient = new MongoClient(connectioString);
		MongoDatabase database = mongoClient.getDatabase(db);
		MongoCollection<Document> collection = database.getCollection(COLLECTION_PERSONE);
		Document unoStudent;

		unoStudent = new Document(ELEMENT_COGNOME, student.getLastName())
				.append(ELEMENT_NOME, student.getFirstName())
				.append(ELEMENT_ORE, student.getOre());
		collection.insertOne(unoStudent);
		mongoClient.close();
	}

	@Override
	public Student getStudentById(String id) {
		List<Student> students = new ArrayList<Student>();
		MongoClient mongoClient = new MongoClient(connectioString);
		MongoDatabase mongoDatabase = mongoClient.getDatabase(db);
		MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION_PERSONE);

		for(Document current : collection.find(eq(ELEMENT_NOME, id))){
			Student st = new Student(current.getString(ELEMENT_COGNOME), current.getString(ELEMENT_NOME), current.getString(ELEMENT_ORE));
			students.add(st);
		}

		mongoClient.close();
		assert students.size()==1;
		return students.get(0);
	}

	@Override
	public Student updateStudent(Student student) {
		MongoClient mongoClient = new MongoClient(connectioString);
		MongoDatabase database = mongoClient.getDatabase(db);
		MongoCollection<Document> collection = database.getCollection(COLLECTION_PERSONE);
		List<Student> students = new ArrayList<Student>();
		String nuovoNome = student.getFirstName();
		String nuovoCognome = student.getLastName();
		String nuovoOre = student.getOre();

		for(Document current :collection.find(eq(ELEMENT_COGNOME, student.getLastName()))){
			current.put(ELEMENT_COGNOME, nuovoCognome);
			current.put(ELEMENT_NOME, nuovoNome);
			current.put(ELEMENT_ORE, nuovoOre);
			collection.replaceOne(eq(ELEMENT_COGNOME, student.getLastName()), current);
		}
		mongoClient.close();
		return student;
	}

	@Override
	public void deleteStudentById(Long id) {

	}

//
//	private StudentRepository studentRepository;
//
//	public StudentServiceImpl(StudentRepository studentRepository) {
//		super();
//		this.studentRepository = studentRepository;
//	}
//
//	@Override
//	public List<Student> getAllStudents() {
//		return studentRepository.findAll();
//	}
//
//	@Override
//	public Student saveStudent(Student student) {
//		return studentRepository.save(student);
//	}
//
//	@Override
//	public Student getStudentById(Long id) {
//		return studentRepository.findById(id).get();
//	}
//
//	@Override
//	public Student updateStudent(Student student) {
//		return studentRepository.save(student);
//	}
//
//	@Override
//	public void deleteStudentById(Long id) {
//		studentRepository.deleteById(id);
//	}
}
