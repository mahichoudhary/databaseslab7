# databaseslab7

Mahi Choudhary, Garrett Lew, Kaitlin Clever

### How to run

1. CD to the csc365-jdbc directory
2. Run the command `./gradlew run -q --console=plain`  
If command 2 doesn't work, may need to give permission to the gradlew file using chmod

### Interface

Inn reservation system uses a text based interface.
Menu options will be displayed with a given number for each option.
Input the number that corresponds with the option you want to continue.

### Bugs or Deficiencies

The next available checkin date displayed in rooms in rates, will be displayed as the end date of the current stay or as "Today". It does not check for stays after the current stay if one exists.
