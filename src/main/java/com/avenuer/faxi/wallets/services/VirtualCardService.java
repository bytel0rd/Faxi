package com.avenuer.faxi.wallets.services;

import com.avenuer.faxi.Notification.Services.SlackService;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.wallets.dao.VirtualCardDoa;
import com.avenuer.faxi.wallets.models.VirtualCard;
import com.avenuer.faxi.wallets.params.CreateVirtualCardParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@   Slf4j
@Service
public class VirtualCardService {

    @Autowired
    private VirtualCardDoa cardDoa;

    @Autowired
    private VirtualCardProvisionService cardProvisionService;

    @Autowired
    private SlackService slack;

    public VirtualCard create(AuthProfile auth, CreateVirtualCardParam cardReq) {

        Optional<VirtualCard> cardInformation = cardDoa.findByOwnerId(cardReq.getOwnerId());

        if (cardInformation.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Virtual Card Already exist for the user");
        }

        VirtualCard card = null;

        try {

            card =  cardProvisionService.create(cardReq);

            card.setOwnerId(cardReq.getOwnerId());
            card.setCreatedBy(auth.fullName());

            card = cardDoa.save(card);

            return card;

        } catch (Exception e) {

            if (card != null) {
                slack.notify("API_Serive",
                        String.format("Error occured while after creating virtual card" +
                                " \n\r %s", Utilities.objectToJSON(card)));
            }

            log.error(e.getMessage(), e);
            throw e;
        }

    }

    public VirtualCard retrieve(UUID cardId) {

        Optional<VirtualCard> cardInformation = cardDoa.findById(cardId);

        if (cardInformation.isPresent()) {
            return cardInformation.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Virtual Card with id %s not found", cardId));
    }

    public VirtualCard retrieveCardWithOwnerId(UUID userId) {

        Optional<VirtualCard> cardInformation = cardDoa.findByOwnerId(userId);

        if (cardInformation.isPresent()) {
            return cardInformation.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Virtual Card with owner id %s not found", userId));
    }

    public VirtualCard update(AuthProfile authProfile, VirtualCard card) {
        Optional<VirtualCard> cardInformation = cardDoa.findByOwnerId(card.getOwnerId());

        if (!cardInformation.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Virtual card Not Found");
        }

        card.setModifiedBy(authProfile.fullName());

        return cardDoa.save(card);
    }

    public VirtualCard deactivate(AuthProfile authProfile, UUID cardId) {

        Optional<VirtualCard> cardInformation = cardDoa.findById(cardId);

        if (cardInformation.isPresent()) {
            VirtualCard cardUpdate = cardInformation.get();
            cardUpdate.setIsActive(false);
            return update(authProfile, cardUpdate);
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Virtual Card with id %s not found", cardId));

    }

}
