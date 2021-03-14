package com.avenuer.faxi.merchants.services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.merchants.doa.MerchantDoa;
import com.avenuer.faxi.merchants.models.ApiCredential;
import com.avenuer.faxi.merchants.models.Merchant;
import com.avenuer.faxi.merchants.params.CreateApiCredentialParam;
import com.avenuer.faxi.merchants.params.CreateMerchantParam;
import com.avenuer.faxi.merchants.params.CreatedMerchant;
import com.avenuer.faxi.shared.EnvironmentMode;
import com.avenuer.faxi.shared.Utilities;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class MerchantService {

    @Autowired
    private MerchantDoa merchantDoa;

    @Autowired
    private ApiCredentialService credentialSvc;

    public CreatedMerchant create(@NotNull AuthProfile authProfile, @NotNull CreateMerchantParam createMerchantReq) {

        Merchant merchant = null;

        try {

            Merchant merchantUpdate = Merchant.builder()
                    .ownerId(createMerchantReq.getOwnerId())
                    .name(createMerchantReq.getName())
                    .email(createMerchantReq.getEmail())
                    .createdBy(authProfile.fullName())
                    .modifiedBy(authProfile.fullName())
                    .build();

            merchant = merchantDoa.save(merchantUpdate);

            CreateApiCredentialParam liveCredentialReq = CreateApiCredentialParam.builder()
                    .name(createMerchantReq.getName())
                    .email(merchant.getEmail())
                    .merchantId(merchant.getId())
                    .mode(EnvironmentMode.LIVE)
                    .build();

            ApiCredential liveCredential = credentialSvc.create(authProfile, liveCredentialReq);

            CreateApiCredentialParam testCredentialReq = CreateApiCredentialParam.builder()
                    .name(createMerchantReq.getName())
                    .email(merchant.getEmail())
                    .merchantId(merchant.getId())
                    .mode(EnvironmentMode.TEST)
                    .build();

            ApiCredential testCredential = credentialSvc.create(authProfile, testCredentialReq);

            CreatedMerchant data = new CreatedMerchant();


            BeanUtils.copyProperties(merchant, data, Utilities.getNullPropertyNames(merchant));

            data.setLiveAPIKey(liveCredential.getApiKey());
            data.setTestAPIKey(testCredential.getApiKey());

            return data;

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            if (merchant != null) return (CreatedMerchant) merchant;

            throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED, "Error while creating merchant details and credential, please try again");

        }

    }

   public Merchant retrieveById(UUID merchantId) {
        var savedMerchant = merchantDoa.findById(merchantId);

        if (savedMerchant.isPresent()) {
            return savedMerchant.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Merchant with %s id not found.", merchantId));
    }

   public Merchant update (@NotNull AuthProfile authProfile, @NotNull Merchant merchant) {
        try {

            merchant.setDateModified(new Date());
            merchant.setModifiedBy(authProfile.fullName());
            return  merchantDoa.save(merchant);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error occurred while updating details, Please check the request payload and try again");

        }

    }

    public Boolean deleteById(@NotNull AuthProfile authProfile, UUID merchantId) {
        try {
             merchantDoa.deleteById(merchantId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED, String.format("Error occurred while deleting merchant details.", merchantId));
        }
    }

}
