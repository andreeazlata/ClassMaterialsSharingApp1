//package org.example.domain;
//
//import org.example.repository.IRepository;
//
//public class StudentValidator {
//
//    public void validate(Student student, IRepository<Student> studentIRepository) throws Exception {
//        if (student.getName()==null) {
//            throw new Exception("The student must have a name");
//        }
//        Student givenStudent = studentIRepository.readOne((transaction.ge));
//        if (givenStudent == null) {
//            throw new Exception("There is no student with the given id!");
//        }
//    }
//}
