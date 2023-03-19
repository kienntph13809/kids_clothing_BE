package com.kids_clothing.vnpay;

import com.kids_clothing.entity.Mamipay;

import java.util.List;

public interface MamiPayService {
    List<Mamipay> findAll();

    Mamipay ByCustomer(Long id);

    Mamipay create(Mamipay mamipay);

    Mamipay MamipayIdCt(Long id);

    Mamipay finById(Long id);

    Mamipay getMamiPayCustomer(Long authUID);

    Mamipay createMamiPay(Long authUID);
}
