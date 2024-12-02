package lk.ijse.gdse.greenshadow.controller;

import lk.ijse.gdse.greenshadow.customStatusCodes.GeneralErrorCode;
import lk.ijse.gdse.greenshadow.dto.FieldStatus;
import lk.ijse.gdse.greenshadow.dto.impl.FieldDTO;
import lk.ijse.gdse.greenshadow.exceptions.DataPersistException;
import lk.ijse.gdse.greenshadow.service.FieldService;
import lk.ijse.gdse.greenshadow.util.Apputil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("location") String location,
            @RequestParam("size") Double extSizeofField,
            @RequestParam("plantedCrop") String plantedCrop,
            @RequestParam("staffList") List<String> staffList,
            @RequestParam("image1") MultipartFile fieldPicture1,
            @RequestParam("image2") MultipartFile fieldPicture2
    ) {
        String pic1 = null;
        String pic2 = null;
        int x = Integer.parseInt(location.split(",")[0]);
        int y = Integer.parseInt(location.split(",")[1]);

        try {
            byte[] pic1Bytes = fieldPicture1.getBytes();
            byte[] pic2Bytes = fieldPicture2.getBytes();

            pic1 = Apputil.convertToBase64(pic1Bytes);
            pic2 = Apputil.convertToBase64(pic2Bytes);

            var buildField = new FieldDTO();

            buildField.setFieldCode(Apputil.generateFieldCode());
            buildField.setFieldName(fieldName);
            buildField.setLocation(new Point(x,y));
            buildField.setSize(extSizeofField);
            buildField.setStaff(staffList);
//            buildField.setCrop(plantedCrop);
            buildField.setFieldPicture1(pic1);
            buildField.setFieldPicture2(pic2);

            fieldService.saveField(buildField);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode) {
        String regex = "^FID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(fieldCode);
        try {
            if (!matcher.matches()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields() {
        return fieldService.getAllFields();
    }

    @GetMapping(value = "/{fieldCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStatus getField(@PathVariable("fieldCode") String fieldCode) {
        String regex = "^FID[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(fieldCode);
        if (!matcher.matches()) {
            return new GeneralErrorCode(1, "Could not find the field");
        }
        return fieldService.getField(fieldCode);
    }
    @PutMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> updateField(@PathVariable("fieldCode") String fieldCode, @RequestBody FieldDTO fieldDTO) {
        return null;
    }
}
