package com.kids_clothing.vnpay;

import com.kids_clothing.dao.HistoryDao;
import com.kids_clothing.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryDao historyDao;

    @Override
    public History creat(History history) {
        return historyDao.save(history);
    }

    @Override
    public History FinbyTrading_code(Long trading_code) {
        return historyDao.FinbyTrading_code(trading_code);
    }

    @Override
    public List<History> findAll() {
        return historyDao.findAll();
    }

    @Override
    public List<History> findAllByCustomerId(long id) {
        return historyDao.findAllByCustomerId(id);
    }


}
