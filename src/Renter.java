public class Renter {
    private int renterID;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String mobilephone;
    private String phone;
    private String email;
    private String driverlicensenumber;
    private String driversincedate;

    public Renter(){}


    //without phone, with mobilephone
    public Renter(int renterID,String name, String address, String zip, String city, String mobilephone, String email, String driverlicensenumber, String driversincedate){
        this.renterID = renterID;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.mobilephone = mobilephone;
        this.email = email;
        this.driverlicensenumber = driverlicensenumber;
        this.driversincedate = driversincedate;
    }

    //with Phone and Mobilephone
    public Renter(int renterID,String name, String address, String zip, String city, String mobilephone, String phone, String email, String driverlicensenumber, String driversincedate){
        this.renterID = renterID;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.mobilephone = mobilephone;
        this.phone = phone;
        this.email = email;
        this.driverlicensenumber = driverlicensenumber;
        this.driversincedate = driversincedate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverlicensenumber() {
        return driverlicensenumber;
    }

    public void setDriverlicensenumber(String driverlicensenumber) {
        this.driverlicensenumber = driverlicensenumber;
    }

    public int getRenterID() {
        return renterID;
    }

    public void setRenterID(int renterID) {
        this.renterID = renterID;
    }

    public String getDriversincedate() {
        return driversincedate;
    }

    public void setDriversincedate(String driversincedate) {
        this.driversincedate = driversincedate;
    }

    @Override
    public String toString() {
        return "Renter:\n" + "ID: "+renterID+"\n"+
                "Name: " + name + "\n" +
                "Address: " + address + "\n" +
                "Zip: " + zip + "\n" +
                "City: " + city + "\n" +
                "Mobile Phone: " + mobilephone + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n" +
                "Driver's License Number: " + driverlicensenumber + "\n" +
                "Driver Since Date: " + driversincedate + "\n";
    }

}
