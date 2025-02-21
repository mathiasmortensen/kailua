import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CarDAO {

    Connection con;
    ArrayList<Car> LUXURYCARS = new ArrayList<>();
    ArrayList<Car> FAMILYCARS = new ArrayList<>();
    ArrayList<Car> SPORTCARS = new ArrayList<>();
    ArrayList<Car> allCars = new ArrayList<>();
    ArrayList<Car> rentedCars = new ArrayList<>();
    boolean isLoaded;

    public CarDAO() {
    }

    public boolean carIsRented(String reg_plate) {
        int found = 0;
        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM rentalcontract WHERE plate = ? AND is_contract_completed = 0");
            ps.setString(1, reg_plate);
            ResultSet rs = ps.executeQuery();
            found = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found > 0;
    }

    public boolean carIsRented(int id) {
        int found = 0;
        try (Connection con = SQLDAO.SQLConnection()) {
            // Hent om bilen er udlejet ved at tælle antal rækker i rentalcontract hvor id = id og is_contract_completed = 0
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM rentalcontract WHERE id = ? AND is_contract_completed = 0");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            found = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Returner om found er over 0
        return found > 0;
    }

    public void updateIsAvailable(Car car) {
        car.isAvailable = !car.isAvailable;
        String query = "update Car SET is_available = ? WHERE registration_plate = ?";
        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement psmt = con.prepareStatement(query);
            psmt.setBoolean(1, car.isAvailable);
            psmt.setString(2, car.regplate);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getNextAvailableID() {

        String query = "select min(id + 1) as next_id from car t1 where not exists(select 1 from car t2 where t2.id = t1.id + 1)";

        int nextID = 1;
        try (Connection con = SQLDAO.SQLConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next() && rs.getInt("next_id") > 0) {
                nextID = rs.getInt("next_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nextID;
    }


    public int addToDatabase(String brand, String model, Fueltype fuel_type, String regplate, String regYearMonth, int odometer, int cc, boolean isManual, boolean hasAirCon, boolean hasCruiseControl, boolean hasLeatherSeats, int seats, int hp, String car_type) {
        String Lquery = """
                INSERT INTO Car \
                (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, 
                 has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) \
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)""";
        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmt = con.prepareStatement(Lquery);
            int nextID = getNextAvailableID();
            pstmt.setInt(1, nextID);
            pstmt.setString(2, brand);
            pstmt.setString(3, model);
            pstmt.setString(4, fuel_type.toString());
            pstmt.setString(5, regplate);
            pstmt.setString(6, regYearMonth);
            pstmt.setInt(7, odometer);
            pstmt.setInt(8, cc);
            pstmt.setBoolean(9, isManual);
            pstmt.setBoolean(10, hasAirCon);
            pstmt.setBoolean(11, hasCruiseControl);
            pstmt.setBoolean(12, hasLeatherSeats);
            pstmt.setInt(13, seats);
            pstmt.setInt(14, hp);
            pstmt.setString(15, car_type);
            pstmt.setInt(16, 1);
            pstmt.executeUpdate();


            System.out.println("Car added!");
            return nextID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void showAllCars(boolean showOnlyAvailableCars) {
        for (Car car : LUXURYCARS) {
            if (showOnlyAvailableCars && !car.isAvailable) {
                continue;
            }
            System.out.println("Luxury Car: \n -----------");
            System.out.println(car);
            System.out.println("------------\n");
        }
        for (Car car : FAMILYCARS) {
            if (showOnlyAvailableCars && !car.isAvailable) {
                continue;
            }
            System.out.println("Family Car: \n -----------");
            System.out.println(car);
            System.out.println("------------\n");
        }
        for (Car car : SPORTCARS) {
            if (showOnlyAvailableCars && !car.isAvailable) {
                continue;
            }
            System.out.println("Sport Car: \n ------------");
            System.out.println(car);
            System.out.println("------------\n");
        }
    }

    public void deleteCarFromInventoryID(int id) {
        System.out.println(rentedCars.size());
        for (Car car : rentedCars) {
            if (car.id == id) {
                System.out.println("Car cannot be deleted while it is rented");
                return;
            }
        }


        String querySelect = "SELECT id FROM car WHERE id = ?";
        String queryDelete = "DELETE FROM car WHERE id = ?";

        try (Connection con = SQLDAO.SQLConnection();
             PreparedStatement selectStmt = con.prepareStatement(querySelect);
             PreparedStatement deleteStmt = con.prepareStatement(queryDelete)) {

            selectStmt.setInt(1, id);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                deleteStmt.setInt(1, id);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Bilen med ID " + id + " blev slettet.");

                    Iterator<Car> iterator = LUXURYCARS.iterator();
                    Iterator<Car> iterator2 = FAMILYCARS.iterator();
                    Iterator<Car> iterator3 = SPORTCARS.iterator();

                    while (iterator.hasNext()) {
                        Car car = iterator.next();
                        if (car.getId() == id) {
                            iterator.remove();
                        }
                    }
                    while (iterator2.hasNext()) {
                        Car car = iterator2.next();
                        if (car.getId() == id) {
                            iterator2.remove();
                        }
                    }
                    while (iterator3.hasNext()) {
                        Car car = iterator3.next();
                        if (car.getId() == id) {
                            iterator3.remove();
                        }
                    }

                } else {
                    System.out.println("Ingen biler blev slettet.");
                }
            } else {
                System.out.println("Ingen bil med ID " + id + " blev fundet.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteCarFromInventoryREG(String registration_plate) {
        if (carIsRented(registration_plate)) {
            System.out.println("Car cannot be deleted while it is rented");
            return;
        }

        String queryDeleteContract = "DELETE FROM car WHERE registration_plate = ?";
        String querySelect = "SELECT registration_plate FROM car WHERE registration_plate = ?";
        String queryDelete = "DELETE FROM car WHERE registration_plate = ?";

        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement selectStmt = con.prepareStatement(querySelect);
            PreparedStatement deleteStmt = con.prepareStatement(queryDelete);
            PreparedStatement deleteContractStmt = con.prepareStatement(queryDeleteContract);
            selectStmt.setString(1, registration_plate);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {

                deleteStmt.setString(1, registration_plate);
                deleteContractStmt.setString(1, registration_plate);

                deleteContractStmt.executeUpdate();
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Car with Registration plate " + registration_plate + " blev slettet.");

                    Iterator<Car> iterator = LUXURYCARS.iterator();
                    Iterator<Car> iterator2 = FAMILYCARS.iterator();
                    Iterator<Car> iterator3 = SPORTCARS.iterator();
                    while (iterator.hasNext()) {
                        Car c = iterator.next();
                        if (c.getRegplate().equals(registration_plate)) {
                            iterator.remove();
                        }
                    }
                    while (iterator2.hasNext()) {
                        Car c = iterator2.next();
                        if (c.getRegplate().equals(registration_plate)) {
                            iterator2.remove();
                        }
                    }
                    while (iterator3.hasNext()) {
                        Car c = iterator3.next();
                        if (c.getRegplate().equals(registration_plate)) {
                            iterator3.remove();
                        }
                    }


                } else {
                    System.out.println("No cars were deleted");
                }
            } else {
                System.out.println("No car with registration plate " + registration_plate + " was found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void GenerateCarObjectFromSQL() {
        if (!isLoaded) {
            String select = "SELECT * FROM car";
            try (Connection con = SQLDAO.SQLConnection()) {
                PreparedStatement pstmt = con.prepareStatement(select);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String carType = rs.getString("car_type");
                    if ("LUXURY".equals(carType)) {
                        int id = rs.getInt("id");
                        String brand = rs.getString("brand");
                        String model = rs.getString("model");
                        Fueltype fuel_type = Fueltype.valueOf(rs.getString("fuel_type"));
                        String regplate = rs.getString("registration_plate");
                        String regYearMonth = rs.getString("registration_year_month");
                        int odometer = rs.getInt("odometer");
                        int engine_cc = rs.getInt("engine_cc");
                        boolean is_manual = rs.getBoolean("is_manual");
                        boolean hasAirCon = rs.getBoolean("has_aircon");
                        boolean hasCruiseControl = rs.getBoolean("has_cruise_control");
                        boolean hasLeatherSeats = rs.getBoolean("has_leather_seats");
                        int seats = rs.getInt("seats");
                        int hp = rs.getInt("horsepower");
                        boolean is_available = rs.getBoolean("is_available");
                        LuxuryCar l = new LuxuryCar(id, brand, model, fuel_type, regplate, regYearMonth, odometer, engine_cc, is_manual, hasAirCon, hasCruiseControl, hasLeatherSeats, seats, hp, is_available);
                        LUXURYCARS.add(l);
                        allCars.add(l);

                    } else if ("FAMILY".equals(carType)) {
                        int id = rs.getInt("id");
                        String brand = rs.getString("brand");
                        String model = rs.getString("model");
                        Fueltype fuel_type = Fueltype.valueOf(rs.getString("fuel_type"));
                        String regplate = rs.getString("registration_plate");
                        String regYearMonth = rs.getString("registration_year_month");
                        int odometer = rs.getInt("odometer");
                        int engine_cc = rs.getInt("engine_cc");
                        boolean is_manual = rs.getBoolean("is_manual");
                        boolean hasAirCon = rs.getBoolean("has_aircon");
                        boolean hasCruiseControl = rs.getBoolean("has_cruise_control");
                        boolean hasLeatherSeats = rs.getBoolean("has_leather_seats");
                        int seats = rs.getInt("seats");
                        int hp = rs.getInt("horsepower");
                        boolean is_available = rs.getBoolean("is_available");
                        FamilyCar f = new FamilyCar(id, brand, model, fuel_type, regplate, regYearMonth, odometer, engine_cc, is_manual, hasAirCon, hasCruiseControl, hasLeatherSeats, seats, hp, is_available);
                        FAMILYCARS.add(f);
                        allCars.add(f);

                    } else if ("SPORT".equals(carType)) {
                        int id = rs.getInt("id");
                        String brand = rs.getString("brand");
                        String model = rs.getString("model");
                        Fueltype fuel_type = Fueltype.valueOf(rs.getString("fuel_type"));
                        String regplate = rs.getString("registration_plate");
                        String regYearMonth = rs.getString("registration_year_month");
                        int odometer = rs.getInt("odometer");
                        int engine_cc = rs.getInt("engine_cc");
                        boolean is_manual = rs.getBoolean("is_manual");
                        boolean hasAirCon = rs.getBoolean("has_aircon");
                        boolean hasCruiseControl = rs.getBoolean("has_cruise_control");
                        boolean hasLeatherSeats = rs.getBoolean("has_leather_seats");
                        int seats = rs.getInt("seats");
                        int hp = rs.getInt("horsepower");
                        boolean is_available = rs.getBoolean("is_available");
                        SportsCar s = new SportsCar(id, brand, model, fuel_type, regplate, regYearMonth, odometer, engine_cc, is_manual, hasAirCon, hasCruiseControl, hasLeatherSeats, seats, hp, is_available);
                        SPORTCARS.add(s);
                        allCars.add(s);
                    }
                }
                isLoaded = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Cars already loaded");
        }

    }

}



