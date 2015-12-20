package com.vitalsport.profile.web;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.service.StrengthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.format.DateTimeParseException;

import static com.vitalsport.profile.common.CommonUtils.decode;
import static com.vitalsport.profile.common.CommonUtils.getMeasurementDate;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping(value = "/profile")
public class StrengthController {

    @Autowired
    private StrengthInfoService strengthInfoService;

    @RequestMapping(value = "/strength/{userId}/{date}", method = RequestMethod.POST)
    public ResponseEntity<String> saveStrengthInfo(@PathVariable String userId, @PathVariable String date,
                                               @RequestBody StrengthInfo strengthInfo) {
        try {
            strengthInfoService.save(prepareStrengthId(userId, date), strengthInfo);
            return ok("User strength info has been saved.");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }


    @RequestMapping(value = "/strength/{userId}/{date}", method = RequestMethod.GET)
    public ResponseEntity<String> getStrengthInfo(@PathVariable String userId, @PathVariable String date) {

        try {
            StrengthInfo strengthInfo = strengthInfoService.get(prepareStrengthId(userId, date));
            return ok(strengthInfo == null ? "" : strengthInfo.toString());
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }


    @RequestMapping(value = "/strength/{userId}/{date}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStrengthInfo(@PathVariable String userId, @PathVariable String date) {
        try {
            strengthInfoService.delete(prepareStrengthId(userId, date));
            return ok("User strength info has been deleted");
        } catch (DateTimeParseException exception) {
            return badRequest().body(String.format("date = %s has not valid format.", date));
        } catch (IllegalArgumentException exception) {
            return badRequest().body(exception.toString());
        }
    }

    private MeasurementId prepareStrengthId(String userId, String date) {
        return new MeasurementId(decode(userId), getMeasurementDate(date));
    }
}