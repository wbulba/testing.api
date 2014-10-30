package vb.api.model;

/**
 * Created by vbulba on 30.10.2014.
 */
public class User {

    String id;
    String last_name;
    String first_name;
    String middle_name;
    boolean is_admin;
    boolean is_applicant;
    boolean is_employer;
    String email;
    Employer employer;
    Counters counters;
    boolean is_in_search;
    String resumes_url;
    String negotiations_url;
    String personal_manager;
    boolean is_anonymous;
    String mid_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public boolean isIs_applicant() {
        return is_applicant;
    }

    public void setIs_applicant(boolean is_applicant) {
        this.is_applicant = is_applicant;
    }

    public boolean isIs_employer() {
        return is_employer;
    }

    public void setIs_employer(boolean is_employer) {
        this.is_employer = is_employer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Counters getCounters() {
        return counters;
    }

    public void setCounters(Counters counters) {
        this.counters = counters;
    }

    public boolean isIs_in_search() {
        return is_in_search;
    }

    public void setIs_in_search(boolean is_in_search) {
        this.is_in_search = is_in_search;
    }

    public String getResumes_url() {
        return resumes_url;
    }

    public void setResumes_url(String resumes_url) {
        this.resumes_url = resumes_url;
    }

    public String getNegotiations_url() {
        return negotiations_url;
    }

    public void setNegotiations_url(String negotiations_url) {
        this.negotiations_url = negotiations_url;
    }

    public String getPersonal_manager() {
        return personal_manager;
    }

    public void setPersonal_manager(String personal_manager) {
        this.personal_manager = personal_manager;
    }

    public boolean isIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public String getMid_name() {
        return mid_name;
    }

    public void setMid_name(String mid_name) {
        this.mid_name = mid_name;
    }

}
