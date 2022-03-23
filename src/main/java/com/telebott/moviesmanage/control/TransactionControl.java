package com.telebott.moviesmanage.control;

import com.telebott.moviesmanage.entity.RequestData;
import com.telebott.moviesmanage.entity.ResultData;
import com.telebott.moviesmanage.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionControl {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/list")
    private ResultData list(@ModelAttribute RequestData requestData){
        ResultData data = new ResultData();
        data.setData(transactionService.list(requestData.getUser()));
        return data;
    }
}
