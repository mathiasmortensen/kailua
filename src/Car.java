public abstract class Car {
    protected int id;
    protected String brand;
    protected String model;
    protected Fueltype fueltype;
    protected String regplate;
    protected String regYearMonth;
    protected int odometer;
    protected int cc;
    protected boolean isManual;
    protected boolean hasAirCon;
    protected boolean hasCruiseControl;
    protected boolean hasLeatherSeats;
    protected int seats;
    protected int hp;
    protected boolean isAvailable;

    public Car(){}


    public Car(int id, String brand, String model, Fueltype fueltype, String regplate, String regYearMonth, int odometer, int cc, boolean isManual, boolean hasAirCon, boolean hasCruiseControl, boolean hasLeatherSeats, int seats, int hp, boolean isAvailable) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.fueltype = fueltype;
        this.regplate = regplate;
        this.regYearMonth = regYearMonth;
        this.odometer = odometer;
        this.cc = cc;
        this.isManual = isManual;
        this.hasAirCon = hasAirCon;
        this.hasCruiseControl = hasCruiseControl;
        this.hasLeatherSeats = hasLeatherSeats;
        this.seats = seats;
        this.hp = hp;
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Fueltype getFueltype() {
        return fueltype;
    }

    public void setFueltype(Fueltype fueltype) {
        this.fueltype = fueltype;
    }

    public String getRegplate() {
        return regplate;
    }

    public void setRegplate(String regplate) {
        this.regplate = regplate;
    }

    public int getOdometer() {
        return odometer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }

    public boolean isHasAirCon() {
        return hasAirCon;
    }

    public void setHasAirCon(boolean hasAirCon) {
        this.hasAirCon = hasAirCon;
    }

    public boolean isHasCruiseControl() {
        return hasCruiseControl;
    }

    public void setHasCruiseControl(boolean hasCruiseControl) {
        this.hasCruiseControl = hasCruiseControl;
    }

    public boolean isHasLeatherSeats() {
        return hasLeatherSeats;
    }

    public void setHasLeatherSeats(boolean hasLeatherSeats) {
        this.hasLeatherSeats = hasLeatherSeats;
    }

    public int whatCar() {
        if (cc > 3000 && !isManual && hasAirCon && hasCruiseControl && hasLeatherSeats) {
            //Luxury car
            return 1;
        } else if (isManual && hasAirCon && seats >= 7) {
            // Family Car
            return 2;
        } else if (isManual && hp >= 200) {
            //sports car
            return 3;
        }

        return 0;
    }

    public String getRegYearMonth() {
        return regYearMonth;
    }

    public void setRegYearMonth(String regYearMonth) {
        this.regYearMonth = regYearMonth;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public int getSeats() {
        return seats;
    }

    public String toString(){
        return "CarID: " + id + "\n" +
                "Brand: " + brand + "\n" +
                "Model: " + model + "\n" +
                "Fueltype: " + fueltype + "\n" +
                "Registration Plate: " + regplate + "\n" +
                "Registration Year/Month: " + regYearMonth + "\n" +
                "Odometer: " + odometer + " km\n" +
                "Engine CC: " + cc + "\n" +
                "Is Manual: " + (isManual ? "Yes" : "No") + "\n" +
                "Has Air Conditioning: " + (hasAirCon ? "Yes" : "No") + "\n" +
                "Has Cruise Control: " + (hasCruiseControl ? "Yes" : "No") + "\n" +
                "Has Leather Seats: " + (hasLeatherSeats ? "Yes" : "No") + "\n" +
                "Seats: " + seats + "\n";
    }

}