//package com.jikezhiji.model;
//
//import org.hibernate.annotations.NaturalId;
//
//import javax.persistence.*;
//import java.util.*;
//
///**
// * Created by liusizuo on 2017/6/7.
// */
//public class OneToManyMain {
//
//    @Entity(name = "Person")
//    public static class Person {
//
//        @Id
//        @GeneratedValue
//        private Long id;
//        @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
//        private List<Phone> phones = new ArrayList<>();
//
//        public Person() {
//        }
//
//        public Person(Long id) {
//            this.id = id;
//        }
//
//        public List<Phone> getPhones() {
//            return phones;
//        }
//
//        public void addPhone(Phone phone) {
//            phones.add( phone );
//            phone.setPerson( this );
//        }
//
//        public void removePhone(Phone phone) {
//            phones.remove( phone );
//            phone.setPerson( null );
//        }
//    }
//    @Entity(name = "Phone")
//    public static class Phone {
//
//        @Id
//        @GeneratedValue
//        private Long id;
//
//        @NaturalId
//        @Column(name = "`number`", unique = true)
//        private String number;
//
//        @ManyToOne
//        private Person person;
//
//        public Phone() {
//        }
//
//        public Phone(String number) {
//            this.number = number;
//        }
//
//        public Long getId() {
//            return id;
//        }
//
//        public String getNumber() {
//            return number;
//        }
//
//        public Person getPerson() {
//            return person;
//        }
//
//        public void setPerson(Person person) {
//            this.person = person;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if ( this == o ) {
//                return true;
//            }
//            if ( o == null || getClass() != o.getClass() ) {
//                return false;
//            }
//            Phone phone = (Phone) o;
//            return Objects.equals( number, phone.number );
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash( number );
//        }
//    }
//
//    public static void main(String[] args) {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = factory.createEntityManager();
//        entityManager.getTransaction().begin();
//        Person person = new Person();
//        Phone phone1 = new Phone( "123-456-7890" );
//        Phone phone2 = new Phone( "321-654-0987" );
//
//        person.addPhone( phone1 );
//        person.addPhone( phone2 );
//        entityManager.persist( person );
//        entityManager.flush();
//        person.getPhones().remove(0);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        factory.close();
//    }
//}
