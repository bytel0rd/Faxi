package com.avenuer.faxi.merchants.services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.merchants.doa.ApiCredentialDoa;
import com.avenuer.faxi.merchants.models.ApiCredential;
import com.avenuer.faxi.merchants.params.CreateApiCredentialParam;
import com.avenuer.faxi.shared.EnvironmentMode;
import com.avenuer.faxi.shared.Utilities;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@Service
public class ApiCredentialService {

    @Autowired
    private ApiCredentialDoa credentialDoa;

    public ApiCredential create(@NotNull AuthProfile authProfile, CreateApiCredentialParam credentialRequest) {

        ApiCredential credential = null;

        try {

            EnvironmentMode mode = credentialRequest.getMode() != null ? credentialRequest.getMode() : EnvironmentMode.TEST;

            ApiCredential credentialUpdate = ApiCredential.builder()
                    .merchantId(credentialRequest.getMerchantId())
                    .secretKey(String.format("BLF-%s-%s-%s", mode, Utilities.randomString(12), Utilities.randomString(12)))
                    .secretPass(String.format("BLF-%s-%s-%s", mode, Utilities.randomString(12), Utilities.randomString(12)))
                    .modifiedBy(authProfile.fullName())
                    .createdBy(authProfile.fullName())
                    .whiteListedIPs(credentialRequest.getWhiteListedIPs())
                    // Use Joda Time to parse the expiring date
                    //.expiringDate(credentialRequest.getExpiringDate())
                    .build();

            credential = credentialDoa.save(credentialUpdate);

            return credential;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error occurred while creating merchant credentials, please retry again or contact admin");
        }

    }


    public ApiCredential retrieve(UUID credentialId) {
        var credential = credentialDoa.findById(credentialId);

        if (credential.isPresent()) {
            return credential.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Merchant Credential with id %s not found", credentialId));

    }

    public ApiCredential retrieveByMerchantId(UUID merchantId) {
        var credential = credentialDoa.findByMerchantId(merchantId);

        if (credential.isPresent()) {
            return credential.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Merchant Credential Not found for %s owner", merchantId));

    }

    public Boolean delete(@NotNull AuthProfile auth, UUID credentialId) {
        try {
            credentialDoa.deleteById(credentialId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error processing request at the moment, please try again ");
        }
    }

}
