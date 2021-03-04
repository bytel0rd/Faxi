package com.avenuer.faxi.wallets.services;

import com.avenuer.faxi.Notification.Services.SlackService;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.wallets.dao.VirtualNubanDao;
import com.avenuer.faxi.wallets.models.VirtualNuban;
import com.avenuer.faxi.wallets.params.CreateNubanParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class VirtualNubanService {

    @Autowired
    private VirtualNubanDao nubanDao;

    @Autowired
    private NubanProvisionService nubanProvisionService;

    @Autowired
    private SlackService slack;

    public VirtualNuban create(AuthProfile auth, CreateNubanParam nubanReq) {

        Optional<VirtualNuban> nubanInformation = nubanDao.findByUserId(nubanReq.getUserId());

        if (nubanInformation.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Virtual Nuban Already exist for the user");
        }

        VirtualNuban nuban = null;

        try {

            nuban =  nubanProvisionService.create(nubanReq);

            nuban.setUserId(nubanReq.getUserId());
            nuban.setCreatedBy(auth.fullName());

            nuban = nubanDao.save(nuban);

            return nuban;

        } catch (Exception e) {

            if (nuban != null) {
                slack.notify("API_Serive",
                        String.format("Error occured while after creating virtual nuban details." +
                                " \n\r %s", Utilities.objectToJSON(nuban)));
            }

            log.error(e.getMessage(), e);
            throw e;
        }

    }

    public VirtualNuban retrieve(UUID nubanId) {

        Optional<VirtualNuban> nubanInformation = nubanDao.findById(nubanId);

        if (nubanInformation.isPresent()) {
            return nubanInformation.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Virtual Nuban with id %s not found", nubanId));
    }

    public VirtualNuban retrieveNubanWithUserId(UUID userId) {

        Optional<VirtualNuban> nubanInformation = nubanDao.findByUserId(userId);

        if (nubanInformation.isPresent()) {
            return nubanInformation.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Virtual Nuban with id %s not found", userId));
    }

    public VirtualNuban update(AuthProfile authProfile, VirtualNuban nuban) {
        Optional<VirtualNuban> nubanInformation = nubanDao.findByUserId(nuban.getUserId());

        if (!nubanInformation.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Virtual Nuban Not Found");
        }

        nuban.setModifiedBy(authProfile.fullName());

        return nubanDao.save(nuban);
    }

    public VirtualNuban deactivate(AuthProfile authProfile, UUID nubanId) {
        Optional<VirtualNuban> nubanInformation = nubanDao.findById(nubanId);

        if (nubanInformation.isPresent()) {
            VirtualNuban nubanUpdate = nubanInformation.get();
            nubanUpdate.setIsActive(false);
            return update(authProfile, nubanUpdate);
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Virtual Nuban with id %s not found", nubanId));

    }

}
