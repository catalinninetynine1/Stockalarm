package com.projectdevm8.stockalarm.service;

import com.projectdevm8.stockalarm.StockAlarmApplication;
import com.projectdevm8.stockalarm.ConfigEx.exception.StockReaderException;
import com.projectdevm8.stockalarm.service.implementation.StockReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = StockAlarmApplication.class)
class StockReaderServiceIt {

  @Autowired
  private StockReaderService service;

  @Test
  void getPrice_whenTheStockExists_thenReturnPrice() {
    Double realStock = service.getPrice("FB");
    assertThat(realStock).isNotNull();
    assertThat(realStock).isNotEqualTo(0.0);
  }

  @Test
  void getPrice_whenStockSymbolDoesNotExist_thenThrowStockReaderException() {
    assertThatExceptionOfType(StockReaderException.class).isThrownBy(() -> service.getPrice("this is a fake stock symbol"));
  }
}