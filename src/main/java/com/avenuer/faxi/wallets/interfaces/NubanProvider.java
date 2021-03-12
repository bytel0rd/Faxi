package com.avenuer.faxi.wallets.interfaces;

import com.avenuer.faxi.wallets.models.VirtualNuban;
import com.avenuer.faxi.wallets.params.CreateNubanParam;

public interface NubanProvider {

    public VirtualNuban create(CreateNubanParam create);

    public VirtualNuban retrieve();

    public VirtualNuban update();

    public boolean delete();

}
