package com.avenuer.faxi.users.Services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Models.TraditionalBankingInformation;
import com.avenuer.faxi.users.Params.BVNUpdateParam;
import com.avenuer.faxi.users.Params.TraditionalAccountUpdateParam;
import com.avenuer.faxi.users.doa.TraditionalBankingInformationDoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TraditionalBankingInformationService {

    @Autowired
    private TraditionalBankingInformationDoa traditionalBankingDoa;

    public TraditionalBankingInformation updateBvnInformation(AuthProfile currentUser, BVNUpdateParam bvnInformation) {
        TraditionalBankingInformation accountDetailsToUpdate = traditionalBankingDoa.findByUserId(bvnInformation.getUserId());

        if (accountDetailsToUpdate == null) {
            accountDetailsToUpdate = TraditionalBankingInformation.builder()
                    .userId(bvnInformation.getUserId())
                    .build();
        }

        if (false) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "UnMatch Data provided during BVN verification");
        }

        accountDetailsToUpdate.setBVN(bvnInformation.getBVN());
        accountDetailsToUpdate.setModifiedBy(currentUser.fullName());

        return traditionalBankingDoa.save(accountDetailsToUpdate);
    }

    TraditionalBankingInformation updateBankInformation(AuthProfile currentUser, TraditionalAccountUpdateParam accountInformation) {
        TraditionalBankingInformation accountDetailsToUpdate = traditionalBankingDoa.findByUserId(accountInformation.getOwnerId());

        if (accountDetailsToUpdate == null) {
            accountDetailsToUpdate = TraditionalBankingInformation.builder()
                    .userId(accountInformation.getOwnerId())
                    .build();
        }

        accountDetailsToUpdate.setBankCode(accountInformation.getBankCode());
        accountDetailsToUpdate.setBankAccountNumber(accountInformation.getBankAccountNumber());
        accountDetailsToUpdate.setModifiedBy(currentUser.fullName());

        return traditionalBankingDoa.save(accountDetailsToUpdate);

    }

}
