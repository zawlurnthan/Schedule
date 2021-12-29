
Title : Scheduling App
Purpose: User able to manage and schedule appointments 
         with the contacts from eastern time and 
         customers from all over the world.

Author: Zaw Than 
        zthan@wgu.edu

version: sma 101, Nov 2020

Tools: Apache NetBeans 12.0, Scene Builder 11.0.0
Platform: JavaFx 15, Java SE 15.0.1 Jdk , MySQL 8.0.22

Customer report is added to application as my choice to fill the gap of requirement.


How to run the Program:
        Once successfully login to application, Main page will be found 
        with three options to choose: Customer; Appointment and Report.

Customer Option:
        After choosing Customer by pressing Customer button, All available Customers Table View 
        will be found which has four options to choose: Add; Modify; Delete and Home button. 

        Add Button: Open Add Customer page with form to add new customer to the customer list. It 
        has a function to prevent invalid inputs and once country is chosen, divisions are available 
        in combo-box associated with each country. It also has two buttons to choose function: 
        save and back. Save button saves new customer in database. Back button just goes back to 
        Customer Main page. 

        Modify Button: Open Modify Customer page with pre-populated all information of selected customer in 
        each input. It has same functionality and buttons as Add Customer page.

        Delete Button: Give warning to ensure deleting selected customer. Delete upon choosing 
        OK button in the warning alert. If customer has associated appointments, selected customer 
        won't be deleted and give another warning alert with instruction of how to delete.

        Home Button: Go to Main Page which is starting point of the application after login. 

Appointment Option:
        After choosing Appointment by pressing Appointment button, All available Appointments Table View 
        will be found which page has four options to choose: Add; Modify; Delete and Home button. It also has 
        options to view weekly and monthly list of appointments by selecting radio-buttons and picking a 
        date from the date-picker. Date-picker must pick a date to get desired week or month.

        Add Button: Open Add Appointment page with form to add new appointment to the appointment list. It 
        has some functions to prevent scheduling in past, weekend and invalid input. It has two buttons to 
        choose function: save and back. Save button saves new appointment in database. Back button just goes 
        back to Appointment Main page. 

        Modify Button: Open Modify Appointment page with pre-populated all information of selected appointment in 
        each input. It has same functionality and buttons as Add Appointment page.

        Delete Button: Give warning to ensure deleting selected appointment. Delete upon choosing 
        OK button in the warning alert.

        Home Button: Go to Main Page which is starting point of the application after login. 

Report Option:
        After choosing Report by pressing Report button, Report page will be found which has three options to 
        view each reports. 
        
        All appointment list of a contact can be seen by choosing desired customer from 
        Contacts combo-box.
        
        All appointment list of a customer can be seen by choosing a customer from Customers 
        combo-box. 
        
        Appointments can be seen by type and month just by choosing type and month
        form each combo-box.

        It also has Home button to go back Main Page which is starting point of the application after login.


