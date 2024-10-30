package src.controller;

public class Queries{
            public static String adminViewOrg = "SELECT  u.name, u.email, u.mobile_number, r.role " +
            "FROM users u " +
            "JOIN roles r ON u.userid = r.userid " +
            "WHERE r.role = 'organizer'";

            public static String adminViewCust = "SELECT  u.name, u.email, u.mobile_number, r.role " +
                    "FROM users u " +
                    "JOIN roles r ON u.userid = r.userid " +
                    "WHERE r.role = 'customer'";

            public static String adminViewBook = "SELECT b.id AS booking_id, u.name AS customer_name, v.venue_name, b.event_date, b.event_cost, e.event_name " +
                    "FROM bookings b " +
                    "JOIN venue v ON b.venue_id = v.id " +
                    "JOIN events e ON b.event_id = e.id " +
                    "JOIN users u ON b.user_id = u.userid";
            
            public static String adminViewVenue = "SELECT * FROM venue";
            
            public static String ViewCustBook = "SELECT b.id AS booking_id, v.venue_name, v.venue_place, e.event_name, "
                    + "b.event_date, b.event_time, b.total_amount "
                    + "FROM bookings b "
                    + "JOIN venue v ON b.venue_id = v.id "
                    + "JOIN events e ON b.event_id = e.id "
                    + "WHERE b.user_id = ?;";
            
            public static String viewCustUpBook = "SELECT b.id AS booking_id, v.venue_name, v.venue_place, e.event_name, "
                    + "b.event_date, b.event_time, b.total_amount "
                    + "FROM bookings b "
                    + "JOIN venue v ON b.venue_id = v.id "
                    + "JOIN events e ON b.event_id = e.id "
                    + "WHERE b.user_id = ? AND b.event_date >= ?;";
            
            public static String CustProfile ="SELECT u.userid, u.name, u.email, u.mobile_number, r.role " +
                    "FROM users u " +
                    "JOIN roles r ON u.userid = r.userid " +
                    "WHERE u.userid = ?";
            
            public static String OrgProfile = "SELECT u.userid, u.name, u.email, u.mobile_number, r.role " +
                    "FROM users u " +
                    "JOIN roles r ON u.userid = r.userid " +
                    "WHERE u.userid = ?";
            
            public static String InsertBooking = "INSERT INTO bookings (user_id, venue_id, event_id, event_date, event_time, event_cost, food_cost, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
            
            public static String InsertPaymentDetails = "INSERT INTO payment_detail (booking_id, advance_payment, available_payment) VALUES (?, ?, ?)";
            
            public static String FetchVenue = "SELECT id, venue_name, venue_place FROM venue";
            
            public static String FetchEvent = "SELECT * FROM events WHERE venue_id = ?";
            
            public static String FetchFood = "SELECT * FROM fooditems WHERE venue_id = ?";
            
             public static String FetchDateForBooking = "SELECT COUNT(*) FROM bookings WHERE venue_id = ? AND event_date = ?";
            
            public static String orgBooking = "SELECT b.id AS booking_id, u.name AS customer_name, u.mobile_number, "
                + "v.venue_name, v.venue_place, e.event_name, "
                + "b.event_date, b.event_time, b.total_amount "
                + "FROM bookings b "
                + "JOIN users u ON b.user_id = u.userid "
                + "JOIN venue v ON b.venue_id = v.id "
                + "JOIN events e ON b.event_id = e.id "
                + "WHERE v.organizer_id = ?;";

            public static String orgUpBooking = "SELECT b.id AS booking_id, u.name AS customer_name, u.mobile_number," +
                                   "v.venue_name, v.venue_place, e.event_name," +
                                  "b.event_date, b.event_time, b.total_amount " +
                                  "FROM bookings b " +
                                  "JOIN users u ON b.user_id = u.userid " +
                                  "JOIN venue v ON b.venue_id = v.id " +
                                  "JOIN events e ON b.event_id = e.id " +
                                  "WHERE v.organizer_id = ? " +
                                  "AND b.event_date >= ?;";
            
            public static String AdvInserPaymentProcess = "INSERT INTO payment (booking_id, payment_method, amount_paid, payment_status) VALUES (?, ?, ?, ?)";
            
            public static String AdvUpdatePaymentProcess = "UPDATE payment_detail SET advance_payment = advance_payment + ?, available_payment = available_payment - ? WHERE booking_id = ?";
            
            public static String FullInserPaymentProcess = "INSERT INTO payment (booking_id, payment_method, amount_paid, payment_status) VALUES (?, ?, ?, ?)";
            
            public static String FullUpdatePaymentProcess = "UPDATE payment_detail SET available_payment = 0 WHERE booking_id = ?";
            
            public static String PaymentDetails = "SELECT * FROM payment_detail WHERE booking_id = ?";
            
            public static String SelectUserCred = "SELECT userid, password FROM usercredential WHERE TRIM(username) = ? ;";
            
            public static String SelectUserRole="SELECT role FROM roles WHERE userid = ?;";
            
            public static String SelectUserId = "SELECT * FROM users WHERE userid = ?;";

            public static String InserVenue = "INSERT INTO venue (venue_name, venue_place, venue_contact, venue_email, capacity, ac, organizer_id) VALUES (?, ?, ?, ?, ?, ?::venue_ac, ?)";

            public static String SelectEventForInsert = "SELECT venue_email, id FROM venue WHERE organizer_id = ?";

            public static String InserEvent ="INSERT INTO events (venue_id, event_name, event_cost) VALUES (?, ?, ?)";

            public static String SelectFoodForInset = "SELECT id, venue_email FROM venue WHERE organizer_id = ?";

            public static String InsertFood = "INSERT INTO fooditems (venue_id, food_name, food_cost) VALUES (?, ?, ?)";

            public static String PendingAmount = "SELECT pd.booking_id, pd.available_payment " +
                    "FROM payment_detail pd " +
                    "JOIN payment p ON pd.booking_id = p.booking_id " +
                    "JOIN bookings b ON pd.booking_id = b.id " +
                    "WHERE p.payment_status = 'Pending' AND b.user_id = ? AND pd.available_payment != 0.0";

            public static String SelectEventForAddVenue ="SELECT * FROM venue WHERE venue_email = ?";

            public static String SelectFoodForAddVenue = "SELECT * FROM venue WHERE venue_email = ?";

}
