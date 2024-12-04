package lk.ijse.gdse.greenshadow.controller;

import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dto.CropStatus;
import lk.ijse.gdse.greenshadow.dto.impl.CropDTO;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.CropService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/crop")
public class CropController {
    @Autowired
    private CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestParam("commonName") String commonName,
            @RequestParam("scientificName") String scientificName,
            @RequestParam("category") String category,
            @RequestParam("season") String season,
            @RequestParam("fieldList")List<FieldDTO> fieldDTOList,
            @RequestParam("image")MultipartFile image
            ){
        String cropImage = null;
        try{
            byte[] imageArr = image.getBytes();
            cropImage = Apputil.convertToBase64(imageArr);

            CropDTO cropDTO = new CropDTO();

            cropDTO.setCropCode(Apputil.generateCropCode());
            cropDTO.setCropCommonName(commonName);
            cropDTO.setCropScientificName(scientificName);
            cropDTO.setCategory(category);
            cropDTO.setSeason(season);
            cropDTO.setFields(fieldDTOList);

            cropService.saveCrop(cropDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<CropDTO> getAllCrop(){
       return cropService.getAllCrops();
    }
    @GetMapping(value = "/{cropCode}")
    public CropStatus getCropById(@PathVariable("cropCode") String cropCode){
        String regex = "^CID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(cropCode);
        if (!matcher.matches()) {
            return new GeneralErrorCode(1,"Could not find the crop with id "+cropCode);
        }else {
            return cropService.getCrop(cropCode);
        }
    }
    @DeleteMapping(value = "/{cropCode}")
    public ResponseEntity<Void> deleteCropById(@PathVariable("cropCode") String cropCode){
        String regex = "^FID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(cropCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try{
                cropService.deleteCrop(cropCode);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch(DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @PutMapping(value = "/{cropCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("cropCode") String cropCode,
                                           @RequestParam("commonName") String commonName,
                                           @RequestParam("scientificName") String scientificName,
                                           @RequestParam("category") String category,
                                           @RequestParam("season") String season,
                                           @RequestParam("fieldList")List<FieldDTO> fieldDTOList,
                                           @RequestParam("image")MultipartFile image){

        String regex = "^FID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(cropCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            String cropImage = null;
            try {
                byte[] imgArr = image.getBytes();
                cropImage = Apputil.convertToBase64(imgArr);

                CropDTO cropDTO = new CropDTO();
                cropDTO.setCropCommonName(commonName);
                cropDTO.setCropScientificName(scientificName);
                cropDTO.setCategory(category);
                cropDTO.setSeason(season);
                cropDTO.setFields(fieldDTOList);
                cropDTO.setCropImage(cropImage);

                cropService.updateCrop(cropDTO,cropCode);
                return new ResponseEntity<>(HttpStatus.OK);
             }catch (DataPersistException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


}
