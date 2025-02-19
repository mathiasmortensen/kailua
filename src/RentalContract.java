import java.util.ArrayList;
import java.util.Date;

public class RentalContract {
    private String name;
    private String address;
    private int zip;
    private String city;
    private String DriverLicenseNumber;
    private Date rentedFrom;
    private Date rentedTo;
    private int maxkm;
    private int kmrentedStart;
    private String registrationNumber;
    private Renter renter;
    private Car car;
    private int id;
    private boolean isCompleted;

    ArrayList<RentalContract> rentalContracts = new ArrayList<RentalContract>();

    public RentalContract() {
    }

    public RentalContract(int id, Renter renter, Car car, Date rentedFrom, Date rentedTo, int maxkm, int kmrentedStart, String registrationNumber, boolean isCompleted) {
        if (id != 0) {
            this.id = id;
        }

        this.renter = renter;
        this.car = car;
        this.rentedFrom = rentedFrom;
        this.rentedTo = rentedTo;
        this.maxkm = maxkm;
        this.kmrentedStart = kmrentedStart;
        this.registrationNumber = registrationNumber;
        this.isCompleted = isCompleted;
    }

    public RentalContract(int id, int renterID, int carID, Date rentedFrom, Date rentedTo, int maxkm, int kmrentedStart, String registrationNumber, boolean isCompleted) {
        if (id != 0) {
            this.id = id;
        }

        this.renter = loadRenterDAO(renterID);
        this.car = loadCarDAO(carID);
        this.rentedFrom = rentedFrom;
        this.rentedTo = rentedTo;
        this.maxkm = maxkm;
        this.kmrentedStart = kmrentedStart;
        this.registrationNumber = registrationNumber;
        this.isCompleted = isCompleted;
    }

    public Car loadCarDAO(int id) {
        CarDAO carDao = new CarDAO();
        carDao.GenerateCarObjectFromSQL();
        for (Car c : carDao.allCars) {
            if (c.id != id) {
                continue;
            }
            return c;
        }
        return null;
    }

    public Renter loadRenterDAO(int id) {
        RentalDAO rentalDao = new RentalDAO();
        rentalDao.generateRenterObjectFromSQL();

        for (Renter r : rentalDao.Renters) {
            if (r.getRenterID() != id) {
                continue;
            }

            return r;
        }
        return null;
    }

    public Renter getRenter() {
        return renter;
    }

    public Car getCar() {
        return car;
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

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDriverLicenseNumber() {
        return DriverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        DriverLicenseNumber = driverLicenseNumber;
    }

    public Date getRentedFrom() {
        return rentedFrom;
    }

    public void setRentedFrom(Date rentedFrom) {
        this.rentedFrom = rentedFrom;
    }

    public Date getRentedTo() {
        return rentedTo;
    }

    public void setRentedTo(Date rentedTo) {
        this.rentedTo = rentedTo;
    }

    public int getMaxkm() {
        return maxkm;
    }

    public void setMaxkm(int maxkm) {
        this.maxkm = maxkm;
    }

    public int getKmrentedStart() {
        return kmrentedStart;
    }

    public void setKmrentedStart(int kmrentedStart) {
        this.kmrentedStart = kmrentedStart;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "Rental Contract Details:\n" +
               "----------------------------\n" +
               "Zip Code: " + renter.getZip() + "\n" +
               "Name: " + renter.getName() + "\n" +
               "Address: " + renter.getAddress() + "\n" +
               "City: " + renter.getCity() + "\n" +
               "Driver License Number: " + renter.getDriverlicensenumber() + "\n" +
               "Rented From: " + rentedFrom + "\n" +
               "Rented To: " + rentedTo + "\n" +
               "Max KM Allowed: " + maxkm + " km\n" +
               "Odometer Start: " + kmrentedStart + " km\n" +
               "Car Details: " + car.toString() + "\n" +
               "Contract Completed: " + (isCompleted ? "Yes" : "No") + "\n";
    }

}