import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Tui {
    CarDAO cd = new CarDAO();
    RentalDAO rd = new RentalDAO();
    RentalContract rc = new RentalContract();

    public Tui() {

    }

    public void Start() {
        Scanner sc = new Scanner(System.in);
        rd.defaultDataRenter();
        cd.GenerateCarObjectFromSQL();
        rd.generateRenterObjectFromSQL();
        rd.generateRentalContractObjectFromSQL();
        while (true) {
            System.out.println(" _   __  ___  _____ _     _   _  ___        _____   ___  ______      ______ _____ _   _ _____ ___   _     \n" +
                               "| | / / / _ \\|_   _| |   | | | |/ _ \\      /  __ \\ / _ \\ | ___ \\     | ___ \\  ___| \\ | |_   _/ _ \\ | |    \n" +
                               "| |/ / / /_\\ \\ | | | |   | | | / /_\\ \\     | /  \\// /_\\ \\| |_/ /     | |_/ / |__ |  \\| | | |/ /_\\ \\| |    \n" +
                               "|    \\ |  _  | | | | |   | | | |  _  |     | |    |  _  ||    /      |    /|  __|| . ` | | ||  _  || |    \n" +
                               "| |\\  \\| | | |_| |_| |___| |_| | | | |     | \\__/\\| | | || |\\ \\      | |\\ \\| |___| |\\  | | || | | || |____\n" +
                               "\\_| \\_/\\_| |_/\\___/\\_____/\\___/\\_| |_/      \\____/\\_| |_/\\_| \\_|     \\_| \\_\\____/\\_| \\_/ \\_/\\_| |_/\\_____/\n" +
                               "                                                                                                          \n" +
                               "                                                                                                          \n");
            System.out.println("1. Add car to inventory ");
            System.out.println("2. Delete car from inventory");
            System.out.println("3. Display car inventory");
            System.out.println("33. Display available car inventory");
            System.out.println("4. Add renter to system");
            System.out.println("5. Delete renter from system");
            System.out.println("6. Display renter(s) from system");
            System.out.println("7. Create Rental Contract");
            System.out.println("8. Mark rental contract as completed");
            System.out.println("9. Display Rental Contracts");


            int valg = Integer.parseInt(sc.nextLine());

            switch (valg) {
                case 1:
                    addCarToInventory();
                    break;
                case 2:
                    DeleteCarFromInventory();
                    break;
                case 3:
                    cd.showAllCars(false);
                    break;
                case 4:
                    addRentertoSystem();
                    break;
                case 5:
                    deleteRenterFromSystem();
                    break;
                case 6:
                    rd.displayCustomers();
                    break;
                case 7:
                    createRentalContract();
                    break;
                case 8:
                    markRentalAsCompleted();
                    break;
                case 9:
                    for (RentalContract rc : rd.rentalContractList) {
                        System.out.println(rc);
                    }
                    break;
                case 33:
                    cd.showAllCars(true);
                    break;
            }

        }

    }

    public void markRentalAsCompleted() {
        Scanner sc = new Scanner(System.in);
        // Iterer igennem listen af lejekontrakter
        for (RentalContract rc : rd.rentalContractList) {
            // Tjekker om lejekontrakten allerede er afsluttet
            if (rc.isCompleted()) {
                continue; // hvis den er afsluttet, går den videre til næste element i listen af kontrakter
            }
            // Udskriver nødvendige informationer omkring biler, som ikke er markeret færdig
            System.out.println("Car: " + rc.getCar().brand + " " + rc.getCar().model +
                               " License plate: " + rc.getCar().regplate);
        }


        System.out.println("Enter registration number to mark contract as completed");
        String registrationsplate = sc.nextLine();
        for (RentalContract rc : rd.rentalContractList) {
            if (rc.isCompleted()) {
                continue;
            }
            if (Objects.equals(registrationsplate, rc.getCar().regplate)) {
                // Markerer lejekontrakten som færdig, baseret på registreringsnummeret
                rd.setRentalContractToCompleted(registrationsplate);
                // Opdaterer bilens tilgængelighed
                cd.updateIsAvailable(rc.getCar());
                //Fjerner lejekontrakten fra listen med lejekontrakter og returnerer om listen blev fjernet
                boolean deleted = rd.rentalContractList.remove(rc);
                System.out.println(deleted);
                break;
            }
        }
    }


    public Renter selectRenter() {
        Scanner sc = new Scanner(System.in);
        // Viser listen af kunder / Renters
        rd.displayCustomers();
        System.out.println("Choose a renter and enter the unique number: ");
        int renterValg = Integer.parseInt(sc.nextLine());
        if (renterValg >= 0 && renterValg <= rd.Renters.size()) {
            return rd.Renters.get(renterValg - 1);
        } else {
            System.out.println("Invalid renter number, please try again");
            return null;
        }
    }


    public Car selectCar() {
        Scanner sc = new Scanner(System.in);
        cd.showAllCars(true);
        System.out.println("Choose a car by entering the unique number: ");
        int carValg = Integer.parseInt(sc.nextLine());
        if (carValg > 0 && carValg <= cd.allCars.size()) {
            Car car = cd.allCars.get(carValg - 1);
            System.out.println(car);
            return car;
        } else {
            System.out.println("Invalid car number, please try again");
            return null;
        }
    }

    public void createRentalContract() {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("1. Use existing customer: ");
        System.out.println("2. Create new customer: ");

        int valg;
        try {
            valg = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        Renter selectedRenter;
        Car selectedCar;

        switch (valg) {
            case 1:
                selectedRenter = selectRenter();
                selectedCar = selectCar();
                break;
            case 2:
                selectedRenter = addRentertoSystem();
                selectedCar = selectCar();
                break;
            default:
                System.out.println("bad choice, try again");
                return;
        }

        Date startDate = null, endDate = null;

        while (startDate == null) {
            try {
                System.out.println("Enter the rental start date (YYYY-MM-DD):");
                String input = sc.nextLine();
                startDate = dateFormat.parse(input);

                if (startDate.before(Date.from(Instant.now()))) {
                    System.out.println("Start date cannot be in the past. Try again.");
                    startDate = null;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again (YYYY-MM-DD).");
            }
        }


        while (endDate == null) {
            try {
                System.out.println("Enter the return date (YYYY-MM-DD):");
                String input = sc.nextLine();
                endDate = dateFormat.parse(input);

                if (endDate.before(startDate)) {
                    System.out.println("Return date must be after the start date. Try again.");
                    endDate = null;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again (YYYY-MM-DD).");
            }
        }

        System.out.println("Enter the max number of kilometers the car will be driven: ");
        int maxkm = Integer.parseInt(sc.nextLine());

        System.out.println("Odometer for selected car: " +selectedCar.odometer);
        System.out.println("Enter the current number of kilometers on the odometer: ");
        int odometerStart = Integer.parseInt(sc.nextLine());

        System.out.println(selectedCar);
        System.out.println("Enter the registration plate on the car that needs to be rented: ");
        String registrationPlate = sc.nextLine();

        RentalContract rc = new RentalContract(0,selectedRenter, selectedCar, startDate, endDate, maxkm, odometerStart, registrationPlate, false);
        cd.updateIsAvailable(selectedCar);

        rd.rentalContractList.add(rc);
        rd.insertRentalContractToSQL(rc);
        System.out.println("Rental contract successfully created!");
    }


    public void deleteRenterFromSystem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Delete renter from system with email");
        System.out.println("2. Delete renter from system with Driver license number");
        int valg = Integer.parseInt(sc.nextLine());
        switch (valg) {
            case 1:
                System.out.println("Enter Renter email to delete renter from system");
                String email = sc.nextLine();
                rd.deleteRenterFromSystemEmail(email);
                break;
            case 2:
                System.out.println("Enter renter driver license number to delete renter from system");
                String DLN = sc.nextLine();
                rd.deleteRenterFromSystemDLN(DLN);
                break;

        }
    }

    public Renter addRentertoSystem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Renter Name: ");
        String name = sc.nextLine();
        System.out.println("Enter Renter Address: ");
        String address = sc.nextLine();
        System.out.println("Enter Zip Code for Renter: ");
        String zipCode = sc.nextLine();
        System.out.println("Enter city for Renter: ");
        String city = sc.nextLine();
        System.out.println("Enter Renter mobile phone: ");
        String mobilephone = sc.nextLine();
        System.out.println("Enter Renter phone\n (leave blank if renter only has mobile phone)");
        String phone = sc.nextLine();
        System.out.println("Enter Renter Email: ");
        String email = sc.nextLine();
        System.out.println("Enter Driver License Number: ");
        String license = sc.nextLine();
        System.out.println("Enter Driver Since Date");
        String date = sc.nextLine();
        int id = rd.addToDatabase(name, address, zipCode, city, mobilephone, phone, email, license, date);
        Renter r = new Renter(id, name, address, zipCode, city, mobilephone, phone, email, license, date);
        rd.Renters.add(r);
        return r;
    }


    public void DeleteCarFromInventory() {

        Scanner sc = new Scanner(System.in);
        System.out.println("1. Delete car from inventory with ID");
        System.out.println("2. Delete car from inventory with Registration_plate");
        int valg = Integer.parseInt(sc.nextLine());

        switch (valg) {
            case 1:
                cd.showAllCars(false);
                System.out.println("Enter Car ID to delete car from inventory: ");
                int id = Integer.parseInt(sc.nextLine());
                cd.deleteCarFromInventoryID(id);

                break;
            case 2:
                cd.showAllCars(false);
                System.out.println("Enter Car registration plate to delete car from inventory: ");
                String regi = sc.nextLine();
                cd.deleteCarFromInventoryREG(regi);

                break;
        }
    }


    public void addCarToInventory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. for luxury car");
        System.out.println("2. for family car");
        System.out.println("3. for sportscar");

        int valg = Integer.parseInt(sc.nextLine());

        switch (valg) {
            case 1:
                String car_type = "LUXURY";
                Fueltype Lfueltype = null;

                System.out.println("Car brand: ");
                String Lbrand = sc.nextLine();
                System.out.println("Car model: ");
                String Lmodel = sc.nextLine();
                System.out.println("Fuel type: \n 1. Diesel \n 2. Gasoline \n 3. Hybrid \n 4. Electric");
                int fuel = Integer.parseInt(sc.nextLine());
                if (fuel == 1) {
                    Lfueltype = Fueltype.DIESEL;
                } else if (fuel == 2) {
                    Lfueltype = Fueltype.GASOLINE;
                } else if (fuel == 3) {
                    Lfueltype = Fueltype.HYBRID;
                } else if (fuel == 4) {
                    Lfueltype = Fueltype.ELECTRIC;
                }
                System.out.println("Car registration plate: ");
                String Lregplate = sc.nextLine();
                System.out.println("Enter first registration year and month");
                String LregYearMonth = sc.nextLine();
                System.out.println("Enter odometer");
                int Lodometer = Integer.parseInt(sc.nextLine());
                System.out.println("Enter cc: \n(Minimum 3000 for luxury cars)");
                int Lcc = Integer.parseInt(sc.nextLine());
                System.out.println("Geartype: Automatic");
                System.out.println("hasAirCon = true");
                System.out.println("hasCruiseControl = true");
                System.out.println("hasLeatherSeats = true");
                boolean LisManual = false;
                boolean LhasAirCon = true;
                boolean LhasCruiseControl = true;
                boolean LhasLeatherSeats = true;
                System.out.println("How many seats? ");
                int Lseats = Integer.parseInt(sc.nextLine());
                System.out.println("How much horsepower?");
                int Lhp = Integer.parseInt(sc.nextLine());
                int id = cd.addToDatabase(Lbrand, Lmodel, Lfueltype, Lregplate, LregYearMonth, Lodometer, Lcc, LisManual, LhasAirCon, LhasCruiseControl, LhasLeatherSeats, Lseats, Lhp, car_type);
                LuxuryCar l = new LuxuryCar(id, Lbrand, Lmodel, Lfueltype, Lregplate, LregYearMonth, Lodometer, Lcc, LisManual, LhasAirCon, LhasCruiseControl, LhasLeatherSeats, Lseats, Lhp, true);
                cd.LUXURYCARS.add(l);
                break;
            case 2:
                Fueltype Ffueltype = null;
                String Fcar_type = "FAMILY";
                System.out.println("Car brand: ");
                String Fbrand = sc.nextLine();
                System.out.println("Car model: ");
                String Fmodel = sc.nextLine();
                System.out.println("Fuel type: \n 1. Diesel \n 2. Gasoline \n 3. Hybrid \n 4. Electric");
                int Ffuel = Integer.parseInt(sc.nextLine());
                if (Ffuel == 1) {
                    Ffueltype = Fueltype.DIESEL;
                } else if (Ffuel == 2) {
                    Ffueltype = Fueltype.GASOLINE;
                } else if (Ffuel == 3) {
                    Ffueltype = Fueltype.HYBRID;
                } else if (Ffuel == 4) {
                    Ffueltype = Fueltype.ELECTRIC;
                }
                System.out.println("Car registration plate: ");
                String Fregplate = sc.nextLine();
                System.out.println("Enter first registration year and month");
                String FregYearMonth = sc.nextLine();
                System.out.println("Enter odometer");
                int Fodometer = Integer.parseInt(sc.nextLine());
                System.out.println("Enter cc: ");
                int Fcc = Integer.parseInt(sc.nextLine());
                System.out.println("Geartype: Manual");
                boolean FisManual = true;
                System.out.println("Aircon: true");
                boolean FhasAirCon = true;
                System.out.println("Cruise control? \n 1 for yes \n 2 for false");
                boolean FhasCruiseControl = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Leather seats? \n 1 for yes \n 2 for false");
                boolean FhasLeatherSeats = Boolean.parseBoolean(sc.nextLine());
                System.out.println("This car type has atleast 7 seats\nHow many seats? ");
                int Fseats = Integer.parseInt(sc.nextLine());
                System.out.println("How much horsepower?");
                int Fhp = Integer.parseInt(sc.nextLine());
                int id2 = cd.addToDatabase(Fbrand, Fmodel, Ffueltype, Fregplate, FregYearMonth, Fodometer, Fcc, FisManual, FhasAirCon, FhasCruiseControl, FhasLeatherSeats, Fseats, Fhp, Fcar_type);
                FamilyCar F = new FamilyCar(id2, Fbrand, Fmodel, Ffueltype, Fregplate, FregYearMonth, Fodometer, Fcc, FisManual, FhasAirCon, FhasCruiseControl, FhasLeatherSeats, Fseats, Fhp, true);
                cd.FAMILYCARS.add(F);
                break;
            case 3:
                Fueltype Sfueltype = null;
                String sCar_type = "SPORT";
                System.out.println("Car brand: ");
                String Sbrand = sc.nextLine();
                System.out.println("Car model: ");
                String Smodel = sc.nextLine();
                System.out.println("Fuel type: \n 1. Diesel \n 2. Gasoline \n 3. Hybrid \n 4. Electric");
                int Sfuel = Integer.parseInt(sc.nextLine());
                if (Sfuel == 1) {
                    Sfueltype = Fueltype.DIESEL;
                } else if (Sfuel == 2) {
                    Sfueltype = Fueltype.GASOLINE;
                } else if (Sfuel == 3) {
                    Sfueltype = Fueltype.HYBRID;
                } else if (Sfuel == 4) {
                    Sfueltype = Fueltype.ELECTRIC;
                }
                System.out.println("Car registration plate: ");
                String Sregplate = sc.nextLine();
                System.out.println("Enter first registration year and month");
                String SregYearMonth = sc.nextLine();
                System.out.println("Enter odometer");
                int Sodometer = Integer.parseInt(sc.nextLine());
                System.out.println("Enter cc: ");
                int Scc = Integer.parseInt(sc.nextLine());
                System.out.println("Geartype: Manual");
                boolean SisManual = true;
                System.out.println("Air con? \n 1 for yes \n 0 for false");
                boolean ShasAirCon = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Cruise control? \n 1 for yes \n 2 for false");
                boolean ShasCruiseControl = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Leather seats? \n 1 for yes \n 2 for false");
                boolean ShasLeatherSeats = Boolean.parseBoolean(sc.nextLine());
                System.out.println("How many seats? ");
                int Sseats = Integer.parseInt(sc.nextLine());
                System.out.println("Atleast 200 HP\nHow much horsepower?");
                int Shp = Integer.parseInt(sc.nextLine());
                int idd = cd.addToDatabase(Sbrand, Smodel, Sfueltype, Sregplate, SregYearMonth, Sodometer, Scc, SisManual, ShasAirCon, ShasCruiseControl, ShasLeatherSeats, Sseats, Shp, sCar_type);
                SportsCar S = new SportsCar(idd,Sbrand, Smodel, Sfueltype, Sregplate, SregYearMonth, Sodometer, Scc, SisManual, ShasAirCon, ShasCruiseControl, ShasLeatherSeats, Sseats, Shp, true);
                cd.SPORTCARS.add(S);
                break;
        }

    }



}
