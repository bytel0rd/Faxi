package com.avenuer.faxi.users.Params;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class BVNUpdateParam {

    @NonNull
    private String BVN;

    @NonNull
    private String dob;

    private  String userId;
}
