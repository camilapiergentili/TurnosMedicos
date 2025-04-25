package ar.com.dontar.demo.model;

public class Clinic {

    private String clinicName;
    private String cliniAddress;
    private String clinicPhone;
    private Professional professional;
    private Speciality specialty;

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getCliniAddress() {
        return cliniAddress;
    }

    public void setCliniAddress(String cliniAddress) {
        this.cliniAddress = cliniAddress;
    }

    public String getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(String clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Speciality getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Speciality specialty) {
        this.specialty = specialty;
    }
}
