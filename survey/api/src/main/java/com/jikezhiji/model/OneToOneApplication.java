package com.jikezhiji.model;
import javax.persistence.*;
/**
 * Created by liusizuo on 2017/6/7.
 */
public class OneToOneApplication {
    @Entity(name = "Phone")
    public static class Phone {

        @Id
        @GeneratedValue
        private Long id;

        @Column(name = "`number`")
        private String number;

        @OneToOne(mappedBy = "phone", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private PhoneDetails details;

        public Phone() {
        }

        public Phone(String number) {
            this.number = number;
        }

        public Long getId() {
            return id;
        }

        public String getNumber() {
            return number;
        }

        public PhoneDetails getDetails() {
            return details;
        }

        public void addDetails(PhoneDetails details) {
            details.setPhone( this );
            this.details = details;
        }

        public void removeDetails() {
            if ( details != null ) {
                details.setPhone( null );
                this.details = null;
            }
        }
    }

    @Entity(name = "PhoneDetails")
    public static class PhoneDetails {

        @Id
        @Column(name="phone_id")
        private Long id;

        private String provider;

        private String technology;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "phone_id")
        private Phone phone;

        public PhoneDetails() {
        }

        public PhoneDetails(String provider, String technology) {
            this.provider = provider;
            this.technology = technology;
        }

        public String getProvider() {
            return provider;
        }

        public String getTechnology() {
            return technology;
        }

        public void setTechnology(String technology) {
            this.technology = technology;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
            if(phone != null) {
                this.id = phone.getId();
            }
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Phone phone = new Phone( "123-456-7890" );

        entityManager.persist( phone );
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        PhoneDetails details = new PhoneDetails( "T-Mobile", "GSM" );
        phone.addDetails( details );
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        phone.removeDetails();
        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }
}
