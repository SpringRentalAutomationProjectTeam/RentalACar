package com.etiya.RentACar.business.constants;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.entites.LanguageWord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {

    //bende
    public static final String BRANDADD= "brand_add";//1--Marka eklendi
    public static final String BRANDUPDATE = "brand_update";//2-- Marka güncellendi
    public static final String BRANDDELETE = "brand_delete";//3 -- Marka silindi
    public static final String BRANDLIST = "brand_list";//4 -- Markalar listelendi
    public static final String BRANDNAMEERROR = "brand_name_error";//5
    public static final String BRANDFOUND = "brand_found";//6
    public static final String BRANDNOTFOUND = "brand_not_found";//7
    public static final String BRANDDELETEERROR = "brand_delete_error";//8

    public static final String CARADD = "car_add";//9
    public static final String CARUPDATE = "car_update";//10
    public static final String CARDELETE = "car_delete";//11
    public static final String CARLIST = "car_list";//12
    public static final String CARFOUND = "car_found";//13
    public static final String CARNOTFOUND = "car_not_found";//14
    public static final String CARBRANDANDCOLORLIST = "car_brand_and_color_list";//15
    public static final String CARGETBRAND = "car_get_brand";//16
    public static final String CARGETCOLOR = "car_get_color";//17
    public static final String CARGETCITY = "car_get_city";//18
    public static final String CARKMUPDATE = "car_km_update";//19
    public static final String CARISONRENT = "car_is_on_rent";//20
    public static final String CARAVAILABLE = "car_available";//21

    public static final String CITYADD = "city_add";//22 --Şehir eklendi
    public static final String CITYUPDATE = "city_update";//23 // --Şehir güncellendi.

    public static final String CITYDELETE = "city_delete";//24
    public static final String CITYLIST = "city_list";//25
    public static final String CITYNOTFOUND = "city_not_found";//26
    public static final String CITYFOUND = "city_found"; //27--yazıldı
    public static final String CITYALREADYEXISTS = "city_already_exists";//28

    public static final String COLORADD = "color_add";//29
    public static final String COLORUPDATE = "color_update";//30
    public static final String COLORDELETE = "color_delete";//31
    public static final String COLORLIST = "color_list";//32
    public static final String COLORNAMEERROR = "color_name_error";//33
    public static final String COLORFOUND = "color_found";//34
    public static final String COLORNOTFOUND = "color_not_found";//35
    public static final String COLORERROR = "color_error";//36
    public static final String COLORDELETEERROR = "color_delete_error";//37

//berkay

    public static final String CUSTOMERADD = "customer_add";//38
    public static final String CUSTOMERUPDATE = "customer_update";//39
    public static final String CUSTOMERDELETE = "customer_delete";//40
    public static final String CUSTOMERLIST = "customer_list";//41
    public static final String CUSTOMERFOUND = "customer_found";//42
    public static final String CUSTOMERNOTFOUND = "customer_not_found";//43
    public static final String CUSTOMERISALREADYEXISTS = "customer_is_already_exists";//44
    public static final String CUSTOMERTAXNUMBEREXISTS = "customer_taxnumber_exists";//45


    public static final String EMAILERROR = "email_error";//46
    public static final String EMAILFORMATERROR = "email_format_error";//47

    public static final String RENTALADD = "rental_add";//48
    public static final String RENTALUPDATE = "rental_update";//49
    public static final String RENTALDELETE = "rental_delete";//50
    public static final String RENTALLIST = "rental_list";//51
    public static final String RENTALDATEERROR = "rental_date_error";//52
    public static final String RENTALDATESUCCESS = "rental_date_success";//53
    public static final String RENTALFINDEXSCOREERROR = "rental_findex_score_error";//54
    public static final String RENTALFINDEXSCORE = "rental_findex_score";//55
    public static final String RENTALMAINTENANCEERROR = "rental_maintenance_error";//56
    public static final String RENTALMAINTENANCE = "rental_maintenance";//57
    public static final String RENTALFOUND = "rental_found";//(RENTALFOUD)//58
    public static final String RENTALNOTFOUND = "rental_not_found";//59
    public static final String INSUFFICIENTBALANCE = "rental_insufficient_balance";//60
    public static final String SUFFICIENTBALANCE = "rental_sufficient_balance";//61

    public static final String CARIMAGEADD = "carimage_add";//62
    public static final String CARIMAGEUPDATE = "carimage_update";//63
    public static final String CARIMAGEDELETE = "carimage_delete";//64
    public static final String CARIMAGELIST = "carimage_list";//65
    public static final String CARIMAGELIMITERROR = "carimage_limit_error";//66
    public static final String CARIMAGEDEFAULT = "carimage_default";//67
    public static final String CARIMAGEEMPTY = "carimage_empty";//68
    public static final String CARIMAGETYPEERROR = "carimage_type_error";//69
    public static final String CARIMAGEFOUND = "carimage_found";//70
    public static final String CARIMAGELIMIT = "carimage_limit";//71
    public static final String CARIMAGENOTFOUND = "carimage_not_found";//72
    //berkan
    public static final String LOGINEMAILERROR = "login_email_error";//73
    public static final String LOGINPASSWORDERROR = "login_password_error";//74
    public static final String LOGINSUCCESS = "login_success";//75


    public static final String CREDITCARDADD = "creditcard_add";//76
    public static final String CREDITCARDUPDATE = "creditcard_update";//77
    public static final String CREDITCARDDELETE = "creditcard_delete";//78
    public static final String CREDITCARDLIST = "creditcard_list";//79
    public static final String CREDITCARDNUMBERERROR = "creditcard_number_error";//80
    public static final String CREDITCARDDATEERROR = "creditcard_date_error";//81
    public static final String CREDITCARDCVCERROR = "creditcard_cvv_error";//82
    public static final String CREDITCARDSAVE = "creditcard_save";//83
    public static final String CREDITCARDNOTSAVE = "creditcard_not_save";//84
    public static final String CREDITCARDFOUND = "creditcard_found";//85
    public static final String CREDITCARDDELETEERROR ="creditcard_delete_error";

    public static final String CARMAINTENANCEADD = "carmaintenance_add";//86
    public static final String CARMAINTENANCEUPDATE = "carmaintenance_update";//87
    public static final String CARMAINTENANCEDELETE = "carmaintenance_delete";//88
    public static final String CARMAINTENANCELIST = "carmaintenance_list";//89
    public static final String CARMAINTENANCERENTALERROR = "carmaintenance_rental_error";//90
    public static final String CARMAINTENANCEERROR = "carmaintenance_error";//91
    public static final String CARMAINTENANCEFOUND = "carmaintenance_found";//92
    public static final String CARMAINTENANCENOTFOUND = "carmaintenance_not_found";//93

    public static final String INVOICEADD = "invoice_add";//94
    public static final String INVOICEUSERERROR = "invoice_user_error";//95
    public static final String INVOICEUPDATE = "invoice_update";//96
    public static final String INVOICEDELETE = "invoice_delete";//97
    public static final String INVOICENOTFOUND = "invoice_not_found";//98
    public static final String INVOICELIST = "invoice_list";//99
    public static final String INVOICEBYCUSTOMERLIST = "invoice_by_customer_list";//100
    public static final String INVOICEFOUND = "invoice_found";//101
    public static final String INVOICENOTADD = "invoice_not_add";//102


    public static final String USERNOTFOUND = "user_not_found";//103
    public static final String USERFOUND = "user_found";//104
    public static final String USERLIST = "user_list";//105
    //caner
    public static final String DAMAGEADD = "damage_add";//106
    public static final String DAMAGEDELETE = "damage_delete";//107
    public static final String DAMAGEUPDATE = "damage_update";//108
    public static final String DAMAGELIST = "damage_list";//109
    public static final String DAMAGENOTFOUND = "damage_not_found";//110
    public static final String DAMAGEFOUND = "damage_found";//111
    public static final String DAMAGEBELONGTOCAR = "damage_belong_to_car";//112

    public static final String ADDITIONALSERVICEADD = "additionalservice_add";//113
    public static final String ADDITIONALSERVICEUPDATE = "additionalservice_update";//114
    public static final String ADDITIONALSERVICEDELETE = "additionalservice_delete";//115
    public static final String ADDITIONALSERVICELIST = "additionalservice_list";//116
    public static final String ADDITIONALSERVICENOTFOUND = "additionalservice_not_found";//117
    public static final String ADDITIONALSERVICEFOUND = "additionalservice_found";//118

    public static final String ADDITIONALRENTALITEMADD = "additional_rental_item_add";//119
    public static final String ADDITIONALRENTALITEMDELETE = "additional_rental_item_delete";//120
    public static final String ADDITIONALRENTALITEMUPDATE = "additional_rental_item_update";//121
    public static final String ADDITIONALRENTALITEMLIST = "additional_rental_item_list";//122
    public static final String ADDITIONALRENTALITEMNOTFOUND = "additional_rental_item_not_found";//123

    public static final String RECORDNOTFOUND = "record_not_found";//124
    public static final String LOCALDATEERROR = "local_date_error";//125
    public static final String VALIDATIONERROR = "validation_error";//126

    public static final String LANGUAGEADD = "language_add";//127
    public static final String LANGUAGEUPDATE = "language_update";//128
    public static final String LANGUAGEDELETE = "language_delete";//129
    public static final String LANGUAGELIST = "language_list";//130
    public static final String LANGUAGENAMEERROR = "language_name_error";//131
    public static final String LANGUAGENOTFOUND = "language_not_found";//132
    public static final String MESSAGEKEYADD = "messagekey_add";//133
    public static final String MESSAGEKEYUPDATE = "messagekey_update";//134
    public static final String MESSAGEKEYDELETE = "messagekey_delete";//135
    public static final String MESSAGEKEYLIST = "messagekey_list";//136
    public static final String MESSAGEKEYNAMEERROR = "messagekey_name_error";//137
    public static final String MESSAGEKEYNOTFOUND = "messagekey_not_found";//138
    public static final String LANGUAGEWORDADD = "languageword_add";//139
    public static final String LANGUAGEWORDUPDATE = "languageword_update";//140
    public static final String LANGUAGEWORDDELETE = "languageword_delete";//141
    public static final String LANGUAGEWORDLIST = "languageword_list";//142
    public static final String LANGUAGEWORDNAMEERROR = "languageword_name_error";//143
    public static final String LANGUAGEWORDNOTFOUND = "languageword_not_found";//144

    public static final String TESTKEY = "test_key";//147
    public static final String DEFAULTKEY = "default_key";//127
}
