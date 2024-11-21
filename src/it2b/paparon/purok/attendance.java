package it2b.paparon.purok;

import it2b.paparon.purok.activities;
import java.sql.SQLException;
import java.util.Scanner;

public class attendance {

    public static void handleMenu(Scanner sc) {
        int choice;

        do {
            System.out.println("|---------------------------------------------|");
            System.out.println("|                  Attendance                 |");
            System.out.println("|---------------------------------------------|");
            System.out.println("1. Record Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Update Attendance");
            System.out.println("4. Delete Attendance");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            
          
            
             while (!sc.hasNextInt()) {
                System.out.println("Invalid input, please enter a valid number.");
                sc.nextLine();  
               System.out.print("Enter choice: ");  
        }
                choice = sc.nextInt();
                sc.nextLine();
                
            switch (choice) {
                case 1:
                    recordAttendance(sc);
                    break;
                case 2:
                    viewAttendance(sc);
                    break;
                case 3:
                    updateAttendance(sc);
                    break;
                case 4:
                    deleteAttendance(sc);
                    break;
                case 5:
                    System.out.println("Back to Main Menu.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    private static void recordAttendance(Scanner sc) {
        System.out.print("Enter the number of members to record attendance for: ");
        int numberOfMembers = sc.nextInt();
        sc.nextLine(); 

        config conf = new config();

        for (int i = 0; i < numberOfMembers; i++) {
            System.out.println("Recording attendance for member " + (i + 1) + ":");

            members ms = new members();
            ms.viewMembers();

            System.out.print("Enter Member ID: ");
            int mem = sc.nextInt();
            sc.nextLine(); 

            activities ac = new activities();
            ac.viewAllActivities();

            System.out.print("Enter Activity ID: ");
            int act = sc.nextInt();
            sc.nextLine(); 

            System.out.print("Enter Attendance Status: ");
            String status = sc.nextLine();

            String sql = "INSERT INTO tbl_attendance (s_hid, s_id, s_status) VALUES (?, ?, ?)";
            conf.addRecord(sql, mem, act, status);

            System.out.println("Attendance recorded successfully for member " + (i + 1) + ".");
        }
    }

    private static void viewAttendance(Scanner sc) {
        System.out.println("|---------------------------------------------|");
        System.out.println("1. View All Attendance");
        System.out.println("2. View Attendance by ID");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); 
        if (choice == 1) {
            viewAllAttendance();
        } else if (choice == 2) {
            System.out.print("Enter Attendance ID: ");
            int id = sc.nextInt();
            sc.nextLine();  
            viewAttendanceById(id);
        } else {
            System.out.println("Invalid choice. Returning to menu.");
        }
    }

    private static void viewAllAttendance() {
        String sqlQuery = "SELECT "
                         + "tbl_members.s_fname AS 'First Name', "
                         + "tbl_members.s_lastname AS 'Last Name', "
                         + "tbl_activities.s_name AS 'Activity Name', "
                         + "tbl_activities.s_loc AS 'Location', "
                         + "tbl_activities.s_date AS 'Date', "
                         + "tbl_attendance.s_status AS 'Status' "
                         + "FROM tbl_attendance "
                         + "JOIN tbl_members ON tbl_attendance.s_hid = tbl_members.s_hid "
                         + "JOIN tbl_activities ON tbl_attendance.s_id = tbl_activities.s_id";

        String[] attendanceHeaders = {"First Name", "Last Name", "Activity Name", "Location", "Date", "Status"};
        String[] attendanceColumns = {"First Name", "Last Name", "Activity Name", "Location", "Date", "Status"};

        config conf = new config();
        conf.viewRecords(sqlQuery, attendanceHeaders, attendanceColumns);
    }

    private static void viewAttendanceById(int id) {
        String sqlQuery = "SELECT tbl_attendance.s_hid AS 'Member ID', "
                        + "tbl_members.s_fname AS 'First Name', "
                        + "tbl_members.s_lastname AS 'Last Name', "
                        + "tbl_activities.s_name AS 'Activity Name', "
                        + "tbl_activities.s_loc AS 'Location', "
                        + "tbl_activities.s_date AS 'Date', "
                        + "tbl_attendance.s_status AS 'Status' "
                        + "FROM tbl_attendance "
                        + "JOIN tbl_members ON tbl_attendance.s_hid = tbl_members.s_hid "
                        + "JOIN tbl_activities ON tbl_attendance.s_id = tbl_activities.s_id "
                        + "WHERE tbl_attendance.s_id = ?";

        String[] attendanceHeaders = {"Member ID", "First Name", "Last Name", "Activity Name", "Location", "Date", "Status"};
        String[] attendanceColumns = {"Member ID", "First Name", "Last Name", "Activity Name", "Location", "Date", "Status"};

        config conf = new config();
        conf.viewRecordsById(sqlQuery, attendanceHeaders, attendanceColumns, id);
    }

    private static void updateAttendance(Scanner sc) {
        activities ac = new activities();
        ac.viewAllActivities();  

        System.out.print("Enter the Attendance ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new Activity Name: ");
        String activityName = sc.nextLine();
        System.out.print("Enter new Date: ");
        String date = sc.nextLine();
        System.out.print("Enter new Location: ");
        String location = sc.nextLine();

        String qry = "UPDATE tbl_activities "
                   + "SET s_name = ?, s_loc= ? "
                   + "WHERE s_id = ?";
        config conf = new config();
        conf.updateRecord(qry, activityName, location, id);

        System.out.println("Attendance updated successfully.");
    }

    private static void deleteAttendance(Scanner sc) {
        System.out.print("Enter the Attendance ID to Delete: ");
        int id = sc.nextInt();

        String qry = "DELETE FROM tbl_attendance WHERE s_id = ?";
        config conf = new config();
        conf.deleteRecord(qry, id);

        System.out.println("Attendance record deleted successfully.");
    }
}