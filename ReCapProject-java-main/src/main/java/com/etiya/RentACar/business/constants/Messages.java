package com.etiya.RentACar.business.constants;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.entites.LanguageWord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {

    public static Map<String,String> keyAndValue=new HashMap<>();

    public static final String BRANDADD= "Brand added";
    public static final String BRANDUPDATE = "Brand updated.";
    public static final String BRANDDELETE = "Brand deleted.";//3
    public static final String BRANDLIST = "Brands listed.";//4
    public static final String BRANDNAMEERROR = "This brand exists.";//5
    public static final String BRANDGET = "Brand found";//6
    public static final String BRANDNOTFOUND = "BRAND NOT FOUND.";//7
    public static final String BRANDDELETEERROR = "Car of this brand found";//8
//key bitti value bitti
    //key bitti value bitti
    public static final String CARADD = "Car added.";//9
    public static final String CARUPDATE = "Car updated.";//10
    public static final String CARDELETE = "Car deleted.";//11
    public static final String CARLIST = "Cars listed.";//12
    public static final String CARFOUND = "CAR FOUND.";//13
    public static final String CARNOTFOUND = "CAR NOT FOUND.";//14
    public static final String CARBRANDANDCOLORLIST = "Car Detail Listed by Brandname And Colorname.";//15
    public static final String CARGETBRAND = "Car get by Brand Id.";//16
    public static final String CARGETCOLOR = "Car get by Color Id.";//17
    public static final String CARGETCITY = "Car get by City Id.";//18
    public static final String CARKMUPDATE = "Car Km updated.";//19
    public static final String CARISONRENT = "Car Do Not Return From Rental.";//20
    public static final String CARAVAILABLE = "Car Available";//21

    //key
    public static final String CITYADD = "City Added";//22
    public static final String CITYUPDATE = "City Updated";//23
    public static final String CITYDELETE = "City Deleted";//24
    public static final String CITYLIST = "Cities Listed";//25
    public static final String CITYNOTFOUND = "CITY NOT FOUND.";//26
    public static final String CITYFOUND = "City Found";    //27--yazıldı
    public static final String CITYALREADYEXISTS = "City already exists.";//28


    public static final String COLORADD = "Color added.";//29---
    public static final String COLORUPDATE = "Color updated.";
    public static final String COLORDELETE = "Color deleted.";
    public static final String COLORLIST = "Colors listed.";
    public static final String COLORNAMEERROR = "This color exists.";
    public static final String ColorFound = "Color found";
    public static final String COLORNOTFOUND = "COLOR NOT FOUND.";
    public static final String COLORERROR = "This color is available.";
    public static final String COLORDELETEERROR = "Car of this color found ";


    public static final String CUSTOMERADD = "Customer added.";
    public static final String CUSTOMERUPDATE = "Customer updated.";
    public static final String CUSTOMERDELETE = "Customer deleted.";
    public static final String CUSTOMERLIST = "Customers listed.";
    public static final String CUSTOMERGET = "Customer found";
    public static final String CUSTOMERNOTFOUND = "Customer not found.";
    public static final String CUSTOMERISALREADYEXISTS = "Customer already exist !";
    public static final String CUSTOMERTAXNUMBEREXISTS = "Customer tax number already exist !";


    public static final String EMAILERROR = "This email is already in use.";
    public static final String EMAILFORMATERROR = "This email format is wrong";

    public static final String RENTALADD = "Rental added.";
    public static final String RENTALUPDATE = "Rental updated.";
    public static final String RENTALDELETE = "Rental deleted.";
    public static final String RENTALLIST = "Rentals listed.";
    public static final String RENTALDATEERROR = "Rental rent date must be before return date.";
    public static final String RENTALDATESUCCESS = "Vehicle can be rented.";
    public static final String RENTALFINDEXSCOREERROR = "Your findex score is not enough to rent this car..";
    public static final String RENTALFINDEXSCORE = "Your findex score is enough to rent this car.";
    public static final String RENTALMAINTENANCEERROR = "The car is currently under maintenance.";
    public static final String RENTALMAINTENANCE = "The car is not currently under maintenance.";
    public static final String RENTALGET = "Rental found";//(RENTALFOUD)
    public static final String RENTALNOTFOUND = "Rental not found";
    public static final String INSUFFICIENTBALANCE = "The balance is insufficient.";
    public static final String SUFFICIENTBALANCE = "The balance is sufficient.";

    public static final String CARIMAGEADD = "Car image added.";
    public static final String CARIMAGEUPDATE = "Car image updated.";
    public static final String CARIMAGEDELETE = "Car image deleted.";
    public static final String CARIMAGELIST = "Car images listed.";
    public static final String CARIMAGELIMITERROR = "A car can not have more than 5 pictures.";
    public static final String CARIMAGEDEFAULT = "Showing default image";
    public static final String CARIMAGEEMPTY = "No image selected.";
    public static final String CARIMAGETYPEERROR = "The file you selected is not an image file.";
    public static final String CARIMAGEGET = "Car Image found";
    public static final String CARIMAGELIMIT = "Image can be added to the car.";
    public static final String CARIMAGENOTFOUND = "CAR IMAGE NOT FOUND.";

    public static final String LOGINEMAILERROR = "This email is not registered";
    public static final String LOGINPASSWORDERROR = "Wrong password.";
    public static final String LOGINSUCCESS = "Login successful.";

    public static final String CREDITCARDADD = "Credit card added.";
    public static final String CREDITCARDUPDATE = "Credit card updated.";
    public static final String CREDITCARDELETE = "Credit card deleted.";
    public static final String CREDITCARDLIST = "Credit cards listed.";
    public static final String CREDITCARDNUMBERERROR = "Credit card number is invalid.";
    public static final String CREDITCARDDATEERROR = "Credit card date is invalid.";
    public static final String CREDITCARDCVCERROR = "Credit card CVC is invalid.";
    public static final String CREDITCARDSAVE = "Credit card registered.";
    public static final String CREDITCARDNOTSAVE = "Credit card not registered.";
    public static final String CREDITCARDGET = "Credit card found";
//kaldık 46//bende
    public static final String CARMAINTENANCEADD = "Car maintenance added.";
    public static final String CARMAINTENANCEUPDATE = "Car maintenance updated.";
    public static final String CARMAINTENANCEDELETE = "Car maintenance deleted.";
    public static final String CARMAINTENANCELIST = "Cars in maintenance listed.";
    public static final String CARMAINTENANCERENTALERROR = "The car is currently rented.";
    public static final String CARMAINTENANCEERROR = "The car is currently maintenance.";
    public static final String CARMAINTENANCEFOUND = "Maintenance found";
    public static final String CARMAINTENANCENOTFOUND = "CAR MAINTENANCE NOT FOUND.";//93
//bende
    public static final String INVOICEADD = "Invoice added.";
    public static final String INVOICEUSERERROR = "The customer does not have an invoice.";
    public static final String INVOICEUPDATE = "Invoice updated.";
    public static final String INVOICEDELETE = "Invoice deleted.";
    public static final String INVOICENOTFOUND = "Invoice not found";
    public static final String INVOICELIST = "Invoice listed.";
    public static final String INVOICEBYCUSTOMERLIST = "Invoice listed.";
    public static final String INVOICEGET = "Invoice found";
    public static final String INVOICENOTADD = "Already make out an invoice.";//102

    //bnerkan
    public static final String USERNOTFOUND = "USER NOT FOUND.";
    public static final String USERFOUND = "USER FOUND";
    public static final String USERLIST = "Users listed.";//105
//berkan
    public static final String DAMAGEADD = "Damage added.";//106
    public static final String DAMAGEDELETE = "Damage deleted.";//107
    public static final String DAMAGEUPDATE = "Damage updated";//108
    public static final String DAMAGELIST = "Damages are listed";//109
    public static final String DAMAGENOTFOUND = "Damage not found";//110
    public static final String DAMAGEFOUND = "Damage found";//111
    public static final String DAMAGEBELONGTOCAR = "There is not damage belong to car";//112
//berkan
    public static final String ADDITIONALSERVICEADD = "Additional service added.";//113
    public static final String ADDITIONALSERVICEUPDATE = "Additional service updated.";//114
    public static final String ADDITIONALSERVICEDELETE = "Additional service deleted.";//115
    public static final String ADDITIONALSERVICELIST = "Additional services listed.";//116
    public static final String ADDITIONALSERVICENOTFOUND = "Additional service not found";//117
    public static final String ADDITIONALSERVICEFOUND = "Additional service found";//118
//erdi
    public static final String ADDITIONALRENTALITEMADD = "Additional rental item added.";//119
    public static final String ADDITIONALRENTALITEMDELETE = "Additional rental item deleted.";//120
    public static final String ADDITIONALRENTALITEMUPDATE = "Additional rental item updated.";//121
    public static final String ADDITIONALRENTALITEMLIST = "Additional rental items are listed.";//122
    public static final String ADDITIONALRENTALITEMNOTFOUND = "Additional rental item not found";//123
//erdi
    public static final String RECORDNOTFOUND = "Record is not found";//124
    public static final String LOCALDATEERROR = "date must be in format yyyy-mm-dd";//125
    public static final String VALIDATIONERROR = "Validation error";//126

}
