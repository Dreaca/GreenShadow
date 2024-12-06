package lk.ijse.gdse.greenshadow.util;

import java.util.Base64;
import java.util.UUID;

public class Apputil {
    public static String generateStaffID(){
        return "SID"+UUID.randomUUID().toString();
    }
    public static String generateFieldCode(){
        return "FID"+UUID.randomUUID().toString();
    }
    public static String generateEquipmentCode(){
        return "EID"+UUID.randomUUID().toString();
    }
    public static String generateLogCode(){
        return "LOG"+UUID.randomUUID().toString();
    }
    public static String generateCropCode(){
        return "CID"+UUID.randomUUID().toString();
    }
    public static String generateVehicleCode(){
        return "VID"+UUID.randomUUID().toString();
    }
    public static String convertToBase64(byte[] profilePicture){
        return Base64.getEncoder().encodeToString(profilePicture);
    }
    public static String generateUSerId(){
        return "UID"+UUID.randomUUID().toString();
    }
    public static String trimmedId(String Id){
        return Id.replaceAll("[\\[\\]\"]", "").trim();
    }
}
