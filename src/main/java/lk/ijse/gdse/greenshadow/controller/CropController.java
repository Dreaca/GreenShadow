package lk.ijse.gdse.greenshadow.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/crops")
public class CropController {
    @Autowired
    private CropService cropService;
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestParam("commonName") String commonName,
            @RequestParam("scientificName") String scientificName,
            @RequestParam("category") String category,
            @RequestParam("fields") String field,
            @RequestParam("season") String season,
            @RequestParam("image")MultipartFile image
            ){
        String cropImage = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            byte[] imageArr = image.getBytes();
            cropImage = Apputil.convertToBase64(imageArr);

            CropDTO cropDTO = new CropDTO();

            cropDTO.setCropCode(Apputil.generateCropCode());
            cropDTO.setCropCommonName(commonName);
            cropDTO.setCropScientificName(scientificName);
            cropDTO.setCategory(category);
            cropDTO.setSeason(season);
            cropDTO.setCropImage(cropImage);
            List<FieldDTO> fieldList = objectMapper.readValue(field, new TypeReference<List<FieldDTO>>() {});
            cropDTO.setFields(fieldList);
            cropService.saveCrop(cropDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMINISTRATOR','SCIENTIST')")
    @GetMapping
    public List<CropDTO> getAllCrop(){
       return cropService.getAllCrops();
    }
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
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
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
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
    @PreAuthorize("hasAnyRole('MANAGER','SCIENTIST')")
    @PutMapping(value = "/{cropCode}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("cropCode") String cropCode,
                                           @RequestParam("commonName") String commonName,
                                           @RequestParam("scientificName") String scientificName,
                                           @RequestParam("category") String category,
                                           @RequestParam("season") String season,
                                           @RequestParam("fieldList")String fields,
                                           @RequestParam("image")MultipartFile image){

        String regex = "^FID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(cropCode);
        if (!matcher.matches()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            String cropImage = null;
            try {
                byte[] imgArr = image.getBytes();
                cropImage = Apputil.convertToBase64(imgArr);
                List<FieldDTO> fieldList = objectMapper.readValue(fields, new TypeReference<List<FieldDTO>>() {});

                CropDTO cropDTO = new CropDTO();
                cropDTO.setCropCommonName(commonName);
                cropDTO.setCropScientificName(scientificName);
                cropDTO.setCategory(category);
                cropDTO.setSeason(season);
                cropDTO.setFields(fieldList);
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
