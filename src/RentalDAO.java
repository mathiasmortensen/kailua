import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class RentalDAO {
    Connection con;
    ArrayList<Renter> Renters = new ArrayList<Renter>();
    ArrayList<RentalContract> rentalContractList = new ArrayList<>();
    boolean isLoadet = false;

    public RentalDAO() {
    }

    public void defaultData(){
        String insertQueryRenter = "INSERT INTO kailua.renter (id, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) VALUES (1, 'Valdemar', 'valdevej 19', '2222', 'København', '22113344', '', 'valde@valde.dk', 'valde222', '2024-02-19');\n" +
                                   "INSERT INTO kailua.renter (id, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) VALUES (2, 'Mathias', 'Ådalsvej 7', '2635', 'Ishoej', '30569972', '', 'mamo0010@stud.kea.dk', 'daskop', '2022-04-20');\n" +
                                   "INSERT INTO kailua.renter (id, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) VALUES (3, 'Sebastian', 'Østergaarden 66', '2635', 'Ishøj', '93960307', '', 'seb@gmail.com', 'BBL243556', '2022-04-20');\n";
        String insertQueryCar = "\n" +
                                "INSERT INTO kailua.car (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) VALUES (1, 'Mazda', 'MX5', 'GASOLINE', 'JK12345', '2025-02', 0, 200, 1, 0, 0, 0, 2, 300, 'SPORT', 1);\n" +
                                "INSERT INTO kailua.car (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) VALUES (2, 'Toyota', 'Astra', 'HYBRID', 'GG12345', '2025-02', 0, 3000, 1, 1, 0, 0, 7, 1000, 'FAMILY', 1);\n" +
                                "INSERT INTO kailua.car (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) VALUES (3, 'Bentley', 'Bentayaga', 'HYBRID', 'GG44667', '2025-02', 0, 6000, 0, 1, 1, 1, 7, 1000, 'LUXURY', 1);\n";

        String insertQueryRentalContract = "INSERT INTO kailua.rentalcontract (id, renter_id, car_id, from_date_time, to_date_time, max_km, start_odometer, registration_plate, is_contract_completed) VALUES (1, 1, 1, '2024-02-21 00:00:00', '2025-02-21 00:00:00', 1000, 0, 'JK12345', null);\n" +
                                           "INSERT INTO kailua.rentalcontract (id, renter_id, car_id, from_date_time, to_date_time, max_km, start_odometer, registration_plate, is_contract_completed) VALUES (2, 2, 2, '2024-02-21 00:00:00', '2025-03-21 00:00:00', 1000, 0, 'GG12345', null);\n" +
                                           "INSERT INTO kailua.rentalcontract (id, renter_id, car_id, from_date_time, to_date_time, max_km, start_odometer, registration_plate, is_contract_completed) VALUES (3, 3, 3, '2024-02-24 00:00:00', '2026-03-21 00:00:00', 2000, 0, 'GG44667', null);\n";



        try(Connection con = SQLDAO.SQLConnection()){
            PreparedStatement ps = con.prepareStatement(insertQueryRenter);
            PreparedStatement ps2 = con.prepareStatement(insertQueryCar);
            PreparedStatement ps3 = con.prepareStatement(insertQueryRentalContract);

            ps.execute();
            ps2.execute();
            ps3.execute();
            


        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void insertRentalContractToSQL(RentalContract contract){
        String query = "INSERT INTO rentalcontract (renter_id, car_id, from_date_time, to_date_time, max_km, " +
                       "start_odometer, registration_plate, is_contract_completed) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, contract.getRenter().getRenterID());
            pstmt.setInt(2, contract.getCar().id);

            // Konverterer fra java.util.Date til java.sql.Date
            pstmt.setDate(3, new java.sql.Date(contract.getRentedFrom().getTime()));
            pstmt.setDate(4, new java.sql.Date(contract.getRentedTo().getTime()));

            pstmt.setInt(5, contract.getMaxkm());
            pstmt.setInt(6, contract.getKmrentedStart());
            pstmt.setString(7, contract.getRegistrationNumber());
            pstmt.setBoolean(8, false); // Standard er ikke færdiggjort

            // Kør SQL INSERT (executeUpdate i stedet for executeQuery)
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " rental contract(s) inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace(); // Giver mere detaljeret fejlmeddelelse
        }
    }



    public int addToDatabase(String name, String address, String zip, String city, String mobile_phone, String phone, String email, String driver_license_number, String driver_since) {
        String Lquery = "INSERT INTO Renter (id,name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) Values (?,?,?,?,?,?,?,?,?,?) ";
        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmt = con.prepareStatement(Lquery);
            pstmt.setInt(1, getNextAvailableID());
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            pstmt.setString(4, zip);
            pstmt.setString(5, city);
            pstmt.setString(6, mobile_phone);
            pstmt.setString(7, phone);
            pstmt.setString(8, email);
            pstmt.setString(9, driver_license_number);
            pstmt.setString(10, driver_since);
            pstmt.executeUpdate();


            System.out.println("Customer (renter) added!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getNextAvailableID();
    }

    public void displayCustomers() {
        System.out.println("Customers: \n-----------");
        for (Renter r : Renters) {
            System.out.println(r.toString());
            System.out.println("-----------");
        }

    }

    public int getNextAvailableID() {

        String query = "select min(id + 1) as next_id from renter t1 where not exists(select 1 from renter t2 where t2.id = t1.id + 1)";

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



    public void deleteRenterFromSystemDLN(String driver_license_number) {
        String querySelect = "Select email from renter where driver_license_number = ?";
        String queryDelete = "DELETE FROM renter WHERE driver_license_number = ?";


        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmtSelect = con.prepareStatement(querySelect);
            PreparedStatement pstmtDelete = con.prepareStatement(queryDelete);

            pstmtSelect.setString(1, driver_license_number);

            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                pstmtDelete.setString(1, driver_license_number);

                int rowsAffected = pstmtDelete.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Customer (renter) deleted!");

                    Iterator<Renter> iterator = Renters.iterator();
                    while (iterator.hasNext()) {
                        Renter r = iterator.next();
                        if (r.getEmail().equals(r.getEmail())) {
                            iterator.remove();
                        }
                    }

                } else {
                    System.out.println("Customer (renter) not deleted!");
                }
            } else {
                System.out.println("Customer (renter) with driver license number " + driver_license_number + " not found!");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRentalContractToCompleted(String registration_plate) {
        String queryUpdate = "UPDATE rentalcontract SET is_contract_completed = 1 WHERE registration_plate = ?";
        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmt = con.prepareStatement(queryUpdate);
            pstmt.setString(1, registration_plate);
            pstmt.executeUpdate();

            System.out.println("Rental contract updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deleteRenterFromSystemEmail(String email) {
        String querySelect = "Select email from renter where email = ?";
        String queryDelete = "DELETE FROM renter WHERE email = ?";


        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmtSelect = con.prepareStatement(querySelect);
            PreparedStatement pstmtDelete = con.prepareStatement(queryDelete);

            pstmtSelect.setString(1, email);

            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                pstmtDelete.setString(1, email);

                int rowsAffected = pstmtDelete.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Customer (renter) deleted!");

                    Iterator<Renter> iterator = Renters.iterator();
                    while (iterator.hasNext()) {
                        Renter r = (Renter) iterator.next();
                        if (r.getEmail().equals(email)) {
                            iterator.remove();
                        }
                    }

                } else {
                    System.out.println("Customer (renter) not deleted!");
                }
            } else {
                System.out.println("Customer (renter) with email " + email + " not found!");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void generateRenterObjectFromSQL() {
        String query = "Select * from renter";

        if (!isLoadet) {
            try (Connection con = SQLDAO.SQLConnection()) {
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int renterID = rs.getInt("id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String zip = rs.getString("zip");
                    String city = rs.getString("city");
                    String mobile_phone = rs.getString("mobile_phone");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String driver_license_number = rs.getString("driver_license_number");
                    String driver_since = rs.getString("driver_since");
                    Renter r = new Renter(renterID, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since);
                    Renters.add(r);
                }
                isLoadet = true;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Customers already loaded!");
        }

    }

    public void generateRentalContractObjectFromSQL() {
        String query = "Select * from rentalcontract";
        try (Connection con = SQLDAO.SQLConnection()) {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int contractID = rs.getInt("id");
                int renterID = rs.getInt("renter_id");
                int carID = rs.getInt("car_id");
                Date fromDate = rs.getDate("from_date_time");
                Date toDate = rs.getDate("to_date_time");
                int maxkm = rs.getInt("max_km");
                int startodometer = rs.getInt("start_odometer");
                String registraion_plate = rs.getString("registration_plate");
                boolean isCompleted = rs.getBoolean("is_contract_completed");
                RentalContract rc = new RentalContract(contractID, renterID, carID, fromDate, toDate, maxkm, startodometer, registraion_plate, isCompleted);
                rentalContractList.add(rc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
