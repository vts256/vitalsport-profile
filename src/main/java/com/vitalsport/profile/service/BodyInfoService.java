package com.vitalsport.profile.service;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.MeasurementInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BodyInfoService implements MeasurementInfoService<BodyInfo> {

    private MeasurementInfoRepository<BodyInfo> measurementInfoRepository;

    @Autowired
    public BodyInfoService(MeasurementInfoRepository measurementInfoRepository) {
        this.measurementInfoRepository = measurementInfoRepository;
    }

    @Override
    public void save(MeasurementId id, BodyInfo bodyInfo) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if(bodyInfo == null) {
            throw new IllegalArgumentException("bodyInfo couldn't be null");
        }

        log.info("Saving bodyInfo for id = %s, body = %s", id, bodyInfo);

        bodyInfo.setId(id);
        measurementInfoRepository.save(bodyInfo);
    }

    @Override
    public BodyInfo get(MeasurementId id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving bodyInfo for id = %s", id);

        return measurementInfoRepository.findOne(id);
    }

    @Override
    public void delete(MeasurementId id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Deleting bodyInfo for id = %s", id);

        measurementInfoRepository.delete(id);
    }
}
