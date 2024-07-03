package com.production.qtickets.utils;

import com.production.qtickets.R;

/**
 * Created by hemanth on 4/9/2018.
 */

public class AppConstants {

    public static final String Authorization_QT5 = "c3fcd3d76192e4007dfb496cca67e13b";
    public static final String AppSource = "3";
    public static final String SERVER_BASE_URL = "https://api.q-tickets.com/V5.0/";
    public static final String COUNTRY_LIST_API = "https://api.q-tickets.com/V3.0/getqticketscountries";
    public static final String CMS_PAGE_URL = "http://api.q-tickets.com/V2.0/";

    public static final String SERVICE_URL_GET_USER_LOGIN = SERVER_BASE_URL + "loginmobile?";
    public static final String SERVICE_URL_GET_USER_REGISTRATION = SERVER_BASE_URL + "Registration?";
    public static final String SERVICE_URL_GET_HOMEPAGELOGOS = SERVER_BASE_URL + "gethomepagelogos";
    public static final String SERVICE_URL_GET_HOMEMOVIELIST = SERVER_BASE_URL + "getmoviesjson";
    public static final String SERVICE_URL_GET_HOMEEVENTLIST = SERVER_BASE_URL + "getalleventsdetailsbycountryjson?Country=";
    public static final String CMS_PAGES_URL = CMS_PAGE_URL + "getcmsjson";
    public static final String SERVICE_URL_MY_PROFILE = SERVER_BASE_URL + "profileupdationjson?";
    //Email pattern
    public static final String REGEX_EMAIL_ADDRESS = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,64}$";
    //com.production.qtickets.login
    public static final Integer LOGIN = 1000;
    public static final String url = "http://buytickets.me/Flikcinema/Movies";

    public static final String SHARED_EVENTS_TICKET_PRICES = "SHARED_EVENTS_TICKET_PRICES";
    public static final String COUNTRY_TYPE_CURRENCY = "COUNTRY_TYPE";
    public static final String TICKET_FIELDS = "TICKET_FIELDS";

    public static final String TOTAL_TICKET_COUNT = "TOTAL_TICKET_COUNT";
    public static final String TOTAL_TICKET_COST = "TOTAL_TICKET_COST";
    public static final String TICKET_PRICE_ID_COUNT = "TICKET_PRICE_ID_COUNT";

    public static final String EVENT_ID = "EVENT_ID";
    public static final String BOAT_TYPE = "BOAT_TYPE";


    public static final String IS_PROMO_COUPON = "is_promo_code";
    public static final String IS_COUPON_CODE = "is_cupon_code";



    public static final String TOTAL_COUNT = "TOTAL_COUNT";
    public static final String TOTAL_PRICE = "TOTAL_PRICE";
    public static final String TOTAL_SERVICEPRICE_EVENT = "TOTAL_SERVICEPRICE_EVENT";
    public static final String EVENET_SELECTED_DATE = "EVENET_SELECTED_DATE";

    //novocinema username and password
    public static String user_name = "rajanpathak";
    public static String password = "QkhwnKLs";
    public static final String novo_country_id = "2";

    public static  String PDF_FILES_ENABLE="1";
    public static  String PNG_FILES_ENABLE="2";
    public static  String JPG_FILES_ENABLE="3";
    public static  String XLS_FILES_ENABLE="4";
    public static  String DOC_FILES_ENABLE="5";
    public static  String TXT_FILES_ENABLE="6";



    public static String[] country_code = {"+974", "+91", "+973", "+971", "+968", "+93",
            "+355",
            "+213",
            "+376",
            "+244",
            "+672",
            "+54",
            "+374",
            "+297",
            "+61",
            "+43",
            "+994",
            "+880",
            "+375",
            "+32",
            "+501",
            "+229",
            "+975",
            "+591",
            "+387",
            "+267",
            "+55",
            "+673",
            "+359",
            "+226",
            "+95",
            "+257",
            "+855",
            "+237",
            "+1",
            "+238",
            "+236",
            "+235",
            "+56",
            "+86",
            "+61",
            "+57",
            "+269",
            "+242",
            "+243",
            "+682",
            "+506",
            "+385",
            "+53",
            "+357",
            "+420",
            "+45",
            "+253",
            "+670",
            "+593",
            "+20",
            "+503",
            "+240",
            "+291",
            "+372",
            "+251",
            "+500",
            "+298",
            "+679",
            "+358",
            "+33",
            "+689",
            "+241",
            "+220",
            "+995",
            "+49",
            "+233",
            "+350",
            "+30",
            "+299",
            "+502",
            "+224",
            "+245",
            "+592",
            "+509",
            "+504",
            "+852",
            "+36",
            "+62",
            "+98",
            "+964",
            "+353",
            "+44",
            "+972",
            "+39",
            "+225",
            "+81",
            "+962",
            "+7",
            "+254",
            "+686",
            "+965",
            "+996",
            "+856",
            "+371",
            "+961",
            "+266",
            "+231",
            "+218",
            "+423",
            "+370",
            "+352",
            "+853",
            "+389",
            "+261",
            "+265",
            "+60",
            "+960",
            "+223",
            "+356",
            "+692",
            "+222",
            "+230",
            "+262",
            "+52",
            "+691",
            "+373",
            "+377",
            "+976",
            "+382",
            "+212",
            "+258",
            "+264",
            "+674",
            "+977",
            "+31",
            "+687",
            "+64",
            "+505",
            "+227",
            "+234",
            "+683",
            "+850",
            "+47",
            "+92",
            "+680",
            "+507",
            "+675",
            "+595",
            "+51",
            "+63",
            "+870",
            "+48",
            "+351",
            "+1",
            "+40",
            "+7",
            "+250",
            "+590",
            "+685",
            "+378",
            "+239",
            "+966",
            "+221",
            "+381"};
    public static int flags[] = {R.drawable.flag_qatar, R.drawable.flag_india, R.drawable.flag_bahrain, R.drawable.flag_uae, R.drawable.flag_oman, R.drawable.flag_afghanistan, R.drawable.flag_albania, R.drawable.flag_algeria, R.drawable.flag_andorra, R.drawable.flag_angola, R.drawable.flag_antarctica, R.drawable.flag_argentina, R.drawable.flag_armenia, R.drawable.flag_aruba, R.drawable.flag_australia, R.drawable.flag_austria, R.drawable.flag_azerbaijan, R.drawable.flag_bangladesh, R.drawable.flag_belarus, R.drawable.flag_belgium, R.drawable.flag_belize, R.drawable.flag_benin, R.drawable.flag_bhutan, R.drawable.flag_bolivia, R.drawable.flag_bosnia, R.drawable.flag_botswana,
            R.drawable.flag_brazil, R.drawable.flag_brunei, R.drawable.flag_bulgaria, R.drawable.flag_burkina_faso, R.drawable.flag_myanmar, R.drawable.flag_burundi, R.drawable.flag_cambodia, R.drawable.flag_canada, R.drawable.flag_cape_verde,
            R.drawable.flag_central_african_republic, R.drawable.flag_chad, R.drawable.flag_chile, R.drawable.flag_china, R.drawable.flag_christmas_island, R.drawable.flag_cocos, R.drawable.flag_colombia, R.drawable.flag_comoros, R.drawable.flag_republic_of_the_congo, R.drawable.flag_democratic_republic_of_the_congo, R.drawable.flag_cook_islands, R.drawable.flag_costa_rica, R.drawable.flag_croatia, R.drawable.flag_cuba, R.drawable.flag_cyprus, R.drawable.flag_czech_republic, R.drawable.flag_denmark, R.drawable.flag_djibouti, R.drawable.flag_timor_leste, R.drawable.flag_ecuador, R.drawable.flag_egypt, R.drawable.flag_el_salvador,
            R.drawable.flag_equatorial_guinea,
            R.drawable.flag_eritrea,
            R.drawable.flag_estonia,
            R.drawable.flag_ethiopia,
            R.drawable.flag_falkland_islands,
            R.drawable.flag_faroe_islands,
            R.drawable.flag_fiji,
            R.drawable.flag_finland,
            R.drawable.flag_france,
            R.drawable.flag_french_polynesia,
            R.drawable.flag_gabon,
            R.drawable.flag_gambia,
            R.drawable.flag_georgia,
            R.drawable.flag_germany,
            R.drawable.flag_ghana,
            R.drawable.flag_gibraltar,
            R.drawable.flag_greece,
            R.drawable.flag_greenland,
            R.drawable.flag_guatemala,
            R.drawable.flag_guinea,
            R.drawable.flag_guinea_bissau,
            R.drawable.flag_guyana,
            R.drawable.flag_haiti,
            R.drawable.flag_honduras,
            R.drawable.flag_hong_kong,
            R.drawable.flag_hungary,
            R.drawable.flag_indonesia,
            R.drawable.flag_iran,
            R.drawable.flag_iraq,
            R.drawable.flag_ireland,
            R.drawable.flag_isleof_man,
            R.drawable.flag_israel,
            R.drawable.flag_italy,
            R.drawable.flag_cote_divoire,
            R.drawable.flag_japan,
            R.drawable.flag_jordan,
            R.drawable.flag_kazakhstan,
            R.drawable.flag_kenya,
            R.drawable.flag_kiribati,
            R.drawable.flag_kuwait,
            R.drawable.flag_kyrgyzstan,
            R.drawable.flag_laos,
            R.drawable.flag_latvia,
            R.drawable.flag_lebanon,
            R.drawable.flag_lesotho,
            R.drawable.flag_liberia,
            R.drawable.flag_libya,
            R.drawable.flag_liechtenstein,
            R.drawable.flag_lithuania,
            R.drawable.flag_luxembourg,
            R.drawable.flag_macao,
            R.drawable.flag_macedonia,
            R.drawable.flag_madagascar,
            R.drawable.flag_malawi,
            R.drawable.flag_malaysia,
            R.drawable.flag_maldives,
            R.drawable.flag_mali,
            R.drawable.flag_malta,
            R.drawable.flag_marshall_islands,
            R.drawable.flag_mauritania,
            R.drawable.flag_mauritius,
            R.drawable.flag_martinique,
            R.drawable.flag_mexico,
            R.drawable.flag_micronesia,
            R.drawable.flag_moldova,
            R.drawable.flag_monaco,
            R.drawable.flag_mongolia,
            R.drawable.flag_of_montenegro,
            R.drawable.flag_morocco,
            R.drawable.flag_mozambique,
            R.drawable.flag_namibia,
            R.drawable.flag_nauru,
            R.drawable.flag_nepal,
            R.drawable.flag_netherlands,
            R.drawable.flag_new_caledonia,
            R.drawable.flag_new_zealand,
            R.drawable.flag_nicaragua,
            R.drawable.flag_niger,
            R.drawable.flag_nigeria,
            R.drawable.flag_niue,
            R.drawable.flag_north_korea,
            R.drawable.flag_norway,
            R.drawable.flag_pakistan,
            R.drawable.flag_palau,
            R.drawable.flag_panama,
            R.drawable.flag_papua_new_guinea,
            R.drawable.flag_paraguay,
            R.drawable.flag_peru,
            R.drawable.flag_philippines,
            R.drawable.flag_pitcairn_islands,
            R.drawable.flag_poland,
            R.drawable.flag_portugal,
            R.drawable.flag_puerto_rico,
            R.drawable.flag_romania,
            R.drawable.flag_russian_federation,
            R.drawable.flag_rwanda,
            R.drawable.flag_saint_barthelemy,
            R.drawable.flag_samoa,
            R.drawable.flag_san_marino,
            R.drawable.flag_sao_tome_and_principe,
            R.drawable.flag_saudi_arabia,
            R.drawable.flag_senegal,
            R.drawable.flag_serbia};
    public static String[] nationality = new String[]{"Select Natoinality", "Afghan", "Albanian", "Algerian", "American", "Andorran", "Angolan", "Antiguans", "Argentinean", "Armenian",
            "Australian", "Austrian", "Azerbaijani", "Bahamian", "Bahraini", "Bangladeshi", "Barbadian", "Barbudans",
            "Batswana", "Belarusian", "Belgian", "Belizean", "Beninese", "Bhutanese", "Bolivian", "Bosnian", "Brazilian",
            "British", "Bruneian", "Bulgarian", "Burkinabe", "Burmese", "Burundian", "Cambodian", "Cameroonian", "Canadian",
            "Cape Verdean", "Central African", "Chadian", "Chilean", "Chinese", "Colombian", "Comoran", "Congolese", "Costa Rican",
            "Croatian", "Cuban", "Cypriot", "Czech", "Danish", "Djibouti", "Dominican", "Dutch", "East Timorese", "Ecuadorean",
            "Egyptian", "Emirian", "Equatorial Guinean", "Eritrean", "Estonian", "Ethiopian", "Fijian", "Filipino", "Finnish",
            "French", "Gabonese", "Gambian", "Georgian", "German", "Ghanaian", "Greek", "Grenadian", "Guatemalan",
            "Guinea-Bissauan", "Guinean", "Guyanese", "Haitian", "Herzegovinian", "Honduran", "Hungarian", "Icelander",
            "Indian", "Indonesian", "Iranian", "Iraqi", "Irish", "Israeli", "Italian", "Ivorian", "Jamaican", "Japanese",
            "Jordanian", "Kazakhstani", "Kenyan", "Kittian and Nevisian", "Kuwaiti", "Kyrgyz", "Laotian", "Latvian",
            "Lebanese", "Liberian", "Libyan", "Liechtensteiner", "Lithuanian", "Luxembourger", "Macedonian", "Malagasy",
            "Malawian", "Malaysian", "Maldivan", "Malian", "Maltese", "Marshallese", "Mauritanian", "Mauritian", "Mexican",
            "Micronesian", "Moldovan", "Monacan", "Mongolian", "Moroccan", "Mosotho", "Motswana", "Mozambican", "Namibian",
            "Nauruan", "Nepalese", "New Zealander", "Ni-Vanuatu", "Nicaraguan", "Nigerien", "North Korean", "Northern Irish",
            "Norwegian", "Omani", "Pakistani", "Palauan", "Palestinian", "Panamanian", "Papua New Guinean", "Paraguayan",
            "Peruvian", "Polish", "Portuguese", "Qatari", "Romanian", "Russian", "Rwandan", "Saint Lucian", "Salvadoran",
            "Samoan", "San Marinese", "Sao Tomean", "Saudi", "Scottish", "Senegalese", "Serbian", "Seychellois", "Sierra Leonean",
            "Singaporean", "Slovakian", "Slovenian", "Solomon Islander", "Somali", "South African", "South Korean", "Spanish",
            "Sri Lankan", "Sudanese", "Surinamer", "Swazi", "Swedish", "Swiss", "Syrian", "Taiwanese", "Tajik", "Tanzanian",
            "Thai", "Togolese", "Tongan", "Trinidadian or Tobagonian", "Tunisian", "Turkish", "Tuvaluan", "Ugandan", "Ukrainian",
            "Uruguayan", "Uzbekistani", "Venezuelan", "Vietnamese", "Welsh", "Yemenite", "Zambian", "Zimbabwean"};


    public final static String VOUCHER_ID = "voucher_id";
    public final static String VOUCHER_CODE = "voucher_code";
    public final static String VOUCHER_VALUE = "voucher_value";


    //    QT5 Event
//    Event booking
    public final static String EVENT_TIME = "EVENT_TIME";
    public final static String EVENT_DATE = "EVENT_DATE";
    public final static String EVENT_DATE_ID = "EVENT_DATE_ID";
    public final static String NO_OF_TICKETS = "NO_OF_TICKETS";
    public final static String SERVICE_CHARGE = "SERVICE_CHARGE";
    public final static String TOTAL_AMT = "TOTAL_AMT";
    public final static String TICKET_TYPE_LIST = "TICKET_TYPE_LIST";
    public final static String EVENT_NAME = "EVENT_NAME";
    public final static String EVENT_VENUE = "EVENT_VENUE";
    public final static String IS_COUPON = "IS_COUPON";
    public final static String COUPON_CODE = "COUPON_CODE";
    public final static String GUEST_NAME = "GUEST_NAME";
    public final static String IS_GUEST = "IS_GUEST";
    public final static String GUEST_EMAIL = "GUEST_EMAIL";
    public final static String GUEST_PHONE = "GUEST_PHONE";
    public final static String TICKET_XML = "TICKET_XML";
    public final static String TICKET_VERIFY_XML = "TICKET_VERIFY_XML";
    public final static String IS_EVENT_OFFLINE = "IS_EVENT_OFFLINE";

}
