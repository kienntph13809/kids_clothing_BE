package com.kids_clothing.vnpay;

import com.kids_clothing.entity.History;

import java.util.List;

public interface HistoryService {
    History creat(History history);

    History FinbyTrading_code(Long trading_code);

    List<History> findAll();

    List<History> findAllByCustomerId(long id);
}
